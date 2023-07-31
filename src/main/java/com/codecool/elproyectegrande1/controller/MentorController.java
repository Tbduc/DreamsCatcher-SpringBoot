package com.codecool.elproyectegrande1.controller;

import com.codecool.elproyectegrande1.dto.dream.DreamDto;
import com.codecool.elproyectegrande1.dto.dreamer.DreamerDto;
import com.codecool.elproyectegrande1.dto.mentor.MentorDto;
import com.codecool.elproyectegrande1.dto.offer.NewOfferDto;
import com.codecool.elproyectegrande1.dto.offer.OfferDto;
import com.codecool.elproyectegrande1.dto.user.UserDto;
import com.codecool.elproyectegrande1.service.ImageService;
import com.codecool.elproyectegrande1.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.security.Principal;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:8081/")
@RestController
@RequestMapping("/api/v1/mentors")
public class MentorController {

    private final MentorService mentorService;
    private final ImageService imageService;
    private boolean isLoggedIn = false;

    @Autowired
    public MentorController(MentorService mentorService, ImageService imageService) {
        this.mentorService = mentorService;
        this.imageService = imageService;
    }

    @PostMapping("/offer")
    public OfferDto addOffer(@RequestBody NewOfferDto offerDto, Principal principal) {
        String name = principal.getName();
        return mentorService.addOffer(name, offerDto);
    }

    @GetMapping("/all")
    public List<UserDto> getAllMentors() {
        return mentorService.getAllMentors();
    }

    @GetMapping("/unverified")
    public List<UserDto> getAllUnverifiedMentors() {
        return mentorService.getAllUnverifiedMentors();
    }

    @GetMapping("/{nickname}")
    public MentorDto getMentorByNickname(@PathVariable("nickname") String nickname) {
        return mentorService.getMentorByNickname(nickname);
    }

//    @GetMapping("/{id}")
//    public MentorDto getMentor(@PathVariable("id") String id) {
//        return mentorService.getMentor(id);
//    }

    @GetMapping("/{nickname}/offers")
    public List<OfferDto> getAllOffersByMentorNickname(@PathVariable("nickname") String nickname) {
        return mentorService.getAllOffersByMentorNickname(nickname);
    }

    @PutMapping("/{nickname}/follow")
    public ResponseEntity<String> followMentor(@PathVariable("nickname") String nickname, Principal principal) {
        String name = principal.getName();
        System.out.println(name);
        mentorService.followMentor(nickname, name);
        return new ResponseEntity<>("Followed successfully!", HttpStatus.OK);
    }

    @PutMapping("/{nickname}/unfollow")
    public ResponseEntity<String> unfollowMentor(@PathVariable String nickname, Principal principal) {
        String name = principal.getName();
        mentorService.unfollowMentor(nickname, name);
        return new ResponseEntity<>("You have unfollowed this dreamer", HttpStatus.OK);
    }

}
