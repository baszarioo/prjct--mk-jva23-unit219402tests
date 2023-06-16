package com.tattoostudio.api.repository;

import com.tattoostudio.api.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByStudioId(int studioId);
}