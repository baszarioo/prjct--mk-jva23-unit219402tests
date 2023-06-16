package com.tattoostudio.api.service;

import com.tattoostudio.api.dto.StudioDto;
import com.tattoostudio.api.dto.StudioResponse;
import com.tattoostudio.api.models.Studio;
import com.tattoostudio.api.repository.StudioRepository;
import com.tattoostudio.api.service.impl.StudioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        ///to finish...
    }
}
