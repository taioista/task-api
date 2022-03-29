package com.api.task.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.api.task.clients.YoutubeFeignClient;
import com.api.task.dtos.YoutubeApiDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class YoutubeApiService {

    @Autowired
    private YoutubeFeignClient youtubeFeignClient;

    public String getUploadPlaylistId(String key, String part, String id) {
        String response = this.youtubeFeignClient.getUploadPlaylistId(key, part, id);
        String uploads = "";
        log.info("Request uploads id to Youtube Api with the parameters: {}", id, part);
        try {
            JsonNode responseJ = new ObjectMapper().readTree(response);
            uploads = responseJ.get("items").get(0)
                        .get("contentDetails").get("relatedPlaylists")
                        .get("uploads").textValue();
            log.info("Uploads playlist: {}", uploads);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            log.error("Error while mapping json: {}", e);
        } catch (JsonProcessingException e) {
            log.error("Error while processing json: {}", e);
        }
        return uploads;
    }

    public YoutubeApiDTO getVideosInfoByUploadsId(String playlistId, String key, String pageToken) {
        log.info("Request videos info from Youtube Api with the parameters: {}", playlistId, pageToken);
        Map<String, String> parameters = this.getMapParameters(playlistId, key, pageToken);
        YoutubeApiDTO youtubeApiDTO = this.youtubeFeignClient.getVideosByPlaylistId(parameters);
        return youtubeApiDTO;
    }
    
    private Map<String, String> getMapParameters(String playlistId, String key, String pageToken) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("playlistId", playlistId);
        parameters.put("key", key);
        parameters.put("part", "snippet");
        parameters.put("maxResults", "50");
        Optional.ofNullable(pageToken)
            .ifPresent(parameter -> parameters.put("pageToken", parameter));
        return parameters;
    }
}
