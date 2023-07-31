package com.codecool.elproyectegrande1.mapper;

import com.codecool.elproyectegrande1.dto.mentor.MentorDto;
import com.codecool.elproyectegrande1.entity.Mentor;
import com.codecool.elproyectegrande1.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class MentorMapper {

    public Mentor mapUserToMentor(User user) {
        return new Mentor(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public MentorDto mapEntityToDto(Mentor entity) {
        return new MentorDto (
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getImageUrl(),
                entity.getFollowers(),
                entity.getProfilePicture().getId()
        );
    }
}
