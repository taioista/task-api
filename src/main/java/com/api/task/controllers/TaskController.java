package com.api.task.controllers;

import java.util.List;

import com.api.task.dtos.VideoDTO;
import com.api.task.entities.Channel;
import com.api.task.entities.Task;
import com.api.task.services.ChannelService;
import com.api.task.services.TaskService;
import com.api.task.services.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private VideoService videoService;
    
    /**
     * Creates a new task to parse the channel, returns taskId
     * @param youtubeChannelId
     * @return taskId
     */
    @PostMapping("/{youtubeChannelId}")
    public ResponseEntity<Long> createTask(@PathVariable String youtubeChannelId) {
        Long taskId = this.taskService.createTaskAndReturnId(youtubeChannelId);
        Channel channel = this.channelService.createChannelAndReturnIt(youtubeChannelId);
        this.videoService.process(youtubeChannelId, taskId, channel);
        return ResponseEntity.ok(taskId);
    }

    /**
     * Returns the list of created tasks with their statuses 
     * (may be parsed immediately or when all data has been received)
     * @return List<Task>
     */
    @GetMapping()
    public ResponseEntity<List<Task>> findAll() {
        List<Task> tasks = this.taskService.findAll();
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Returns list of videos that have been found in the specified channel 
     * as an array of objects: video id, description, link to the video
     * @param taskId
     * @return Task
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<List<VideoDTO>> findById(@PathVariable Long taskId) {
        Task task = this.taskService.findById(taskId);
        Channel channel = this.channelService.getChannelByChannelId(task.getYoutubeChannelId());
        List<VideoDTO> videos = this.videoService.getVideoDTOByChannelId(channel.getId());
        return ResponseEntity.ok(videos);
    }
}
