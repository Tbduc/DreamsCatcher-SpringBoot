package com.codecool.elproyectegrande1.mapper;

import com.codecool.elproyectegrande1.dto.mentor.MentorDto;
import com.codecool.elproyectegrande1.entity.Mentor;
import com.codecool.elproyectegrande1.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;


class MentorMapperTest {

    private final MentorMapper mentorMapper = new MentorMapper();

    @Test
    void shouldMapUserToMentor() {
        User user = new User("Username", "email", "password");

        Mentor actual = mentorMapper.mapUserToMentor(user);

        Assertions.assertEquals(user.getEmail(), actual.getEmail());
    }
}
