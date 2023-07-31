package com.codecool.elproyectegrande1.repository;

import com.codecool.elproyectegrande1.entity.Parameter;
import com.codecool.elproyectegrande1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    @Modifying
    @Query("update Parameter p set p = :toBeSaved where p.id = :id")
    void update(Parameter toBeSaved, Long id);
}
