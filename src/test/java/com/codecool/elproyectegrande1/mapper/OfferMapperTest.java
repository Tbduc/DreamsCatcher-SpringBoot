package com.codecool.elproyectegrande1.mapper;

import com.codecool.elproyectegrande1.dto.offer.NewOfferDto;
import com.codecool.elproyectegrande1.dto.offer.OfferDto;
import com.codecool.elproyectegrande1.entity.Image;
import com.codecool.elproyectegrande1.entity.Offer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.codecool.elproyectegrande1.entity.EOffer.PRIVATE_LESSON;

class OfferMapperTest {

    private final CommentMapper commentMapper = new CommentMapper();
    private final OfferMapper offerMapper = new OfferMapper(commentMapper);

    @Test
    void shouldMapEntityToOfferDto() {
        Offer offer = new Offer(PRIVATE_LESSON, "Test title", "Test description", BigDecimal.valueOf(10.0), LocalDate.parse("1983-04-05"), new Image());
        offer.setLikes(10);
        offer.setViews(200);

        OfferDto actual = offerMapper.mapEntityToOfferDto(offer);

        Assertions.assertEquals(offer.getTitle(), actual.getTitle());
        Assertions.assertEquals(offer.getDescription(), actual.getDescription());
        Assertions.assertEquals(offer.getPrice(), actual.getPrice());
        Assertions.assertEquals(offer.getDate(), actual.getDate());
        Assertions.assertEquals(offer.getMainImage(), actual.getImage());
        Assertions.assertEquals(offer.getComments(), actual.getComments());
    }

    @Test
    void shouldMapOfferDtoToEntity() {
        NewOfferDto offerDto = new NewOfferDto(PRIVATE_LESSON, "Test123", "Test description", BigDecimal.valueOf(15.0), LocalDate.parse("2023-04-05"), "Test name photo", 1L);

        Offer actual = offerMapper.mapOfferDtoToEntity(offerDto);

        Assertions.assertEquals(offerDto.getTitle(), actual.getTitle());
        Assertions.assertEquals(offerDto.getDescription(), actual.getDescription());
        Assertions.assertEquals(offerDto.getPrice(), actual.getPrice());
        Assertions.assertEquals(offerDto.getDate(), actual.getDate());
        Assertions.assertEquals(offerDto.getImage(), actual.getMainImage());
//        Assertions.assertEquals(offerDto.getComments(), actual.getComments());
    }
}