package com.api.task.controllers;

import java.util.ArrayList;
import java.util.List;

import com.api.task.clients.YoutubeFeignClient;
import com.api.task.dtos.VideoDTO;
import com.api.task.dtos.YoutubeApiDTO;
import com.api.task.entities.Task;
import com.api.task.entities.Video;
import com.api.task.services.TaskService;
import com.api.task.services.VideoService;
import com.api.task.services.YoutubeApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private YoutubeApiService youtubeApiService;

    @Autowired
    private YoutubeFeignClient youtubeClient;

    @Value("${youtube.api-key}")
    private String apiKey;
    
    /**
     * Creates a new task to parse the channel, returns taskId
     * @param youtubeChannelId
     * @return taskId
     */
    @PostMapping("/{youtubeChannelId}")
    public ResponseEntity<Long> createTask(@PathVariable String youtubeChannelId) {
        Long taskId = this.taskService.createTaskAndReturnId(youtubeChannelId);

        //this.youtubeApiService.getUploadPlaylistId(key, part, id)

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
    //public ResponseEntity<List<Video>> findById(@PathVariable Long taskId) {
        public ResponseEntity<VideoDTO> findById(@PathVariable Long taskId) {
        YoutubeApiDTO testDTO = youtubeApiService.getVideosInfoByUploadsId("UUSBp6wFCG4bVtk1TUlMyIQQ", apiKey, "");
        VideoDTO test = null;//youtubeClient.getVideosByPlaylistId("UUSBp6wFCG4bVtk1TUlMyIQQ", "AIzaSyDTG6AyoEZxcYEnsFUVm3KBCk-xancA4J0", "snippet");
        System.out.println(test);
        Task task = this.taskService.findById(taskId);
        List<Video> videos = new ArrayList<Video>();//this.videoService.findByChannel(task.getYoutubeChannelId());
        return ResponseEntity.ok(test);
    }
}
