package com.api.task.clients;

import java.util.Map;

import com.api.task.dtos.YoutubeApiDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "youtube", url = "https://www.googleapis.com/youtube/v3")
public interface YoutubeFeignClient {

    @GetMapping(path = "/channels")
    String getUploadPlaylistId(@RequestParam(value="key") String key,
                                @RequestParam(value="part") String part,
                                @RequestParam(value="id") String id);

    @GetMapping(path = "/playlistItems")
    YoutubeApiDTO getVideosByPlaylistId(@SpringQueryMap Map<String, String> paramMap);
}
