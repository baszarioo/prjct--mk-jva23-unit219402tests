package com.tattoostudio.api.controllers;

import com.tattoostudio.api.dto.RatingDto;
import com.tattoostudio.api.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class RatingController {

    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/studio/{studioId}/ratings")
    public ResponseEntity<RatingDto> createRating(@PathVariable(value = "studioId") int studioId, @RequestBody RatingDto ratingDto) {
        return new ResponseEntity<>(ratingService.createRating(studioId, ratingDto), HttpStatus.CREATED);
    }

    @GetMapping("/studio/{studioId}/ratings")
    public List<RatingDto> getRatingsByStudioId(@PathVariable(value = "studioId") int studioId) {
        return ratingService.getRatingsByStudioId(studioId);
    }

    @GetMapping("/studio/{studioId}/ratings/{id}")
    public ResponseEntity<RatingDto> getRatingById(@PathVariable(value = "studioId") int studioId, @PathVariable(value = "id") int ratingId) {
        RatingDto ratingDto = ratingService.getRatingById(studioId, ratingId);
        return new ResponseEntity<>(ratingDto, HttpStatus.OK);
    }

    @PutMapping("/studio/{studioId}/ratings/{id}")
    public ResponseEntity<RatingDto> updateRating(@PathVariable(value = "studioId") int studioId, @PathVariable(value = "id") int ratingId,
                                                  @RequestBody RatingDto ratingDto) {
        RatingDto updatedRating = ratingService.updateRating(studioId, ratingId, ratingDto);
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    @DeleteMapping("/studio/{studioId}/ratings/{id}")
    public ResponseEntity<String> deleteRating(@PathVariable(value = "studioId") int studioId, @PathVariable(value = "id") int ratingId) {
        ratingService.deleteRating(studioId, ratingId);
        return new ResponseEntity<>("Rating deleted successfully", HttpStatus.OK);
    }
}