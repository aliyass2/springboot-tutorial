package org.scopesky.jdktutorial;

import jakarta.validation.Valid;
import org.scopesky.jdktutorial.dto.SoftwareEngineerRequestDTO;
import org.scopesky.jdktutorial.dto.SoftwareEngineerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/software-engineer")
public class SoftwareEngineerController {
    private final SoftwareEngineerService SoftwareEngineerService;
    public SoftwareEngineerController(SoftwareEngineerService SoftwareEngineerService) {
        this.SoftwareEngineerService = SoftwareEngineerService;
    }

    @GetMapping
    public Page<SoftwareEngineerResponseDto> getAll(
            @RequestParam(defaultValue = "0")   int page,
            @RequestParam(defaultValue = "10")  int size,
            @RequestParam(defaultValue = "id")  String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return SoftwareEngineerService.getAllSoftwareEngineers(page, size, sortBy, sortDir);
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