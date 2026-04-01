package org.scopesky.jdktutorial;

import jakarta.validation.Valid;
import org.scopesky.jdktutorial.dto.SoftwareEngineerRequestDTO;
import org.scopesky.jdktutorial.dto.SoftwareEngineerResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/software-engineer")
public class SoftwareEngineerController {
    private final SoftwareEngineerService SoftwareEngineerService;
    public SoftwareEngineerController(SoftwareEngineerService SoftwareEngineerService) {
        this.SoftwareEngineerService = SoftwareEngineerService;
    }

    @GetMapping
    public List<SoftwareEngineerResponseDto> getAll() {
        return SoftwareEngineerService.getAllSoftwareEngineers();
    }
    @GetMapping("/{id}")
    public SoftwareEngineerResponseDto getSoftwareEngineerById(@PathVariable Integer id){
        return SoftwareEngineerService.getSoftwareEngineerById(id);
    }
    @PostMapping
    public void saveSoftwareEngineer(@Valid @RequestBody SoftwareEngineerRequestDTO softwareEngineerRequestDTO){
        SoftwareEngineerService.saveSoftwareEngineer(softwareEngineerRequestDTO);
    }
    //Update the software engineer
    @PutMapping("/{id}")
    public void updateSoftwareEngineer(@Valid @RequestBody SoftwareEngineerRequestDTO softwareEngineerRequestDTO, @PathVariable Integer id){
        SoftwareEngineerService.updateSoftwareEngineer(softwareEngineerRequestDTO, id);
    }
    @DeleteMapping("/{id}")
    public void deleteSoftwareEngineer(@PathVariable Integer id){
        SoftwareEngineerService.deleteSoftwareEngineer(id);
    }
}