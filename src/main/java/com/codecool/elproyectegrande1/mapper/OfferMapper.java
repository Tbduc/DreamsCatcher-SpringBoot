package com.codecool.elproyectegrande1.mapper;

import com.codecool.elproyectegrande1.dto.offer.NewOfferDto;
import com.codecool.elproyectegrande1.dto.offer.OfferDto;
import com.codecool.elproyectegrande1.entity.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OfferMapper {

    @Autowired
    private CommentMapper commentMapper;


    public OfferDto mapEntityToOfferDto(Offer entity) {
        Long imageId = null;
        if (entity.getMainImage() != null)
            imageId = entity.getMainImage().getId();
        return new OfferDto(
                entity.getId(),
                entity.getType(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getDate(),
                imageId,
                entity.getLikes(),
                entity.getViews(),
                entity.getComments()
                        .stream()
                        .map(commentMapper::mapEntityToCommentDto)
                        .collect(Collectors.toSet())
        );
    }

    public Offer mapOfferDtoToEntity(NewOfferDto dto) {
        return new Offer(
                dto.getType(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getDate(),
                dto.getImage()
        );
    }
}
