package com.codecool.elproyectegrande1.dto.image;

import com.codecool.elproyectegrande1.entity.Dream;
import com.codecool.elproyectegrande1.entity.Offer;

public class ImageDto {
    private Long id;
    private String name;
    private String type;
    private Offer offer;
    private Dream dream;

    public ImageDto(Long id, String name, String type, Offer offer, Dream dream) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.offer = offer;
        this.dream = dream;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Dream getDream() {
        return dream;
    }

    public void setDream(Dream dream) {
        this.dream = dream;
    }
}
