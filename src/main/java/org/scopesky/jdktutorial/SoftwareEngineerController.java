package org.scopesky.jdktutorial;

import jakarta.validation.Valid;
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
    public List<SoftwareEngineer> getAll() {
        return SoftwareEngineerService.getAllSoftwareEngineers(
        );
    }
    @GetMapping("/{id}")
    public SoftwareEngineer getSoftwareEngineerById(@PathVariable Integer id){
        return SoftwareEngineerService.getSoftwareEngineerById(id);
    }
    @PostMapping
    public void saveSoftwareEngineer(@Valid @RequestBody SoftwareEngineer softwareEngineer){
        SoftwareEngineerService.saveSoftwareEngineer(softwareEngineer);
    }
    @DeleteMapping("/{id}")
    public void deleteSoftwareEngineer(@PathVariable Integer id){
        SoftwareEngineerService.deleteSoftwareEngineer(id);
    }
}