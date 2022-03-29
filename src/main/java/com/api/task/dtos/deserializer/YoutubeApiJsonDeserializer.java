package com.api.task.dtos.deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.api.task.dtos.VideoDTO;
import com.api.task.dtos.YoutubeApiDTO;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class YoutubeApiJsonDeserializer extends StdDeserializer<YoutubeApiDTO> {

    public YoutubeApiJsonDeserializer() {
        this(null);
    }

    protected YoutubeApiJsonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public YoutubeApiDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        YoutubeApiDTO dto = new YoutubeApiDTO();
        dto.setNextPageToken(node.get("nextPageToken").asText());
        dto.setTotalResults(node.get("pageInfo").get("totalResults").asLong());

        List<VideoDTO> videos = new ArrayList<VideoDTO>();
        node.get("items").elements().forEachRemaining(item -> {
            VideoDTO videoDTO = new VideoDTO();
            videoDTO.setDescription(item.get("snippet").get("description").asText());
            String videoId = item.get("snippet").get("resourceId").get("videoId").asText();
            videoDTO.setVideoId(videoId);

            String url = "https://www.youtube.com/watch?v=" + videoId;
            videoDTO.setUrl(url);

            videos.add(videoDTO);
        });

        if(!videos.isEmpty()) {
            dto.setVideos(new ArrayList<VideoDTO>());
            dto.getVideos().addAll(videos);
        }
        return dto;
    }
    
}
 