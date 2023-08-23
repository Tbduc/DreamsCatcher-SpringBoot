package com.codecool.elproyectegrande1.controller;


import com.codecool.elproyectegrande1.dto.comment.CommentDto;
import com.codecool.elproyectegrande1.jwt.payload.response.MessageResponse;
import com.codecool.elproyectegrande1.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PutMapping("/{id}/like")
    public void likeComment(@PathVariable Long id) {
        commentService.likeComment(id);
    }

    @PutMapping("{id}/update-comment/{comment}/{updatedDate}")
    public void updateComment(@PathVariable Long id, @PathVariable String comment, @PathVariable LocalDateTime updatedDate) {
        commentService.updateComment(id, comment, updatedDate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCommentById(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().body(new MessageResponse("Comment deleted successfully!"));
    }

    @GetMapping("/dream/{dreamId}")
    public List<CommentDto> getAllCommentsByDream(@PathVariable Long dreamId) {
        return commentService.getAllCommentsById(dreamId);
    }
}
