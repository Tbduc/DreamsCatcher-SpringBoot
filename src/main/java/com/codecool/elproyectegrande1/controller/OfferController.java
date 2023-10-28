package com.codecool.elproyectegrande1.controller;

import com.codecool.elproyectegrande1.dto.comment.CommentDto;
import com.codecool.elproyectegrande1.dto.comment.NewCommentDto;
import com.codecool.elproyectegrande1.dto.offer.OfferDto;
import com.codecool.elproyectegrande1.service.OfferService;
import com.codecool.elproyectegrande1.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://dreams-catcher.onrender.com/, http://localhost:8081/")
@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

    private final OfferService offerService;
    private final CommentService commentService;

    @Autowired
    public OfferController(OfferService offerService, CommentService commentService) {
        this.offerService = offerService;
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public OfferDto getOfferById(@PathVariable("id") Long id) {
        offerService.viewsOffer(id);
        return offerService.getOfferById(id);
    }

    @GetMapping("/all")
    public List<OfferDto> getAllOffers() {
        return offerService.getAllOffers();
    }

    @PutMapping("/{id}/like")
    public void likeOffer(@PathVariable Long id) {
        offerService.likeOffer(id);
    }

    @PutMapping("/{id}/dislike")
    public void dislikeOffer(@PathVariable Long id) {
        offerService.dislikeOffer(id);
    }

    @GetMapping("/{id}/offer")
    public void viewOffer(@PathVariable Long id) {
        offerService.viewsOffer(id);
    }

    @PostMapping("/{id}/comment")
    public CommentDto addComment(@RequestBody NewCommentDto newCommentDto, @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return commentService.addComment(newCommentDto, username, id);
    }
}

