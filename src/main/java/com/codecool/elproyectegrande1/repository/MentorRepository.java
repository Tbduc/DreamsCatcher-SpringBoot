package com.codecool.elproyectegrande1.repository;

import com.codecool.elproyectegrande1.entity.Mentor;
import com.codecool.elproyectegrande1.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {
    Optional<Mentor> findByUsername(String name);

    @Modifying
    @Query("update Mentor m set m.offers = :offers where m.id = :id")
    void updateOffers(@Param(value = "id") Long id, @Param(value = "offers") List<Offer> offers);
}
