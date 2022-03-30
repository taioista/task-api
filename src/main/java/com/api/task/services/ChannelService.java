package com.api.task.services;

import com.api.task.entities.Channel;
import com.api.task.repositories.ChannelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {
    
    @Autowired
    private ChannelRepository channelRepository;

    public Channel getChannelByChannelId(String channelId) {
        return this.channelRepository.findByChannelId(channelId);
    }

    public Long createChannelAndReturnId(String youtubeChannelId) {
        Channel channel = this.buildChannel(youtubeChannelId);
        channel = this.saveChannel(channel);
        return channel.getId();
    }

    public Channel saveChannel(Channel channel) {
        return this.channelRepository.save(channel);
    }

    private Channel buildChannel(String youtubeChannelId) {
        Channel channel = new Channel();
        channel.setChannelId(youtubeChannelId);
        channel.setCountVideos(0L);
        return channel;
    }
}
