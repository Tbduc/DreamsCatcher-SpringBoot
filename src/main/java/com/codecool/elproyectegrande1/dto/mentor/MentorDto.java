package com.codecool.elproyectegrande1.dto.mentor;

import com.codecool.elproyectegrande1.entity.Image;
import com.codecool.elproyectegrande1.entity.Letter;
import com.codecool.elproyectegrande1.entity.Offer;
import com.codecool.elproyectegrande1.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MentorDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private int followers = 0;
    private Long profilePictureId;
    private String profileImgUrl;
    private List<Offer> offers = new ArrayList<>();

    private boolean isVerified = false;
    private Set<String> followed;

    public MentorDto() {
    }

    public MentorDto(Long id, String username, String email, String profileImgUrl, int followers, Long profilePictureId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.followers = followers;
        this.profilePictureId = profilePictureId;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getProfilePictureId() {
        return profilePictureId;
    }

    public void setProfilePictureId(Long profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public String getPassword() {
        return password;
    }

    public int getFollowers() {
        return followers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Set<String> getFollowed() {
        return followed;
    }

    public void setFollowed(Set<String> followed) {
        this.followed = followed;
    }
}
