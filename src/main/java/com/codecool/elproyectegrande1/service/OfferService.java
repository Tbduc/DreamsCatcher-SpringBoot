package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.dto.offer.OfferDto;
import com.codecool.elproyectegrande1.entity.Offer;
import com.codecool.elproyectegrande1.mapper.OfferMapper;
import com.codecool.elproyectegrande1.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;

    @Autowired
    public OfferService(OfferRepository offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    public OfferDto getOfferById(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        return offerMapper.mapEntityToOfferDto(offer);
    }

    public List<OfferDto> getAllOffers() {
        List<Offer> offers = offerRepository.findAll();
        return offers.stream()
                .map(offerMapper::mapEntityToOfferDto)
                .collect(Collectors.toList());
    }

    public void likeOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        offer.setLikes(offer.getLikes() + 1);
        offerRepository.save(offer);
    }

    public void dislikeOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        offer.setLikes(offer.getLikes() - 1);
        offerRepository.save(offer);
    }

    public void viewsOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        offer.setViews(offer.getViews() + 1);
        offerRepository.save(offer);
    }
}
