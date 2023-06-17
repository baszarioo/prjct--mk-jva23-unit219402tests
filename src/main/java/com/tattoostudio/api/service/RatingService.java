package com.tattoostudio.api.service;

import com.tattoostudio.api.dto.RatingDto;

import java.util.List;

public interface RatingService {
    RatingDto createRating(int studioId, RatingDto ratingDto);
    List<RatingDto> getRatingsByStudioId(int id);
    RatingDto getRatingById(int ratingId, int studioId);
    RatingDto updateRating(int studioId, int ratingId, RatingDto ratingDto);
    void deleteRating(int studioId, int ratingId);

}