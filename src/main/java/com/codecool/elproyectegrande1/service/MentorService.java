package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.dto.mentor.MentorDto;
import com.codecool.elproyectegrande1.dto.offer.NewOfferDto;
import com.codecool.elproyectegrande1.dto.offer.OfferDto;
import com.codecool.elproyectegrande1.dto.user.UserDto;
import com.codecool.elproyectegrande1.entity.*;
import com.codecool.elproyectegrande1.mapper.MentorMapper;
import com.codecool.elproyectegrande1.mapper.OfferMapper;
import com.codecool.elproyectegrande1.mapper.UserMapper;
import com.codecool.elproyectegrande1.repository.ImageRepository;
import com.codecool.elproyectegrande1.repository.OfferRepository;
import com.codecool.elproyectegrande1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MentorService {

    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final UserMapper userMapper;
    private final MentorMapper mentorMapper;
    private final OfferMapper offerMapper;
    private final ImageRepository imageRepository;

    @Autowired
    public MentorService(UserRepository userRepository, OfferRepository offerRepository, UserMapper userMapper, MentorMapper mentorMapper, OfferMapper offerMapper, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.userMapper = userMapper;
        this.mentorMapper = mentorMapper;
        this.offerMapper = offerMapper;
        this.imageRepository = imageRepository;
    }

    public OfferDto addOffer(String name, NewOfferDto offerDto) {
        Mentor mentor = (Mentor) userRepository.findByUsername(name)
                .orElseThrow(() -> new IllegalArgumentException("Mentor with nickname " + name + " not found"));

        Offer offer = offerMapper.mapOfferDtoToEntity(offerDto);
        offer.setMentor(mentor);

        Offer savedOffer = offerRepository.save(offer);

        List<Offer> updatedOffers = mentor.getOffers();
        updatedOffers.add(savedOffer);
        userRepository.save(mentor);

        return offerMapper.mapEntityToOfferDto(savedOffer);
    }

    public void createMentorFromUser(User user, Set<Role> roles) {
        Mentor mentor = mentorMapper.mapUserToMentor(user);
        mentor.setRoles(roles);
        userRepository.save(mentor);
    }

    public List<UserDto> getAllMentors() {
        List<User> mentors = userRepository.findAll();
        return mentors.stream()
                .filter(user -> user instanceof Mentor)
                .map(userMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public MentorDto getMentor(String user_id) {
        Mentor mentor = (Mentor) userRepository.findByUsername(user_id)
                .orElseThrow(() -> new IllegalArgumentException("Mentor with nickname " + user_id + " not found"));
        System.out.println(mentor.isVerified());
        return null;
    }

    public List<OfferDto> getAllOffersByMentorNickname(String nickname) {
            List<Offer> offers = offerRepository.findByMentorUsername(nickname);

            List<OfferDto> offerDtos = new ArrayList<>();

            for (int i = 0; i < 8 && i < offers.size(); i++) {
                OfferDto dto = offerMapper.mapEntityToOfferDto(offers.get(i));
                offerDtos.add(dto);
            }

            return offerDtos;
    }

    public MentorDto getMentorByNickname(String nickname) {
            Mentor mentor = (Mentor) userRepository.findByUsername(nickname)
                    .orElseThrow(() -> new IllegalArgumentException("Mentor with username " + nickname + " not found"));
            return mentorMapper.mapEntityToDto(mentor);
    }

    public void followMentor(String nickname, String name) {
        Mentor mentor = (Mentor) userRepository.findByUsername(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Mentor with username " + nickname + " not found"));
        User user = userRepository.findByUsername(name).orElse(null);
        User userGoogle = null;

        if (user == null)
            userGoogle = userRepository.findByEmail(name).orElseThrow();

        if(!(user == null))
            followDreamerOrMentor(name, mentor, user);
        else if (userGoogle != null)
            followDreamerOrMentor(userGoogle.getUsername(), mentor, userGoogle);

        mentor.setFollowers(mentor.getFollowers() + 1);
        userRepository.save(mentor);
    }

    public void unfollowMentor(String nickname, String name) {
        Mentor toBeUnfollowed = (Mentor) userRepository.findByUsername(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Mentor with username " + nickname + " not found"));
        User user= userRepository.findByUsername(name).orElse(null);

        if (user != null)
            checkIfUnfollowed(toBeUnfollowed, name, user);
        else {
            user = userRepository.findByEmail(name).orElse(null);
            if (user != null)
                checkIfUnfollowed(toBeUnfollowed, user.getUsername(), user);
        }

        if (toBeUnfollowed.getFollowers() == 0)
            toBeUnfollowed.setFollowers(1);

        toBeUnfollowed.setFollowers(toBeUnfollowed.getFollowers() - 1);
        userRepository.save(toBeUnfollowed);
    }

    private void checkIfUnfollowed(Mentor toBeUnfollowed, String name, User user) {
        Dreamer dreamer;
        Mentor mentor;
        if (user instanceof Mentor) {
            mentor = (Mentor) userRepository.findByUsername(name).orElse(null);
            if (mentor != null) {
                if (toBeUnfollowed.getFollowedMentors() != null && !toBeUnfollowed.getFollowedMentors().contains(mentor)) {
                    throw new IllegalArgumentException("You are not following this mentor");
                } else
                    toBeUnfollowed.removeMentor(mentor);
                    mentor.removeMentor(toBeUnfollowed);
                    mentor.removeMentorFromFollowed();
            }

        }

        if (user instanceof Dreamer) {
            dreamer = (Dreamer) userRepository.findByUsername(name).orElse(null);
            if (dreamer != null) {
                if (toBeUnfollowed.getFollowedDreamers() != null && !toBeUnfollowed.getFollowedDreamers().contains(dreamer)) {
                    throw new IllegalArgumentException("You are not following this mentor");
                } else {
                    toBeUnfollowed.removeDreamer(dreamer);
                    dreamer.removeDreamerFromFollowed();
                }
            }
        }
    }


    private void followDreamerOrMentor(String name, Mentor toBeFollowed, User user) {
        Mentor mentor;
        Dreamer dreamer;

        if (user instanceof Dreamer) {
            dreamer = (Dreamer) userRepository.findByUsername(name).orElse(null);
            if (dreamer == null)
                dreamer = (Dreamer) userRepository.findByEmail(name).orElse(null);
            if (dreamer != null)
                if (toBeFollowed.getFollowedDreamers().contains(dreamer)) {
                    throw new IllegalArgumentException("You are already following this mentor");
                }
                else {
                    toBeFollowed.getFollowedDreamers().add(dreamer);
                }
        }

        if (user instanceof Mentor) {
            mentor = (Mentor) userRepository.findByUsername(name).orElse(null);
            if (mentor == null)
                mentor = (Mentor) userRepository.findByEmail(name).orElse(null);
            if (mentor != null)
                if (toBeFollowed.getFollowedMentors().contains(mentor)) {
                    throw new IllegalArgumentException("You are already following this mentor");
                }
                else {
                    toBeFollowed.getFollowedMentors().add(mentor);
                }
        }
    }

    public List<UserDto> getAllUnverifiedMentors() {
        List<User> mentors = userRepository.findAll()
                .stream()
                .filter(user -> user instanceof Mentor)
                .collect(Collectors.toList());
        return mentors.stream()
                .map(mentor -> (Mentor) mentor)
                .filter(mentor -> !mentor.isVerified())
                .map(userMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }
}

