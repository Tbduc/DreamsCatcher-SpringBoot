package com.codecool.elproyectegrande1.dto.dream;

import com.codecool.elproyectegrande1.entity.Comment;
import com.codecool.elproyectegrande1.entity.DreamStatus;
import com.codecool.elproyectegrande1.entity.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NewDreamDto {

    private String dreamTitle;
    private String dreamDescription;
    private DreamStatus dreamStatus;
    private List<String> hashtags;
    private Set<Comment> comments;
    private String imageName;

    @JsonIgnore
    private Image image;
    private Long dreamerId;

    public NewDreamDto() {
    }

    public NewDreamDto(String dreamTitle, String dreamDescription, List<String> hashtags, String imageName, Long dreamerId) {
        this.dreamTitle = dreamTitle;
        this.dreamDescription = dreamDescription;
        this.dreamStatus = DreamStatus.PRESENTING;
        this.hashtags = hashtags;
//        this.comments = new HashSet<>();
        this.imageName = imageName;
        this.dreamerId = dreamerId;
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

    public Set<Comment> getComments() {
        return comments;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Long getDreamerId() {
        return dreamerId;
    }

    public void setDreamerId(Long dreamerId) {
        this.dreamerId = dreamerId;
    }
}

