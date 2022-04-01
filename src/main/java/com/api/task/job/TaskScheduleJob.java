package com.api.task.job;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.api.task.entities.Channel;
import com.api.task.entities.Task;
import com.api.task.entities.Video;
import com.api.task.enums.Status;
import com.api.task.services.ChannelService;
import com.api.task.services.TaskService;
import com.api.task.services.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskScheduleJob {
    
    @Autowired
    private VideoService videoService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private TaskService taskService;

    @Scheduled(timeUnit = TimeUnit.DAYS, fixedDelay = 1)
    public void SearchAndRepair() {
        List<Status> listStatus = buildListStatus();
        List<Task> tasks = this.taskService.findByStatusList(listStatus);
        if(tasks != null && !tasks.isEmpty()) {
            List<Task> tasksFiltered = tasks.stream().filter(task -> {
                return isTaskError(task);
            }).collect(Collectors.toList());
            if(tasksFiltered != null && !tasksFiltered.isEmpty()) {
                Channel channel = this.channelService.getChannelByChannelId(tasksFiltered.get(0).getYoutubeChannelId());
                for(int i=0; i < tasksFiltered.size(); i++) {
                    if(channel != null 
                            && isNotSameChannel(tasksFiltered.get(i), channel)) {
                        channel = this.channelService.getChannelByChannelId(tasksFiltered.get(i).getYoutubeChannelId());
                    }

                    if(channel == null) {
                        channel = this.channelService.createChannelAndReturnIt(tasksFiltered.get(i).getYoutubeChannelId());
                    }

                    List<Video> videos = this.videoService.findByChannelId(channel.getId());
                    if(videos != null && videos.size() > 0) {
                        this.videoService.deleteAllInBatch(videos);
                    }

                    this.videoService.process(tasksFiltered.get(i).getYoutubeChannelId(), tasksFiltered.get(i).getId(), channel);
                }
            }
        }
    }

    private boolean isNotSameChannel(Task task, Channel channel) {
        return !channel.getChannelId().equals(task.getYoutubeChannelId());
    }

    private boolean isTaskError(Task task) {
        if(task.getStatus().equals(Status.CREATED) 
            || task.getStatus().equals(Status.PROCESSING)) {
            return task.getLastUpdate().isAfter(task.getLastUpdate().plusDays(1));
        } else {
            return true;
        }
    }

    private List<Status> buildListStatus() {
        List<Status> listStatus = new ArrayList<Status>();
        listStatus.add(Status.CREATED);
        listStatus.add(Status.ERROR);
        listStatus.add(Status.PROCESSING);
        return listStatus;
    }

}
