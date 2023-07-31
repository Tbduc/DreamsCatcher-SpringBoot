package com.codecool.elproyectegrande1.jwt.payload.response;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;

    private Long profilePictureId;
    private List<String> roles;

    private String imageUrl;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles, Long profilePictureId, String imageUrl) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.profilePictureId = profilePictureId;
        this.imageUrl = imageUrl;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public List<String> getRoles() {
        return roles;
    }

    public Long getProfilePictureId() {
        return profilePictureId;
    }

    public void setProfilePictureId(Long profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

