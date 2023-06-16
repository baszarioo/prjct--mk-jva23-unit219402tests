package com.tattoostudio.api.repository;

import com.tattoostudio.api.models.Studio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StudioRepositoryTests {
    @Autowired
    private StudioRepository studioRepository;
    @Test
    public void StudioRepository_SaveAll_ReturnSavedStudio(){
        //Arragne
        Studio studio = Studio.builder()
                .name("tattoo-bapka")
                .city("manchester").build();
        //Act
        Studio savedStudio = studioRepository.save(studio);
        //Assert
        Assertions.assertThat(savedStudio).isNotNull();
        Assertions.assertThat(savedStudio.getId()).isGreaterThan(0);
    }
    @Test
    public void StudioRepository_GetAll_ReturnMoreThanOneStudio() {
        Studio studio = Studio.builder()
                .name("tattoo-bapka")
                .city("manchester").build();
        Studio studio2= Studio.builder()
                .name("tattoo-dziadyga")
                .city("toronto").build();
        studioRepository.save(studio);
        studioRepository.save(studio2);
        List<Studio> studioList = studioRepository.findAll();

        Assertions.assertThat(studioList).isNotNull();
        Assertions.assertThat(studioList.size()).isEqualTo(2);
    }
    @Test
    public void StudioRepository_FindById_ReturnStudio() {
        Studio studio = Studio.builder()
                .name("tattoo-shoper")
                .city("warszawa").build();
        studioRepository.save(studio);
        Studio studioList = studioRepository.findById(studio.getId()).get();
        Assertions.assertThat(studioList).isNotNull();
    }

    @Test
    public void StudioRepository_FindByCity_ReturnStudioNotNull() {
        Studio studio = Studio.builder()
                .name("tattoo-morph")
                .city("Krakow").build();
        studioRepository.save(studio);
        Studio studioList = studioRepository.findByCity(studio.getCity()).get();
        Assertions.assertThat(studioList).isNotNull();
    }
    @Test
    public void StudioRepository_UpdateStudio_ReturnStudioNotNull() {
        Studio studio = Studio.builder()
                .name("tattoo-andrzej")
                .city("warszawa").build();
        studioRepository.save(studio);
        Studio studioSave = studioRepository.findById(studio.getId()).get();
        studioSave.setCity("Warsaw");
        studioSave.setName("Tattoo-Andrew");
        Studio updatedStudio  = studioRepository.save(studioSave);
        Assertions.assertThat(updatedStudio.getName()).isNotNull();
        Assertions.assertThat(updatedStudio.getCity()).isNotNull();
    }
}

