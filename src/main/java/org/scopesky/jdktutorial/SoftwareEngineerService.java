package org.scopesky.jdktutorial;

import org.scopesky.jdktutorial.dto.SoftwareEngineerRequestDTO;
import org.scopesky.jdktutorial.dto.SoftwareEngineerResponseDto;
import org.scopesky.jdktutorial.exception.ResourceNotFoundException;
import org.scopesky.jdktutorial.mapper.SoftwareEngineerMapper;
import org.scopesky.jdktutorial.repositories.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class SoftwareEngineerService {

    private final SoftwareEngineerRepository softwareEngineerRepository;
    private final ProjectRepository projectRepository;
    private final SoftwareEngineerMapper softwareEngineerMapper;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository,
                                   ProjectRepository projectRepository,
                                   SoftwareEngineerMapper softwareEngineerMapper) {
        this.softwareEngineerRepository = softwareEngineerRepository;
        this.projectRepository = projectRepository;
        this.softwareEngineerMapper = softwareEngineerMapper;
    }
    public Page<SoftwareEngineerResponseDto> getAllSoftwareEngineers(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return softwareEngineerRepository.findAll(pageable)
                .map(softwareEngineerMapper::toDto);
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
