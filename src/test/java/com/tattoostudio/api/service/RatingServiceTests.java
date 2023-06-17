package com.tattoostudio.api.service;

import com.tattoostudio.api.dto.RatingDto;
import com.tattoostudio.api.dto.StudioDto;
import com.tattoostudio.api.models.Rating;
import com.tattoostudio.api.models.Studio;
import com.tattoostudio.api.repository.RatingRepository;
import com.tattoostudio.api.repository.StudioRepository;
import com.tattoostudio.api.service.impl.RatingServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTests {
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private StudioRepository studioRepository;
    @InjectMocks
    private RatingServiceImpl ratingService;
     private Studio studio;
    private Rating rating;
    private RatingDto ratingDto;
    private StudioDto studioDto;
    @BeforeEach
    public void init(){
        studio = Studio.builder().name("studio").city("manchester").build();
        studioDto=StudioDto.builder().name("tattoo-studio").city("manchester").build();
        rating = Rating.builder().title("title").content("content").stars(5).build();
        ratingDto = RatingDto.builder().title("rating title").content("test content").stars(5).build();
    }
    @Test
    public void RatingService_CreateRating_ReturnsRatingDto(){
when(studioRepository.findById(studio.getId())).thenReturn(Optional.of(studio));
when(ratingRepository.save(Mockito.any(Rating.class))).thenReturn(rating);
        RatingDto savedRating = ratingService.createRating(studio.getId(), ratingDto);
        Assertions.assertThat(savedRating).isNotNull();
    }
    @Test
    public void RatingService_GetRatingsByStudioId_ReturnRatingDto(){
        int ratingId=1;
        int studioId=1;
        rating.setStudio(studio);
when(studioRepository.findById(studioId)).thenReturn(Optional.of(studio));
when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));
        RatingDto ratingReturn = ratingService.getRatingById(ratingId, studioId);
        Assertions.assertThat(ratingReturn).isNotNull();
        Assertions.assertThat(ratingReturn).isNotNull();
    }
    @Test
    public void RatingService_UpdateStudio_ReturnRatingDto(){
        int studioId=1;
        int ratingId=1;
        studio.setRatings(Arrays.asList(rating));
        rating.setStudio(studio);
when(studioRepository.findById(studioId)).thenReturn(Optional.of(studio));
when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));
when(ratingRepository.save(rating)).thenReturn(rating);
        RatingDto updateReturn=ratingService.updateRating(studioId, ratingId, ratingDto);
        Assertions.assertThat(updateReturn).isNotNull();
    }
    @Test
    public void RatingService_DeleteStudioById_ReturnVoid() {
        int studioId=1;
        int ratingId=1;
        studio.setRatings(Arrays.asList(rating));
        rating.setStudio(studio);
when(studioRepository.findById(studioId)).thenReturn(Optional.of(studio));
when(ratingRepository.findById(studioId)).thenReturn(Optional.of(rating));
        assertAll(()->ratingService.deleteRating(studioId, ratingId));
    }
}
