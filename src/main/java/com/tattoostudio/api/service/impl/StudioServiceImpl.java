package com.tattoostudio.api.service.impl;

import com.tattoostudio.api.dto.StudioDto;
import com.tattoostudio.api.dto.StudioResponse;
import com.tattoostudio.api.exceptions.StudioNotFoundException;
import com.tattoostudio.api.models.Studio;
import com.tattoostudio.api.repository.StudioRepository;
import com.tattoostudio.api.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudioServiceImpl implements StudioService {
    private StudioRepository studioRepository;

    @Autowired
    public StudioServiceImpl(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    @Override
    public StudioDto createStudio(StudioDto studioDto) {
        Studio studio = new Studio();
        studio.setName(studioDto.getName());
        studio.setCity(studioDto.getCity());

        Studio newStudio = studioRepository.save(studio);

        StudioDto studioResponse = new StudioDto();
        studioResponse.setId(newStudio.getId());
        studioResponse.setName(newStudio.getName());
        studioResponse.setCity(newStudio.getCity());
        return studioResponse;
    }

    @Override
    public StudioResponse getAllStudio(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Studio> studios = studioRepository.findAll(pageable);
        List<Studio> listOfStudio = studios.getContent();
        List<StudioDto> content = listOfStudio.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        StudioResponse studioResponse = new StudioResponse();
        studioResponse.setContent(content);
        studioResponse.setPageNo(studios.getNumber());
        studioResponse.setPageSize(studios.getSize());
        studioResponse.setTotalElements(studios.getTotalElements());
        studioResponse.setTotalPages(studios.getTotalPages());
        studioResponse.setLast(studios.isLast());

        return studioResponse;
    }

    @Override
    public StudioDto getStudioById(int id) {
        Studio studio = studioRepository.findById(id).orElseThrow(() -> new StudioNotFoundException("Studio could not be found"));
        return mapToDto(studio);
    }

    @Override
    public StudioDto updateStudio(StudioDto studioDto, int id) {
        Studio studio = studioRepository.findById(id).orElseThrow(() -> new StudioNotFoundException("Studio could not be updated"));

        studio.setName(studioDto.getName());
        studio.setCity(studioDto.getCity());

        Studio updatedStudio = studioRepository.save(studio);
        return mapToDto(updatedStudio);
    }

    @Override
    public void deleteStudioId(int id) {
        Studio studio = studioRepository.findById(id).orElseThrow(() -> new StudioNotFoundException("Studio could not be delete"));
        studioRepository.delete(studio);
    }

    private StudioDto mapToDto(Studio studio) {
        StudioDto studioDto = new StudioDto();
        studioDto.setId(studio.getId());
        studioDto.setName(studio.getName());
        studioDto.setCity(studio.getCity());
        return studioDto;
    }

    private Studio mapToEntity(StudioDto studioDto) {
        Studio studio = new Studio();
        studio.setName(studioDto.getName());
        studio.setCity(studioDto.getCity());
        return studio;
    }
}