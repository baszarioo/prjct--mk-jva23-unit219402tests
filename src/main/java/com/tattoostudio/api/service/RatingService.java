package com.tattoostudio.api.service;

import com.tattoostudio.api.dto.RatingDto;

import java.util.List;

public interface RatingService {
    RatingDto createRating(int studioId, RatingDto reviewDto);
    List<RatingDto> getRatingsByStudioId(int id);
    RatingDto getRatingById(int reviewId, int studioId);
    RatingDto updateRating(int studioId, int reviewId, RatingDto reviewDto);
    void deleteRating(int studioId, int reviewId);

}