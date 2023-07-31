package com.codecool.elproyectegrande1.dto.offer;

import com.codecool.elproyectegrande1.dto.comment.CommentDto;
import com.codecool.elproyectegrande1.entity.EOffer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class OfferDto {

    private Long id;
    private EOffer type;
    private String title;
    private String description;
    private BigDecimal price;
    private LocalDate date;
    private Long image;
    private int likes = 0;
    private int views = 0;
    private Set<CommentDto> comments;

    public OfferDto() {
    }

    public OfferDto(Long id, EOffer type, String title, String description, BigDecimal price, LocalDate date, Long image, int likes, int views, Set<CommentDto> comments) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.date = date;
        this.image = image;
        this.likes = likes;
        this.views = views;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Set<CommentDto> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDto> comments) {
        this.comments = comments;
    }
}
