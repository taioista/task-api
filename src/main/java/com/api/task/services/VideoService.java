package com.api.task.services;

import java.util.List;

import javax.transaction.Transactional;

import com.api.task.dtos.UploadPlaylistDTO;
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
    private ChannelService channelService;

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
    public void process(String youtubeChannelId, Long taskId, Channel channel) {
        try {
            this.taskService.updateTaskStatus(taskId, Status.PROCESSING);
            UploadPlaylistDTO uploadPlaylistDTO = this.youtubeApiService.getUploadPlaylistId(apiKey, youtubeChannelId);
            
            if(uploadPlaylistDTO.getVideoCount() > 0) {
                YoutubeApiDTO youtubeApiDTO = this.youtubeApiService.getVideosInfoByUploadsId(uploadPlaylistDTO.getUploads(), apiKey, null);
                
                channel.setVideoCount(youtubeApiDTO.getTotalResults());
                this.channelService.saveChannel(channel);

                this.insertVideosBatchFrom(youtubeApiDTO.getVideos(), channel);

                while(StringUtils.hasLength(youtubeApiDTO.getNextPageToken())) {
                    youtubeApiDTO = this.youtubeApiService.getVideosInfoByUploadsId(uploadPlaylistDTO.getUploads(), apiKey, youtubeApiDTO.getNextPageToken());
                    this.insertVideosBatchFrom(youtubeApiDTO.getVideos(), channel);
                }
            }

            this.taskService.updateTaskStatus(taskId, Status.DONE);
        } catch (Exception e) {
            this.taskService.updateTaskStatus(taskId, Status.ERROR);
            log.error("Error while processing videos from channel: {}", youtubeChannelId, taskId, e);
        }
    }

    public void insertVideosBatchFrom(List<VideoDTO> videosDTO, Channel channel) {
        List<Video> videos = this.videoMapper.mapToEntity(videosDTO);
        videos.forEach(video -> {
            video.setChannel(channel);
        });
        this.videoRepository.saveAllAndFlush(videos);
    }

}
