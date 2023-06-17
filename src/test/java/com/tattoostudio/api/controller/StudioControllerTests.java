package com.tattoostudio.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tattoostudio.api.controllers.StudioController;
import com.tattoostudio.api.dto.RatingDto;
import com.tattoostudio.api.dto.StudioDto;
import com.tattoostudio.api.models.Rating;
import com.tattoostudio.api.models.Studio;
import com.tattoostudio.api.service.StudioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers= StudioController.class)
@AutoConfigureMockMvc(addFilters=false)
@ExtendWith(MockitoExtension.class)
public class StudioControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudioService studioService;
    @Autowired
    private ObjectMapper objectMapper;
    private Studio studio;
    private Rating rating;
    private RatingDto ratingDto;
    private StudioDto studioDto;
    @BeforeEach
    public void init(){
        studio = Studio.builder().name("tattoo-bapkaxd").city("san andreas").build();
        studioDto= StudioDto.builder().name("tattoo-bapkaxd").city("san andreas").build();
        rating=Rating.builder().title("title").content("content").stars(5).build();
        ratingDto=RatingDto.builder().title("rating title").content("test content").stars(2).build();
    }
    @Test
    public void StudioController_CreateStudio_ReturnCreated() throws Exception{
        given(studioService.createStudio(ArgumentMatchers.any())).willAnswer((invocation->invocation.getArgument(0)));
        ResultActions response=mockMvc.perform(post("/api/studio/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studioDto)));
        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
