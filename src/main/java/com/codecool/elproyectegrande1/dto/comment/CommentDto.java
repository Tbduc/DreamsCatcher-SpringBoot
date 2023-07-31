package com.codecool.elproyectegrande1.dto.comment;

import com.codecool.elproyectegrande1.entity.Dream;

import java.time.LocalDateTime;


public class CommentDto {

    private Long id;
    private String comment;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private int likes = 0;

    private String username;

    private Dream dream;
    private Long userId;

    public CommentDto(Long id, String comment, LocalDateTime createdDate, LocalDateTime updatedDate, int likes, String username, Long userId) {
        this.id = id;
        this.comment = comment;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.likes = likes;
        this.username = username;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public int getLikes() {
        return likes;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Dream getDream() {
        return dream;
    }

    public void setDream(Dream dream) {
        this.dream = dream;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
