package com.codecool.elproyectegrande1.controller;

import com.codecool.elproyectegrande1.dto.comment.CommentDto;
import com.codecool.elproyectegrande1.dto.comment.NewCommentDto;
import com.codecool.elproyectegrande1.dto.dream.DreamDto;
import com.codecool.elproyectegrande1.dto.dream.NewDreamDto;
import com.codecool.elproyectegrande1.entity.Dream;
import com.codecool.elproyectegrande1.jwt.payload.response.MessageResponse;
import com.codecool.elproyectegrande1.service.CommentService;
import com.codecool.elproyectegrande1.service.DreamService;
import com.codecool.elproyectegrande1.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081/")
@RestController
@RequestMapping("/api/v1/dreams")
public class DreamController {

    private final DreamService dreamService;
    private final ImageService imageService;
    private final CommentService commentService;

    @Autowired
    public DreamController(DreamService dreamService, ImageService imageService, CommentService commentService) {
        this.dreamService = dreamService;
        this.imageService = imageService;
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public DreamDto createNewDream(@RequestBody NewDreamDto newDreamDto, Principal principal) {
        String name = principal.getName();
        return dreamService.addDream(name, newDreamDto);
    }

    @GetMapping("/{id}")
    public DreamDto getDreamById(@PathVariable("id") Long id) {
        dreamService.viewsDream(id);
        DreamDto dreamDto = dreamService.getDreamById(id);
        dreamDto.setComments(commentService.getAllCommentsByDreamId(id));
        return dreamService.getDreamById(id);
    }

    @GetMapping("/all")
    public List<DreamDto> getAllDreams() {

        List<DreamDto> all = dreamService.getAllDreams();
        return all;
    }

    @GetMapping("/recents")
    public List<DreamDto> getEightRecentDreams() {
        return dreamService.getLastEightDreams();
    }

    @PutMapping("/{id}/like")
    public void likeDream(@PathVariable Long id) {
        dreamService.likeDream(id);
    }

    @PutMapping("/{id}/dislike")
    public void dislikeDream(@PathVariable Long id) {
        dreamService.dislikeDream(id);
    }

    @GetMapping("/most-liked")
    public Dream getDreamWithMostLikes() {
        return dreamService.getDreamWithMostLikes();
    }

    @RolesAllowed({"ROLE_DREAMER", "ROLE_MENTOR", "ROLE_ADMIN"})
    @GetMapping("/most-viewed")
    public Dream getDreamWithMostViews() {
        return dreamService.getDreamWithMostViews();
    }

    @GetMapping("/most-popular")
    public List<DreamDto> getMostPopularDreams(Principal principal) {
        return dreamService.getTop3DreamsByLikes();
    }

    @RolesAllowed({"ROLE_DREAMER", "ROLE_ADMIN"})
    @PutMapping("{id}/dream-status/{status}")
    public void updateStatus(@PathVariable Long id, @PathVariable String status) { dreamService.updateDreamStatus(id, status); }

    @GetMapping("/{id}/dream")
    public void viewDream(@PathVariable Long id) {
        dreamService.viewsDream(id);
    }

    @RolesAllowed({"ROLE_DREAMER", "ROLE_ADMIN"})
    @PutMapping("{id}/update-dream/{title}/{description}/{status}")
    public void updateStatus(@PathVariable Long id, @PathVariable String title, @PathVariable String description, @PathVariable String status) {
        dreamService.updateDream(id, title, description, status);
    }

    @GetMapping("/search/{hashtag}")
    public List<DreamDto> searchDreamsByHashtag(@PathVariable String hashtag) {
        return dreamService.searchDreamsByHashtag(hashtag);
    }

    @RolesAllowed({"ROLE_DREAMER", "ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteDreamById(@PathVariable("id") Long id) {
        dreamService.deleteDreamById(id);
        return ResponseEntity.ok().body(new MessageResponse("Dream deleted successfully!"));
    }

    @RolesAllowed({"ROLE_DREAMER", "ROLE_MENTOR", "ROLE_ADMIN"})
    @PostMapping("/{id}/comment")
    public CommentDto addComment(@RequestBody NewCommentDto newCommentDto, @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        return commentService.addComment(newCommentDto, username, id);
    }
}
