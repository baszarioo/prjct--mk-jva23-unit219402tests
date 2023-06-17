package com.tattoostudio.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tattoostudio.api.controllers.RatingController;
import com.tattoostudio.api.dto.RatingDto;
import com.tattoostudio.api.dto.StudioDto;
import com.tattoostudio.api.models.Rating;
import com.tattoostudio.api.models.Studio;
import com.tattoostudio.api.service.RatingService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = RatingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RatingControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private ObjectMapper objectMapper;
    private Studio studio;
    private Rating rating;
    private StudioDto studioDto;
    private RatingDto ratingDto;
    @BeforeEach
    public void init() {
        studio = Studio.builder().name("studio-bapka").city("san andreas").build();
        studioDto=StudioDto.builder().name("studio-babunia").city("san andreas").build();
        rating = Rating.builder().title("title").content("content").stars(5).build();
        ratingDto = RatingDto.builder().title("rating title").content("test content").stars(4).build();
    }
    @Test
    public void RatingController_GetRatingsByStudioId_ReturnRatingDto() throws Exception {
        int studioId=1;
        when(ratingService.getRatingsByStudioId(studioId)).thenReturn(Arrays.asList(ratingDto));
        ResultActions response = mockMvc.perform(get("/api/studio/1/ratings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studioDto)));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(ratingDto).size())));
    }
    @Test
    public void RatingController_UpdateRating_ReturnRatingDto() throws Exception {
        int studioId=1;
        int ratingId=1;
        when(ratingService.updateRating(studioId, ratingId, ratingDto)).thenReturn(ratingDto);
        ResultActions response=mockMvc.perform(put("/api.studio/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ratingDto)));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(ratingDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(ratingDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(ratingDto.getStars())));
    }
    @Test
    public void RatingController_CreateRating_ReturnRatingDto() throws Exception{
        int studioId=1;
        when(ratingService.createRating(studioId, ratingDto)).thenReturn(ratingDto);
        ResultActions response = mockMvc.perform(post("/api/studio/1/ratings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ratingDto)));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(ratingDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(ratingDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(ratingDto.getStars())));
    }
    @Test
    public void RatingController_GetRatingId_ReturnRatingDto() throws Exception {
        int studioId=1;
        int ratingId=1;
        when(ratingService.getRatingById(ratingId, studioId)).thenReturn(ratingDto);
        ResultActions response=mockMvc.perform(get("/api/studio/1/ratings/1")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(ratingDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(ratingDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(ratingDto.getStars())));
    }
    @Test
    public void RatingController_DeleteRating_ReturnOk() throws Exception {
        int studioId=1;
        int ratingId=1;
        doNothing().when(ratingService).deleteRating(studioId, ratingId);
        ResultActions response=mockMvc.perform(delete("/api/studio/1/ratings/1")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
