package com.codecool.elproyectegrande1.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("mentor")
public class Mentor extends User {

    @Column(name = "followers", columnDefinition = "INT DEFAULT 0")
    private int followers;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    private List<Offer> offers;

    @Column(name = "isVerified")
    private boolean isVerified = false;

    public Mentor() {
    }

    public Mentor(String username, String email, String password) {
        super(username, email, password);
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

    public void addMentor(Mentor mentor) {
        this.getFollowedMentors().add(mentor);
        mentor.getFollowedMentors().add(this);
    }

    public void removeMentor(Mentor mentor) {
        this.getFollowedMentors().remove(mentor);
        mentor.getFollowedMentors().remove(this);
    }

    public void addDreamer(Dreamer dreamer) {
        this.getFollowedDreamers().add(dreamer);
    }

    public void removeDreamer(Dreamer dreamer) {
        this.getFollowedDreamers().remove(dreamer);
    }

    @PreRemove
    public void removeMentorFromFollowed() {
        for (Mentor mentor : getFollowedMentors()) {
            mentor.getFollowedMentors().remove(this);
        }
    }
}
