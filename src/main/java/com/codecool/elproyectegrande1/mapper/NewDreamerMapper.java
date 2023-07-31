package com.codecool.elproyectegrande1.mapper;

import com.codecool.elproyectegrande1.dto.dreamer.DreamerDto;
import com.codecool.elproyectegrande1.dto.dreamer.NewDreamerDto;
import com.codecool.elproyectegrande1.entity.Dreamer;
import com.codecool.elproyectegrande1.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;


@Component
public class NewDreamerMapper {
    public DreamerDto mapEntityToDreamerDto(Dreamer entity) {
        Long profilePictureId = null;
        if (entity.getProfilePicture() != null)
            profilePictureId = entity.getProfilePicture().getId();
        return new DreamerDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getImageUrl(),
                entity.getFollowers(),
                entity.getFunds(),
                profilePictureId
        );
    }

    public Dreamer mapNewDreamerDtoToEntity(NewDreamerDto dto) {
        return new Dreamer(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getLetters()
        );
    }

    public Dreamer mapUserToDreamer(User user) {
        return new Dreamer(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                new HashSet<>()
        );
    }
}