package com.api.task.dtos.mappers;

import java.util.List;

import com.api.task.dtos.VideoDTO;
import com.api.task.entities.Video;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface VideoMapper {
    Video toEntity(VideoDTO dto);
    List<Video> mapToEntity(List<VideoDTO> dtos);
    List<VideoDTO> mapToDTO(List<Video> entities);
}