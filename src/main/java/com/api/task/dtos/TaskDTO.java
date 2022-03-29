package com.api.task.dtos;

import com.api.task.enums.Status;

import lombok.Data;

@Data
public class TaskDTO {

    private Long id;

    private String youtubeChannelId;

    private Status status;
    
}
