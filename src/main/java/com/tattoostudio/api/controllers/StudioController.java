package com.tattoostudio.api.controllers;

import com.tattoostudio.api.dto.StudioDto;
import com.tattoostudio.api.dto.StudioResponse;
import com.tattoostudio.api.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class StudioController {

    private StudioService studioService;

    @Autowired
    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    @GetMapping("studio")
    public ResponseEntity<StudioResponse> getStudios(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(studioService.getAllStudio(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("studio/{id}")
    public ResponseEntity<StudioDto> studioDetail(@PathVariable int id) {
        return ResponseEntity.ok(studioService.getStudioById(id));

    }

    @PostMapping("studio/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StudioDto> createStudio(@RequestBody StudioDto studioDto) {
        return new ResponseEntity<>(studioService.createStudio(studioDto), HttpStatus.CREATED);
    }

    @PutMapping("studio/{id}/update")
    public ResponseEntity<StudioDto> updateStudio(@RequestBody StudioDto studioDto, @PathVariable("id") int studioId) {
        StudioDto response = studioService.updateStudio(studioDto, studioId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("studio/{id}/delete")
    public ResponseEntity<String> deleteStudio(@PathVariable("id") int studioId) {
        studioService.deleteStudioId(studioId);
        return new ResponseEntity<>("Studio delete", HttpStatus.OK);
    }

}