package com.codecool.elproyectegrande1.dto.dreamer;

import com.codecool.elproyectegrande1.entity.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NewDreamerDto {
    private String username;
    private String email;
    private String password;
    private BigDecimal funds = new BigDecimal(0);
    private Set<Letter> letters;
    private String imageName;
    private Image image;
    private List<Dream> dreams = new ArrayList<>();
    private Set<String> followed;



    public NewDreamerDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String nickname) {
        this.username = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
}
