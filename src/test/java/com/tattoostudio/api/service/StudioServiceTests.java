package com.tattoostudio.api.service;

import com.tattoostudio.api.dto.StudioDto;
import com.tattoostudio.api.dto.StudioResponse;
import com.tattoostudio.api.models.Studio;
import com.tattoostudio.api.repository.StudioRepository;
import com.tattoostudio.api.service.impl.StudioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

//import static org.assertj.core.api.AbstractSoftAssertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudioServiceTests {
    @Mock
    private StudioRepository studioRepository;
    @InjectMocks
    private StudioServiceImpl studioService;
    @Test
    public void StudioService_CreateStudio_ReturnsStudioDto() {
        Studio studio = Studio.builder()
                .name("tattoo_bapka")
                .city("manchester").build();
        StudioDto studioDto = StudioDto.builder().name("tatto-bapka").city("manchester").build();

    when(studioRepository.save(Mockito.any(Studio.class))).thenReturn(studio);
            StudioDto savedStudio = studioService.createStudio(studioDto);
            Assertions.assertThat(savedStudio).isNotNull();
    }
    @Test
    public void StudioService_GetAllStudios_ReturnsResponseDto() {
        Page<Studio> studios = Mockito.mock(Page.class);
when(studioRepository.findAll(Mockito.any(Pageable.class))).thenReturn(studios);
        StudioResponse saveStudio = studioService.getAllStudios(1,10);
        Assertions.assertThat(saveStudio).isNotNull();
    }
    ////////////////////////////////////////////////////////////////////////
    @Test
    public void StudioService_FindById_ReturnStudioDto() {
        int studioId=1;
        Studio studio = Studio.builder().id(1).name("tattoo-bapka").city("manchester").city("this is a city").build();
        when(studioRepository.findById(studioId)).thenReturn(Optional.ofNullable(studio));
        StudioDto studioReturn = studioService.getStudioById(studioId);
        Assertions.assertThat(studioReturn).isNotNull();
    }
    @Test
    public void StudioService_UpdateStudio_ReturnStudioDto() {
        int studioId=1;
        Studio studio = Studio.builder().id(1).name("tattoo-dziadu").city("londyn")
                .city("this is a city").build();
        StudioDto studioDto=StudioDto.builder().id(1).name("tattoo").city("londyn")
                .city("this is a city").build();
    when(studioRepository.findById(studioId)).thenReturn(Optional.ofNullable(studio));
    when(studioRepository.save(studio)).thenReturn(studio);
        StudioDto updateReturn= studioService.updateStudio(studioDto, studioId);
        Assertions.assertThat(updateReturn).isNotNull();
    }
    @Test
    public void PokemonService_DeletePokemonById_ReturnVoid() {
        int pokemonId = 1;
        Studio pokemon = Studio.builder().id(1).name("tattooshop").city("london").city("this is a city").build();

        when(studioRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        doNothing().when(studioRepository).delete(pokemon);

        assertAll(() -> studioService.deleteStudioId(pokemonId));
    }
}