package com.tattoostudio.api.repository;

import com.tattoostudio.api.models.Rating;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RatingRepositoryTests {
    private RatingRepository ratingRepository;
    @Autowired
    public RatingRepositoryTests(RatingRepository ratingRepository){
        this.ratingRepository=ratingRepository;
    }
    @Test
    public void RatingRepository_SaveAll_ReturnsSavedRating() {
        Rating rating = Rating.builder().title("title").content("content").stars(5).build();
        Rating savedRating = ratingRepository.save(rating);
        Assertions.assertThat(savedRating).isNotNull();
        Assertions.assertThat(savedRating.getId()).isGreaterThan(0);
    }
    @Test
    public void RatingRepository_GetAll_ReturnsMoreThenOneRating() {
        Rating rating = Rating.builder().title("title").content("content").stars(5).build();
        Rating rating2= Rating.builder().title("title").content("content").stars(4).build();
        ratingRepository.save(rating);
        ratingRepository.save(rating2);
        List<Rating> ratingList=ratingRepository.findAll();
        Assertions.assertThat(ratingList).isNotNull();
        Assertions.assertThat(ratingList.size()).isEqualTo(2);
    }
    @Test
    public void RatingRepository_FindById_ReturnsSavedRating() {
        Rating rating = Rating.builder().title("title").content("content").stars(5).build();
        ratingRepository.save(rating);
        Rating ratingReturn = ratingRepository.findById(rating.getId()).get();
        Assertions.assertThat(ratingReturn).isNotNull();
    }
    @Test
    public void RatingRepository_UpdateRating_ReturnRating() {
        Rating rating = Rating.builder().title("title").content("content").stars(5).build();
        ratingRepository.save(rating);
        Rating ratingSave=ratingRepository.findById(rating.getId()).get();
        ratingSave.setTitle("title");
        ratingSave.setContent("content");
        Rating updatedStudio = ratingRepository.save(ratingSave);
        Assertions.assertThat(updatedStudio.getTitle()).isNotNull();
        Assertions.assertThat(updatedStudio.getContent()).isNotNull();
    }
    @Test
    public void RatingRepository_RatingDelete_RetrunRatingIsEmpty() {
        Rating rating = Rating.builder().title("title").content("content").stars(3).build();
        ratingRepository.save(rating);
        ratingRepository.deleteById(rating.getId());
        Optional<Rating> ratingReturn = ratingRepository.findById(rating.getId());
        Assertions.assertThat(ratingReturn).isEmpty();
    }
}
