package com.tattoostudio.api.repository;

import com.tattoostudio.api.models.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, Integer> {
    Optional<Studio> findByCity(String city);
}