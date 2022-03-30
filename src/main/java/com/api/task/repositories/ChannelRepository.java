package com.api.task.repositories;

import com.api.task.entities.Channel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

  public Channel findByChannelId(String channelId);
    
}
