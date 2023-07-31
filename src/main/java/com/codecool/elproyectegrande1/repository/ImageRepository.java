package com.codecool.elproyectegrande1.repository;

import com.codecool.elproyectegrande1.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByName(String fileName);

    @Query("SELECT i FROM Image i WHERE i.id = :id")
    Image findImageByID(Long id);

    @Query("SELECT i FROM Image i WHERE i.name = :fileName AND i.id = (SELECT max(id) from Image)")
    Image findImageByName(String fileName);

    @Query("SELECT i FROM Image i JOIN Dream d ON d.id = :dreamId WHERE i.name = :fileName")
    Image findImageByNameAndDreamId(String fileName, Long dreamId);
    @Query("SELECT i FROM Image i JOIN Offer o ON  o.id = :offerId WHERE i.name = :imageName")
    Image findImageByNameAndOfferId(String imageName, Long offerId);
}
