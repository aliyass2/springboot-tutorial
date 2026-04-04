package org.scopesky.jdktutorial.controllers;

import org.scopesky.jdktutorial.dto.ProjectRequestDTO;
import org.scopesky.jdktutorial.dto.ProjectResponseDTO;
import org.scopesky.jdktutorial.services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/project")

public class ProjectController {
    private final ProjectService projectService;
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    //Get All Projects
    @GetMapping
    public List<ProjectResponseDTO> getAllProjects(){
        return projectService.getAllProjects();
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
