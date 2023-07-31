package com.codecool.elproyectegrande1.dto.letter;


import java.time.LocalDateTime;

public class LetterDto {

    private Long id;
    private String content;
    private LocalDateTime dateTime;
    private String sender;

    public LetterDto(String content, LocalDateTime dateTime, String sender) {
        this.id = id;
        this.content = content;
        this.dateTime = dateTime;
        this.sender = sender;
    }

    public LetterDto() {
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
