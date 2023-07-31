package com.codecool.elproyectegrande1.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("dreamer")
public class Dreamer extends User {

    @Column(name = "followers", columnDefinition = "INT DEFAULT 0")
    private int followers;

    @Column(name = "funds", columnDefinition = "INT DEFAULT 0")
    private BigDecimal funds;

    @OneToMany(
            mappedBy = "dreamer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Letter> letters;

    @OneToMany(mappedBy = "dreamer", cascade = CascadeType.ALL)
    private List<Dream> dreams;

    public Dreamer() {
    }

    public Dreamer(String username, String email, String password, Set<Letter> letters) {
        super(username, email, password);
        this.letters = letters;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
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

    public void addDreamer(Dreamer dreamer) {
        this.getFollowedDreamers().add(dreamer);
        dreamer.getFollowedDreamers().add(this);
    }

    public void removeDreamer(Dreamer dreamer) {
        this.getFollowedDreamers().remove(dreamer);
        dreamer.getFollowedDreamers().remove(this);
    }

    public void addMentor(Mentor mentor) {
        this.getFollowedMentors().add(mentor);
    }

    public void removeMentor(Mentor mentor) {
        this.getFollowedMentors().remove(mentor);
    }

    @PreRemove
    public void removeDreamerFromFollowed() {
        for (Dreamer dreamer : getFollowedDreamers()) {
            dreamer.getFollowedDreamers().remove(this);
        }
    }
}