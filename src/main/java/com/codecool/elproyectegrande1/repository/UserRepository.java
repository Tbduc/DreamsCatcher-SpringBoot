package com.codecool.elproyectegrande1.repository;

import com.codecool.elproyectegrande1.entity.Dreamer;
import com.codecool.elproyectegrande1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Dreamer findTopByOrderByFollowersDesc();

    @Modifying
    @Query("update User u set u = :toBeSaved where u.id = :id")
    void update(User toBeSaved, Long id);
}
