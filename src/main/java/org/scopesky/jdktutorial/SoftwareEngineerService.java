package org.scopesky.jdktutorial;

import org.scopesky.jdktutorial.dto.SoftwareEngineerRequestDTO;
import org.scopesky.jdktutorial.dto.SoftwareEngineerResponseDto;
import org.scopesky.jdktutorial.exception.ResourceNotFoundException;
import org.scopesky.jdktutorial.mapper.SoftwareEngineerMapper;
import org.scopesky.jdktutorial.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class SoftwareEngineerService {

    private final SoftwareEngineerRepository softwareEngineerRepository;
    private final ProjectRepository projectRepository;
    @Autowired
    private SoftwareEngineerMapper softwareEngineerMapper;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository, ProjectRepository projectRepository) {
        this.softwareEngineerRepository = softwareEngineerRepository;
        this.projectRepository = projectRepository;
    }
    public List<SoftwareEngineerResponseDto> getAllSoftwareEngineers(){
        return softwareEngineerRepository.findAll()
                .stream()
                .map(softwareEngineerMapper::toDto)
                .toList();
    }
    //we first find the software engineer by id and then return it, we now use our mapper here as well
    public SoftwareEngineerResponseDto getSoftwareEngineerById(Integer id) {
        SoftwareEngineer softwareEngineer = softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Software engineer with id " + id + " not found"
                ));
        return softwareEngineerMapper.toDto(softwareEngineer);
    }
    //We save the softwareEngineer using our mapper
    @Transactional
    public void saveSoftwareEngineer(SoftwareEngineerRequestDTO softwareEngineerRequestDTO){
        SoftwareEngineer softwareEngineer = softwareEngineerMapper.toEntity(softwareEngineerRequestDTO);
        softwareEngineerRepository.save(softwareEngineer);

        Set<Long> projectIds = softwareEngineerRequestDTO.getProjectIds();
        if (projectIds != null) {
            for (Long projectId : projectIds) {
                Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new ResourceNotFoundException("Project with id " + projectId + " not found"));
                project.addEngineer(softwareEngineer);
                projectRepository.save(project);
            }
        }
    }
    //We update the software engineer using our mapper
    public void updateSoftwareEngineer(SoftwareEngineerRequestDTO softwareEngineerRequestDTO, Integer id){
        SoftwareEngineer softwareEngineer = softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Software engineer with id " + id + " not found"
                ));
        softwareEngineer.setName(softwareEngineerRequestDTO.getName());
        softwareEngineer.setTechStack(softwareEngineerRequestDTO.getTechStack());
        softwareEngineerRepository.save(softwareEngineer);
    }
    //we first find the software engineer by id and then delete it
    public void deleteSoftwareEngineer(Integer id) {
        SoftwareEngineer softwareEngineer = softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Software engineer with id " + id + " not found"
                ));
        softwareEngineerRepository.delete(softwareEngineer);
    }
}
