package com.codecool.elproyectegrande1.dto.dream;

import com.codecool.elproyectegrande1.dto.comment.CommentDto;
import com.codecool.elproyectegrande1.entity.DreamStatus;

import java.util.List;
import java.util.Set;

public class DreamDto {

    private Long id;
    private String dreamTitle;
    private String dreamDescription;
    private int likes = 0;
    private int views = 0;
    private DreamStatus dreamStatus;
    private List<String> hashtags;
    private Set<CommentDto> comments;
    private Long imageId;
    private Long dreamerId;

    public DreamDto() {
    }

    public DreamDto(Long id, String dreamTitle, String dreamDescription, List<String> hashtags, Long imageId, Set<CommentDto> comments, int likes, int views, Long dreamerId) {
        this.id = id;
        this.dreamTitle = dreamTitle;
        this.dreamDescription = dreamDescription;
        this.hashtags = hashtags;
        this.imageId = imageId;
        this.dreamStatus = DreamStatus.PRESENTING;
        this.comments = comments;
        this.likes = likes;
        this.views = views;
        this.dreamerId = dreamerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDreamTitle() {
        return dreamTitle;
    }

    public void setDreamTitle(String dreamTitle) {
        this.dreamTitle = dreamTitle;
    }

    public String getDreamDescription() {
        return dreamDescription;
    }

    public void setDreamDescription(String dreamDescription) {
        this.dreamDescription = dreamDescription;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() { return views; }

    public void setViews(int views) { this.views = views; }

    public DreamStatus getDreamStatus() {
        return dreamStatus;
    }

    public void setDreamStatus(DreamStatus dreamStatus) {
        this.dreamStatus = dreamStatus;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public Set<CommentDto> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDto> comments) {
        this.comments = comments;
    }

    public Long getImage() {
        return imageId;
    }

    public void setImage(Long imageId) {
        this.imageId = imageId;
    }

    public Long getDreamerId() {
        return dreamerId;
    }

    public void setDreamerId(Long dreamerId) {
        this.dreamerId = dreamerId;
    }
}
