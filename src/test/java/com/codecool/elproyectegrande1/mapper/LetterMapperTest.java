package com.codecool.elproyectegrande1.mapper;

import com.codecool.elproyectegrande1.dto.letter.LetterDto;
import com.codecool.elproyectegrande1.dto.letter.NewLetterDto;
import com.codecool.elproyectegrande1.entity.Letter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LetterMapperTest {

    private final LetterMapper letterMapper = new LetterMapper();

    @Test
    void shouldMapEntityToLetterDto() {
        Letter letter = new Letter("Test content", "Test sender");
        letter.setId(1L);

        LetterDto actual = letterMapper.mapEntityToLetterDto(letter);

        Assertions.assertEquals(letter.getId(), actual.getId());
        Assertions.assertEquals(letter.getContent(), actual.getContent());
        Assertions.assertEquals(letter.getSender(), actual.getSender());
    }

    @Test
    void shouldMapOfferDtoToEntity() {
        NewLetterDto letterDto = new NewLetterDto("Test content 100", "Duc");

        Letter actual = letterMapper.mapNewLetterDtoToEntity(letterDto, "Test user");

        Assertions.assertEquals(letterDto.getContent(), actual.getContent());
    }
}
