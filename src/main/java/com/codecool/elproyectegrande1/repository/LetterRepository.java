package com.codecool.elproyectegrande1.repository;

import com.codecool.elproyectegrande1.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

}
