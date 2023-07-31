package com.codecool.elproyectegrande1.dto.offer;

import com.codecool.elproyectegrande1.entity.EOffer;
import com.codecool.elproyectegrande1.entity.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class NewOfferDto {
    private EOffer type;
    private String title;
    private String description;
    private BigDecimal price;
    private LocalDate date;
    @JsonIgnore
    private Image image;
    private String imageName;
    private int likes = 0;
    private List<String> comments;

    private Long userId;

    public NewOfferDto() {
    }

    public NewOfferDto(EOffer type, String title, String description, BigDecimal price, LocalDate date, String imageName, Long userId) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.date = date;
        this.imageName = imageName;
        this.userId = userId;
    }

    public EOffer getType() {
        return type;
    }

    public void setType(EOffer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
