package com.codecool.elproyectegrande1.dto.admin;

import com.codecool.elproyectegrande1.entity.User;

public class AdminDto {
    private Long id;
    private String username;
    private String email;

    public AdminDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public AdminDto() {
    }

    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
