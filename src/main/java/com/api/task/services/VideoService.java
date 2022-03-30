package com.api.task.services;

import java.util.List;

import javax.transaction.Transactional;

import com.api.task.dtos.VideoDTO;
import com.api.task.dtos.YoutubeApiDTO;
import com.api.task.dtos.mappers.VideoMapper;
import com.api.task.entities.Channel;
import com.api.task.entities.Video;
import com.api.task.enums.Status;
import com.api.task.repositories.VideoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VideoService {

    @Value("${youtube.api-key}")
    private String apiKey;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private YoutubeApiService youtubeApiService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private VideoMapper videoMapper;

    public List<VideoDTO> getVideoDTOByChannelId(Long channelId) {
        List<Video> videos = this.findByChannelId(channelId);
        return this.videoMapper.mapToDTO(videos);
    }

    public List<Video> findByChannelId(Long channelId) {
        return this.videoRepository.findByChannelId(channelId);
    }

    public void deleteAllInBatch(List<Video> videos) {
        this.videoRepository.deleteAllInBatch(videos);
    }

    @Transactional
    @Async("threadPoolYoutubeApiExecutor")
    public void process(String youtubeChannelId, Long taskId, Long channelId) {
        try {
            this.taskService.updateTaskStatus(taskId, Status.PROCESSING);
            String uploadsId = this.youtubeApiService.getUploadPlaylistId(apiKey, youtubeChannelId);
            YoutubeApiDTO dto = this.youtubeApiService.getVideosInfoByUploadsId(uploadsId, apiKey, null);
            this.insertVideosBatchFrom(dto.getVideos(), channelId);

            while(StringUtils.hasLength(dto.getNextPageToken())) {
                dto = this.youtubeApiService.getVideosInfoByUploadsId(uploadsId, apiKey, dto.getNextPageToken());
                this.insertVideosBatchFrom(dto.getVideos(), channelId);
            }

            this.taskService.updateTaskStatus(taskId, Status.DONE);
        } catch (Exception e) {
            this.taskService.updateTaskStatus(taskId, Status.ERROR);
            log.error("Error while processing videos from channel: {}", youtubeChannelId, taskId, e);
        }
    }

    public void insertVideosBatchFrom(List<VideoDTO> videosDTO, Long channelId) {
        List<Video> videos = this.videoMapper.mapToEntity(videosDTO);
        Channel channel = new Channel();
        channel.setId(channelId);
        videos.forEach(video -> {
            video.setChannel(channel);
        });
        this.videoRepository.saveAllAndFlush(videos);
    }

}
