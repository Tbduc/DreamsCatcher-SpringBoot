package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.dto.dream.DreamDto;
import com.codecool.elproyectegrande1.dto.dreamer.DreamerDto;
import com.codecool.elproyectegrande1.dto.dreamer.NewDreamerDto;
import com.codecool.elproyectegrande1.dto.user.UserDto;
import com.codecool.elproyectegrande1.entity.*;
import com.codecool.elproyectegrande1.mapper.DreamMapper;
import com.codecool.elproyectegrande1.mapper.NewDreamerMapper;
import com.codecool.elproyectegrande1.mapper.UserMapper;
import com.codecool.elproyectegrande1.repository.DreamRepository;
import com.codecool.elproyectegrande1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DreamerService {

    private final UserRepository userRepository;
    private final DreamRepository dreamRepository;
    private final NewDreamerMapper dreamerMapper;
    private final UserMapper userMapper;
    private final DreamMapper dreamMapper;
    private final UserService userService;

    @Autowired
    public DreamerService(UserRepository userRepository, DreamRepository dreamRepository, NewDreamerMapper dreamerMapper, UserMapper userMapper, DreamMapper dreamMapper, UserService userService) {
        this.userRepository = userRepository;
        this.dreamRepository = dreamRepository;
        this.dreamerMapper = dreamerMapper;
        this.userMapper = userMapper;
        this.dreamMapper = dreamMapper;
        this.userService = userService;
    }

    public DreamerDto createDreamer(NewDreamerDto newDreamerDto) {
        Dreamer dreamer = dreamerMapper.mapNewDreamerDtoToEntity(newDreamerDto);
        Dreamer savedDreamer = userRepository.save(dreamer);
        return dreamerMapper.mapEntityToDreamerDto(savedDreamer);
    }

    public Dreamer getDreamerWithMostFollowers() {
        return userRepository.findTopByOrderByFollowersDesc();
    }

    public DreamerDto getDreamerByNickname(String nickname) {
        Dreamer dreamer = (Dreamer) userRepository.findByUsername(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Dreamer with id " + nickname + " not found"));
        return dreamerMapper.mapEntityToDreamerDto(dreamer);
    }

    public void followDreamer(String nickname, String name) {
        Dreamer toBeFollowed = (Dreamer) userRepository.findByUsername(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Dreamer with id " + nickname + " not found"));
        User user = userRepository.findByUsername(name).orElse(null);
        User userGoogle = null;

        if (user == null)
            userGoogle = userRepository.findByEmail(name).orElseThrow();

        if (!(user == null))
            followDreamerOrMentor(name, toBeFollowed, user);
        else if (userGoogle != null)
            followDreamerOrMentor(userGoogle.getUsername(), toBeFollowed, userGoogle);
        toBeFollowed.setFollowers(toBeFollowed.getFollowers() + 1);
        userRepository.save(toBeFollowed);
    }

    private void followDreamerOrMentor(String name, Dreamer toBeFollowed, User user) {
        Mentor mentor;
        Dreamer dreamer;

        if (user instanceof Dreamer) {
            dreamer = (Dreamer) userRepository.findByUsername(name).orElse(null);
            if (dreamer != null) {
                if (toBeFollowed.getFollowedDreamers() != null &&toBeFollowed.getFollowedDreamers().contains(dreamer)) {
                    throw new IllegalArgumentException("You are already following this dreamer");
                } else {
                    toBeFollowed.getFollowedDreamers().add(dreamer);
                }
            }
        }

        if (user instanceof Mentor) {
            mentor = (Mentor) userRepository.findByUsername(name).orElse(null);
            if (mentor != null) {
                if (toBeFollowed.getFollowedMentors() != null && toBeFollowed.getFollowedMentors().contains(mentor)) {
                    throw new IllegalArgumentException("You are already following this mentor");
                } else {
                    toBeFollowed.getFollowedMentors().add(mentor);
                }
            }
        }
    }

    public void unfollowDreamer(String nickname, String name) {
        Dreamer dreamer = (Dreamer) userRepository.findByUsername(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Dreamer with id " + nickname + " not found"));
        User user = userRepository.findByUsername(name).orElse(null);
        if (user != null)
            checkIfUnfollowed(dreamer,name, user);
        else {
            user = userRepository.findByEmail(name).orElse(null);
            if (user != null)
                checkIfUnfollowed(dreamer, name, user);
        }
        if (dreamer.getFollowers() == 0)
            dreamer.setFollowers(1);
        dreamer.setFollowers(dreamer.getFollowers() - 1);
        userRepository.save(dreamer);
    }

    private void checkIfUnfollowed(Dreamer toBeUnfollowed, String name, User user) {
        Dreamer dreamer;
        Mentor mentor;
        if (user instanceof Mentor) {
            mentor = (Mentor) userRepository.findByUsername(name).orElse(null);
            if (mentor != null)
                if (!toBeUnfollowed.getFollowedMentors().contains(mentor)) {
                    throw new IllegalArgumentException("You are not following this dreamer");
                } else {
                    toBeUnfollowed.removeMentor(mentor);
                    mentor.removeDreamer(toBeUnfollowed);
                    mentor.removeMentorFromFollowed();
                }
        }
        if (user instanceof Dreamer) {
            dreamer = (Dreamer) userRepository.findByUsername(name).orElse(null);
            if (dreamer != null)
                if (!toBeUnfollowed.getFollowedDreamers().contains(dreamer)) {
                    throw new IllegalArgumentException("You are not following this dreamer");
                } else {
                    toBeUnfollowed.removeDreamer(dreamer);
                    dreamer.removeDreamerFromFollowed();
                }
        }
    }

    public void donateDreamer(Long dreamerId, BigDecimal amount) {
        Dreamer dreamer = (Dreamer) userRepository.findById(dreamerId).orElseThrow();
        if(dreamer.getFunds() != null)
            dreamer.setFunds(amount.add(dreamer.getFunds()));
        else
            dreamer.setFunds(amount);
        userRepository.save(dreamer);
    }

    public void createDreamerFromUser(User user, Set<Role> roles) {
        Dreamer dreamer = dreamerMapper.mapUserToDreamer(user);
        dreamer.setRoles(roles);
        userRepository.save(dreamer);

        //send confirmation email to the user
        userService.sendConfirmationEmail(dreamer);
    }

    public List<DreamDto> getAllDreamsByDreamerNickname(String nickname) {
        List<Dream> dreams = dreamRepository.findByDreamerUsername(nickname);

        List<DreamDto> dreamDtos = new ArrayList<>();

        for (int i = 0; i < 8 && i < dreams.size(); i++) {
            DreamDto dto = dreamMapper.mapEntityToDreamDto(dreams.get(i));
            dreamDtos.add(dto);
        }

        return dreamDtos;
    }

    public UserDto findDreamerByUserId(Long userId) {
        User dreamer = userRepository.findById(userId).orElseThrow();
        UserDto user = userMapper.mapEntityToDto(dreamer);
        return user;
    }

    public User findDreamerByDreamId(Long dreamId) {
        Dream dream = dreamRepository.findById(dreamId).orElseThrow();
        User dreamer = userRepository.findById(dream.getDreamer().getId()).orElseThrow();
        return dreamer;
    }
}
