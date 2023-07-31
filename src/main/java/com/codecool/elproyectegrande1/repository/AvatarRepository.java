package com.codecool.elproyectegrande1.repository;

import com.codecool.elproyectegrande1.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, java.lang.Long> {
    @Query("SELECT a FROM Avatar a WHERE a.name = :fileName AND a.id = (SELECT max(id) from Avatar)")
    Avatar findAvatarByName(String fileName);
    @Query("SELECT a FROM Avatar a WHERE a.id = :id")
    Avatar findAvatarByID(Long id);
}
