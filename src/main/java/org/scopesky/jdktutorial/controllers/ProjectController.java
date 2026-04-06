package org.scopesky.jdktutorial.controllers;

import org.scopesky.jdktutorial.dto.ProjectRequestDTO;
import org.scopesky.jdktutorial.dto.ProjectResponseDTO;
import org.scopesky.jdktutorial.services.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/project")

public class ProjectController {
    private final ProjectService projectService;
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    //Get All Projects
    @GetMapping
    public Page<ProjectResponseDTO> getAllProjects(
            @RequestParam(defaultValue = "0")    int page,
            @RequestParam(defaultValue = "10")   int size,
            @RequestParam(defaultValue = "id")   String sortBy,
            @RequestParam(defaultValue = "asc")  String sortDir) {
        return projectService.getAllProjects(page, size, sortBy, sortDir);
    }
    @GetMapping ("/{id}")
    public ProjectResponseDTO getProjectById(@PathVariable Long id){
        return projectService.getProjectById(id);
    }
    @PostMapping
    public ProjectResponseDTO saveProject(@RequestBody ProjectRequestDTO projectRequestDTO){
        return projectService.saveProject(projectRequestDTO);
    }

    @PutMapping("/{id}")
    public ProjectResponseDTO updateProject(@PathVariable Long id, @RequestBody ProjectRequestDTO projectRequestDTO){
        return projectService.updateProject(id, projectRequestDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
    }


}
