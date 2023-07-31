package com.codecool.elproyectegrande1.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("offers")
    private Mentor mentor;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EOffer type;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private LocalDate date;

//    @OneToMany(
//            mappedBy = "offer",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    @JsonManagedReference
//    private List<Image> images;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonManagedReference("offer-image")
    private Image mainImage;

    @Column(name = "likes", columnDefinition = "INT DEFAULT 0")
    private int likes;

    @Column(name = "views", columnDefinition = "INT DEFAULT 0")
    private int views;

    @OneToMany(
            mappedBy = "dream",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Comment> comments;

    public Offer() {
    }

    public Offer(EOffer type, String title, String description, BigDecimal price, LocalDate date, Image mainImage) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.date = date;
        this.mainImage = mainImage;
        this.likes = 0;
        this.views = 0;
        this.comments = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
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

//    public List<Image> getImages() {
//        return images;
//    }
//
//    public void setImages(List<Image> images) {
//        this.images = images;
//    }

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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Image getMainImage() {
        return mainImage;
    }

    public void setMainImage(Image mainImage) {
        this.mainImage = mainImage;
    }
}
