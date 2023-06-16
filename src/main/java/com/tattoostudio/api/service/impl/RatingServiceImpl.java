package com.tattoostudio.api.service.impl;

import com.tattoostudio.api.dto.RatingDto;
import com.tattoostudio.api.exceptions.RatingNotFoundException;
import com.tattoostudio.api.exceptions.StudioNotFoundException;
import com.tattoostudio.api.models.Rating;
import com.tattoostudio.api.models.Studio;
import com.tattoostudio.api.repository.RatingRepository;
import com.tattoostudio.api.repository.StudioRepository;
import com.tattoostudio.api.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;
    private StudioRepository studioRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, StudioRepository studioRepository) {
        this.ratingRepository = ratingRepository;
        this.studioRepository = studioRepository;
    }

    @Override
    public RatingDto createRating(int studioId, RatingDto ratingDto) {
        Rating rating = mapToEntity(ratingDto);

        Studio studio = studioRepository.findById(studioId).orElseThrow(() -> new StudioNotFoundException("Studio with associated rating not found"));

        rating.setStudio(studio);

        Rating newRating = ratingRepository.save(rating);

        return mapToDto(newRating);
    }

    @Override
    public List<RatingDto> getRatingsByStudioId(int id) {
        List<Rating> ratings = ratingRepository.findByStudioId(id);

        return ratings.stream().map(rating -> mapToDto(rating)).collect(Collectors.toList());
    }

    @Override
    public RatingDto getRatingById(int ratingId, int studioId) {
        Studio studio = studioRepository.findById(studioId).orElseThrow(() -> new StudioNotFoundException("Studio with associated rating not found"));

        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() -> new RatingNotFoundException("Rating with associate studio not found"));

        if(rating.getStudio().getId() != studio.getId()) {
            throw new RatingNotFoundException("This rating does not belond to a studio");
        }

        return mapToDto(rating);
    }

    @Override
    public RatingDto updateRating(int studioId, int ratingId, RatingDto ratingDto) {
        Studio studio = studioRepository.findById(studioId).orElseThrow(() -> new StudioNotFoundException("Studio with associated rating not found"));

        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() -> new RatingNotFoundException("Rating with associate studio not found"));

        if(rating.getStudio().getId() != studio.getId()) {
            throw new RatingNotFoundException("This rating does not belong to a studio");
        }

        rating.setTitle(ratingDto.getTitle());
        rating.setContent(ratingDto.getContent());
        rating.setStars(ratingDto.getStars());

        Rating updateRating = ratingRepository.save(rating);

        return mapToDto(updateRating);
    }

    @Override
    public void deleteRating(int studioId, int ratingId) {
        Studio studio = studioRepository.findById(studioId).orElseThrow(() -> new StudioNotFoundException("Studio with associated rating not found"));

        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() -> new RatingNotFoundException("Rating with associate studio not found"));

        if(rating.getStudio().getId() != studio.getId()) {
            throw new RatingNotFoundException("This rating does not belong to a studio");
        }

        ratingRepository.delete(rating);
    }

    private RatingDto mapToDto(Rating rating) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setId(rating.getId());
        ratingDto.setTitle(rating.getTitle());
        ratingDto.setContent(rating.getContent());
        ratingDto.setStars(rating.getStars());
        return ratingDto;
    }

    private Rating mapToEntity(RatingDto ratingDto) {
        Rating rating = new Rating();
        rating.setId(ratingDto.getId());
        rating.setTitle(ratingDto.getTitle());
        rating.setContent(ratingDto.getContent());
        rating.setStars(ratingDto.getStars());
        return rating;
    }
}