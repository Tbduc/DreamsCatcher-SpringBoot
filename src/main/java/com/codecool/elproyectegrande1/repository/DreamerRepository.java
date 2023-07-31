package com.codecool.elproyectegrande1.repository;


import com.codecool.elproyectegrande1.entity.Dream;
import com.codecool.elproyectegrande1.entity.Dreamer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DreamerRepository extends JpaRepository<Dreamer, Long> {
    Dreamer findByEmail(String email);

    Optional<Dreamer> findByUsername(String name);

    Dreamer findTopByOrderByFollowersDesc();

    @Modifying
    @Query("update Dreamer d set d.dreams = :dreams where d.id = :id")
    void updateDreams(@Param(value = "id") Long id, @Param(value = "dreams") List<Dream> dreams);

}
