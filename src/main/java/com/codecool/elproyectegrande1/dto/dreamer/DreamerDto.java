package com.codecool.elproyectegrande1.dto.dreamer;

import com.codecool.elproyectegrande1.entity.Dream;
import com.codecool.elproyectegrande1.entity.Image;
import com.codecool.elproyectegrande1.entity.Letter;
import com.codecool.elproyectegrande1.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DreamerDto {
    private Long id;
    private String username;
    private String email;
    private int followers = 0;
    private BigDecimal funds;
    private Set<Letter> letters;
    private Long profilePictureId;
    private String profileImgUrl;
    private List<Dream> dreams = new ArrayList<>();
    private Set<String> followed;

    public DreamerDto() {
    }

    public DreamerDto(Long id, String username, String email, String profileImgUrl, int followers, BigDecimal funds, Long profilePictureId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.followers = followers;
        this.funds = funds;
        this.profilePictureId = profilePictureId;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
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


    public BigDecimal getFunds() {
        return funds;
    }

    public void setFunds(BigDecimal funds) {
        this.funds = funds;
    }

    public Set<Letter> getLetters() {
        return letters;
    }

    public void setLetters(Set<Letter> letters) {
        this.letters = letters;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public Long getProfilePicture() {
        return profilePictureId;
    }

    public void setProfilePicture(Long profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    public List<Dream> getDreams() {
        return dreams;
    }

    public void setDreams(List<Dream> dreams) {
        this.dreams = dreams;
    }

    public Set<String> getFollowed() {
        return followed;
    }

    public void setFollowed(Set<String> followed) {
        this.followed = followed;
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
}
