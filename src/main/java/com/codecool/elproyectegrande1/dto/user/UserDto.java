package com.codecool.elproyectegrande1.dto.user;

import com.codecool.elproyectegrande1.entity.Dream;
import com.codecool.elproyectegrande1.entity.Image;
import com.codecool.elproyectegrande1.entity.Letter;
import com.codecool.elproyectegrande1.entity.Offer;
import com.codecool.elproyectegrande1.util.PasswordMatches;
import com.codecool.elproyectegrande1.util.ValidEmail;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@PasswordMatches
public class UserDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1)
    private String username;

    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

    private boolean isUsing2FA;
    private String profileImgUrl;
    private List<String> roles;

    private Long profilePictureId;

    private int followers = 0;
    private List<Offer> offers = new ArrayList<>();

    private boolean isVerified = false;
    private Set<String> followed;
    private BigDecimal funds;
    private Set<Letter> letters;
    private List<Dream> dreams = new ArrayList<>();
    private List<Long> likedDreamsIds;

    public UserDto() {
    }

    public UserDto(Long id, String username, String email, String profileImgUrl, Long profilePictureId, List<String> roles, List<Long> likedDreamsIds) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.profilePictureId = profilePictureId;
        this.roles = roles;
        this.likedDreamsIds = likedDreamsIds;
    }

    public String getEmail() {
        return email;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public List<String> getRole() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(final String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public boolean isUsing2FA() {
        return isUsing2FA;
    }

    public void setUsing2FA(boolean isUsing2FA) {
        this.isUsing2FA = isUsing2FA;
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

    public List<String> getRoles() {
        return roles;
    }
    public int getFollowers() {
        return followers;
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

    public List<Dream> getDreams() {
        return dreams;
    }

    public void setDreams(List<Dream> dreams) {
        this.dreams = dreams;
    }

    public List<Long> getLikedDreamsIds() {
        return likedDreamsIds;
    }

    public void setLikedDreamsIds(List<Long> likedDreamsIds) {
        this.likedDreamsIds = likedDreamsIds;
    }
}
