package com.tattoostudio.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tattoostudio.api.controllers.StudioController;
import com.tattoostudio.api.dto.RatingDto;
import com.tattoostudio.api.dto.StudioDto;
import com.tattoostudio.api.dto.StudioResponse;
import com.tattoostudio.api.models.Rating;
import com.tattoostudio.api.models.Studio;
import com.tattoostudio.api.service.StudioService;
import org.hamcrest.CoreMatchers;
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

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
//        response.andExpect(MockMvcResultMatchers.status().isCreated());
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(studioDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", CoreMatchers.is(studioDto.getCity())));
    }
    @Test
    public void StudioController_GetAllStudios_ReturnResponseDto() throws Exception {
        StudioResponse responseDto = StudioResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(studioDto)).build();
        when(studioService.getAllStudios(1,10)).thenReturn(responseDto);
        ResultActions response = mockMvc.perform(get("/api/studio")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","1")
                .param("pageSize", "10"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()",
        CoreMatchers.is(responseDto.getContent().size())));
    }
    @Test
    public void StudioController_StudioDetail_ReturnStudioDto() throws Exception {
        int studioId=1;
        when(studioService.getStudioById(studioId)).thenReturn(studioDto);
        ResultActions response = mockMvc.perform(get("/api/studio/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studioDto)));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(studioDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", CoreMatchers.is(studioDto.getCity())));
    }
    @Test
    public void StudioController_UpdateStudio_ReturnStudioDto() throws Exception {
        int studioId=1;
        when(studioService.updateStudio(studioDto, studioId)).thenReturn(studioDto);
        ResultActions response = mockMvc.perform(put("/api/studio/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studioDto)));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is(studioDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city",CoreMatchers.is(studioDto.getCity())));
    }
    @Test
    public void StudioController_DeleteStudio_ReturnString() throws Exception{
        int studioId=1;
        doNothing().when(studioService).deleteStudioId(1);
        ResultActions response = mockMvc.perform(delete("/api/studio/1/delete")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
