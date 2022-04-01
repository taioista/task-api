package com.api.task.dtos;

import java.util.ArrayList;
import java.util.List;

import com.api.task.dtos.deserializer.YoutubeApiJsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
@JsonDeserialize(using = YoutubeApiJsonDeserializer.class)
public class YoutubeApiDTO {
    
    private String nextPageToken;

    private Long totalResults;

    private List<VideoDTO> videos = new ArrayList<VideoDTO>();
}
