package com.codecool.elproyectegrande1.mapper;

import com.codecool.elproyectegrande1.dto.user.UserDto;
import com.codecool.elproyectegrande1.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDto mapEntityToDto(User user) {
        Long profilePictureId = null;
        if (user.getProfilePicture() != null)
            profilePictureId = user.getProfilePicture().getId();
        return new UserDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getImageUrl(),
            profilePictureId,
            user.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList()),
            user.getLikedDreams().stream()
                    .map(dream -> dream.getId())
                    .toList()
        );
    }

}
