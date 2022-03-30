package com.api.task.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.api.task.enums.Status;

import lombok.Data;

@Data
@Entity
@Table(name = "task")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PROCESSED")
    private Long countVideosProcessed;

    @Column(name = "YOUTUBE_CHANNEL_ID", nullable = false)
    private String youtubeChannelId;

    @Column(name="STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "LAST_UPDATE")
    private LocalDateTime lastUpdate;
}
