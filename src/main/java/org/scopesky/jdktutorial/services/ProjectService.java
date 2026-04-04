package org.scopesky.jdktutorial.services;

import org.scopesky.jdktutorial.Project;
import org.scopesky.jdktutorial.dto.ProjectRequestDTO;
import org.scopesky.jdktutorial.dto.ProjectResponseDTO;
import org.scopesky.jdktutorial.exception.ResourceNotFoundException;
import org.scopesky.jdktutorial.mapper.ProjectMapper;
import org.scopesky.jdktutorial.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }
    //Get all projects
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toDto)
                .toList();
    }
    //Get Project By ID
    public ProjectResponseDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project with id " + id + " not found"
                ));
        return projectMapper.toDto(project);
    }
    //Save a new project
    public ProjectResponseDTO saveProject(ProjectRequestDTO projectRequestDTO){
        Project project = projectMapper.toEntity(projectRequestDTO);
        projectRepository.save(project);
        return null;
    }
    //Update an existing project
    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO projectRequestDTO){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project with id " + id + " not found"
                ));
        project.setName(projectRequestDTO.getName());
        project.setDescription(projectRequestDTO.getDescription());
        projectRepository.save(project);
        return (ProjectResponseDTO) projectMapper.toDto(project);
    }
    //Delete a project
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project with id " + id + " not found"
                ));
        projectRepository.delete(project);
    }

}
