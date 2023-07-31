package com.codecool.elproyectegrande1.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Letter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 10, max = 350)
    private String content;

    private LocalDateTime dateTime;

    private String sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("letters")
    private Dreamer dreamer;

    public Letter(String content, String sender) {
        this.content = content;
        this.dateTime = LocalDateTime.now();;
        this.sender = sender;
    }

    public Letter() {
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Dreamer getDreamer() {
        return dreamer;
    }

    public void setDreamer(Dreamer dreamer) {
        this.dreamer = dreamer;
    }
}
