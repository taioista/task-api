package com.api.task.services;

import java.util.List;

import com.api.task.entities.Task;
import com.api.task.enums.Status;
import com.api.task.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task findById(Long taskId) {
        return taskRepository.getById(taskId);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Long createTaskAndReturnId(String youtubeChannelId) {
        Task task = buildTask(youtubeChannelId);
        this.taskRepository.save(task);
        return task.getId();
    }

    private Task buildTask(String youtubeChannelId) {
        Task task = new Task();
        task.setYoutubeChannelId(youtubeChannelId);
        task.setStatus(Status.CREATED);
        return task;
    }

    public Task saveTask(Task task) {
        return this.taskRepository.save(task);
    }
}
