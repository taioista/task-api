package com.api.task.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "video")
public class Video {

    @Id
    private Long id;

    @Column(name = "VIDEO_ID", nullable = false)
    private String videoId;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "URL", nullable = false)
    private String url;

    @JoinColumn(name = "YOUTUBE_CHANNEL_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Channel channel;
}
