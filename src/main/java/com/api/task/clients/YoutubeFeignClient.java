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
    //key=AIzaSyDTG6AyoEZxcYEnsFUVm3KBCk-xancA4J0
    //&part=contentDetails
    //&id=UCSBp6wFCG4bVtk1TUlMyIQQ
    String getUploadPlaylistId(@RequestParam(value="key") String key,
                                @RequestParam(value="part") String part,
                                @RequestParam(value="id") String id);

    //playlistItems?playlistId=UUSBp6wFCG4bVtk1TUlMyIQQ&key=AIzaSyDTG6AyoEZxcYEnsFUVm3KBCk-xancA4J0&part=snippet
    // @GetMapping(path = "/playlistItems")
    // VideoDTO getVideosByPlaylistId(@RequestParam(value="playlistId") String playlistId,
    //                             @RequestParam(value="key") String key,
    //                             @RequestParam(value="part") String part,
    //                             @RequestParam(value="maxResults") String maxResults,
    //                             @RequestParam(value="pageToken") String pageToken);
    @GetMapping(path = "/playlistItems")
    YoutubeApiDTO getVideosByPlaylistId(@SpringQueryMap Map<String, String> paramMap);
}
