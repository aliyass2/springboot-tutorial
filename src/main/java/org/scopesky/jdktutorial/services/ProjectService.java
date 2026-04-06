package org.scopesky.jdktutorial.services;

import org.scopesky.jdktutorial.Project;
import org.scopesky.jdktutorial.dto.ProjectRequestDTO;
import org.scopesky.jdktutorial.dto.ProjectResponseDTO;
import org.scopesky.jdktutorial.exception.ResourceNotFoundException;
import org.scopesky.jdktutorial.mapper.ProjectMapper;
import org.scopesky.jdktutorial.repositories.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }
    //Get all projects
    public Page<ProjectResponseDTO> getAllProjects(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return projectRepository.findAll(pageable)
                .map(projectMapper::toDto);
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
