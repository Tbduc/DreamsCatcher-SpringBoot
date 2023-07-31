package com.codecool.elproyectegrande1.repository;

import com.codecool.elproyectegrande1.entity.Dream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface DreamRepository extends JpaRepository<Dream, Long> {
    Dream findTopByOrderByLikesDesc();
    Dream findTopByOrderByViewsDesc();
    List<Dream> findTop3ByOrderByLikesDesc();
    List<Dream> findByDreamerUsername(String nickname);

   @Query("SELECT d FROM Dream d JOIN d.hashtags h WHERE h = :hashtag")
   List<Dream> findByHashtagsContaining(String hashtag);

    @Modifying
    @Query("update Dream d set d = :toBeSaved where d.id = :id")
    void update(Dream toBeSaved, Long id);
}

