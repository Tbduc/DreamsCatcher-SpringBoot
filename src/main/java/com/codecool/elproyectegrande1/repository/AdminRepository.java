package com.codecool.elproyectegrande1.repository;

import com.codecool.elproyectegrande1.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
//    Optional<Admin> findByUsername(String name);

    Boolean existsByUsername(String Nickname);

    Boolean existsByEmail(String email);
}
