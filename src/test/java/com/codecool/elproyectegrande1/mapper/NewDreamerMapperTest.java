package com.codecool.elproyectegrande1.mapper;

import com.codecool.elproyectegrande1.dto.dreamer.DreamerDto;
import com.codecool.elproyectegrande1.dto.dreamer.NewDreamerDto;
import com.codecool.elproyectegrande1.entity.Dreamer;
import com.codecool.elproyectegrande1.entity.Letter;
import com.codecool.elproyectegrande1.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;


class NewDreamerMapperTest {

    private final NewDreamerMapper newDreamerMapper = new NewDreamerMapper();

    @Test
    void shouldMapEntityToDreamerDto() {
        Dreamer dreamer = new Dreamer("test", "test", "password", new HashSet<>());
        dreamer.setId(1L);
        dreamer.setFollowers(10);

        HashSet<Letter> letters = new HashSet<>();
        letters.add(new Letter("halo bro how are you", "duc@gmail.com"));
        letters.add(new Letter("many from you, how is it?", "dawid@gmail.com"));
        dreamer.setLetters(letters);

        DreamerDto actual = newDreamerMapper.mapEntityToDreamerDto(dreamer);

        Assertions.assertEquals(dreamer.getId(), actual.getId());
        Assertions.assertEquals(dreamer.getFollowers(), actual.getFollowers());
        Assertions.assertEquals(dreamer.getUsername(), actual.getUsername());
        Assertions.assertEquals(dreamer.getLetters(), actual.getLetters());
    }

    @Test
    void shouldMapNewDreamerDtoToEntity() {

        NewDreamerDto newDreamerDto = new NewDreamerDto("test", "test", "password");

        HashSet<Letter> letters = new HashSet<>();
        letters.add(new Letter("halo bro how are you", "duc@gmail.com"));
        letters.add(new Letter("many from you, how is it?", "dawid@gmail.com"));
        newDreamerDto.setLetters(letters);

        Dreamer actual = newDreamerMapper.mapNewDreamerDtoToEntity(newDreamerDto);

        Assertions.assertEquals(newDreamerDto.getUsername(), actual.getUsername());
    }


    @Test
    void shouldMapUserToDreamer() {
        User user = new User("Username", "email", "password");

        Dreamer actual = newDreamerMapper.mapUserToDreamer(user);

        Assertions.assertEquals(user.getId(), actual.getId());
        Assertions.assertEquals(user.getEmail(), actual.getEmail());
    }

}