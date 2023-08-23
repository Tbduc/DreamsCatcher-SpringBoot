package com.codecool.elproyectegrande1.mapper;

import com.codecool.elproyectegrande1.dto.dream.DreamDto;
import com.codecool.elproyectegrande1.dto.dream.NewDreamDto;
import com.codecool.elproyectegrande1.entity.Dream;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DreamMapper {

    private final CommentMapper commentMapper;

    public DreamMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public DreamDto mapEntityToDreamDto(Dream entity) {
        Long imageId = null;
        if (entity.getMainImage() != null)
             imageId = entity.getMainImage().getId();
        return new DreamDto (
                entity.getId(),
                entity.getDreamTitle(),
                entity.getDreamDescription(),
                entity.getHashtags(),
                imageId,
                entity.getComments()
                        .stream()
                        .map(commentMapper::mapEntityToCommentDto)
                        .collect(Collectors.toSet()),
                entity.getLikes(),
                entity.getViews(),
                entity.getDreamer().getId()
        );
    }

    public Dream mapNewDreamDtoToEntity(NewDreamDto dto) {
        return new Dream(
                dto.getDreamTitle(),
                dto.getDreamDescription(),
                dto.getHashtags(),
                dto.getImage()
        );
    }
}