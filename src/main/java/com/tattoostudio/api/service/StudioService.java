package com.tattoostudio.api.service;

import com.tattoostudio.api.dto.StudioDto;
import com.tattoostudio.api.dto.StudioResponse;

public interface StudioService {
    StudioDto createStudio(StudioDto pokemonDto);
    StudioResponse getAllStudios(int pageNo, int pageSize);
    StudioDto getStudioById(int id);
    StudioDto updateStudio(StudioDto pokemonDto, int id);
    void deleteStudioId(int id);
}