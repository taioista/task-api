package com.api.task.repositories;

import java.util.List;

import com.api.task.entities.Video;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByChannelId(Long channelId);
    
}
