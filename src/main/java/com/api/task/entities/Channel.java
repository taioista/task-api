package com.api.task.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "channel")
@NoArgsConstructor
public class Channel {

    public Channel(Long id) {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "YOUTUBE_CHANNEL_ID", nullable = false)
    private String channelId;

    @Column(name = "COUNT", nullable = false)
    private Long countVideos;

    @OneToMany(mappedBy = "channel", fetch=FetchType.LAZY)
    private List<Video> videos; 
}
