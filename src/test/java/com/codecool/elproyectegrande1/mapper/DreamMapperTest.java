package com.codecool.elproyectegrande1.mapper;


import com.codecool.elproyectegrande1.dto.dream.DreamDto;
import com.codecool.elproyectegrande1.dto.dream.NewDreamDto;
import com.codecool.elproyectegrande1.entity.Dream;
import com.codecool.elproyectegrande1.entity.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class DreamMapperTest {

    private final DreamMapper dreamMapper = new DreamMapper();

    @Test
    void shouldMapEntityToDreamDto() {
        Dream dream = new Dream("Test", "test", new ArrayList<>(), new Image());
        dream.setId(1L);
        dream.setLikes(10);
        dream.setViews(200);

        List<String> hashtags = new ArrayList<>();
        hashtags.add("test");
        hashtags.add("test2");
        dream.setHashtags(hashtags);

        DreamDto actual = dreamMapper.mapEntityToDreamDto(dream);

        Assertions.assertEquals(dream.getId(), actual.getId());
        Assertions.assertEquals(dream.getDreamTitle(), actual.getDreamTitle());
        Assertions.assertEquals(dream.getDreamDescription(), actual.getDreamDescription());
        Assertions.assertEquals(dream.getHashtags(), actual.getHashtags());
        Assertions.assertEquals(dream.getMainImage(), actual.getImage());
    }

    @Test
    void shouldMapDreamDtoToEntity() {

        NewDreamDto dreamDto = new NewDreamDto("test", "test", new ArrayList<>(), "image.jpg", 1L);

        List<String> hashtags = new ArrayList<>();
        hashtags.add("test");
        hashtags.add("test2");
        dreamDto.setHashtags(hashtags);

        Dream actual = dreamMapper.mapNewDreamDtoToEntity(dreamDto);

        Assertions.assertEquals(dreamDto.getDreamTitle(), actual.getDreamTitle());
        Assertions.assertEquals(dreamDto.getDreamDescription(), actual.getDreamDescription());
        Assertions.assertEquals(dreamDto.getHashtags(), actual.getHashtags());
        Assertions.assertEquals(dreamDto.getImage(), actual.getMainImage());

    }
}