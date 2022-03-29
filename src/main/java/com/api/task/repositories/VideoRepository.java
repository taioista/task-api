package com.api.task.repositories;

import com.api.task.entities.Video;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
    
}
