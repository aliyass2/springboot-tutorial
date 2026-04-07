package org.scopesky.jdktutorial.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.scopesky.jdktutorial.Project;
import org.scopesky.jdktutorial.dto.ProjectResponseDTO;
import org.scopesky.jdktutorial.mapper.ProjectMapper;
import org.scopesky.jdktutorial.repositories.ProjectRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    ProjectRepository projectRepository;
    @Mock
    ProjectMapper projectMapper;
    @InjectMocks
    ProjectService projectService;
    @Test
    void getProjectById_returnsProjectDto(){
        //Arrange
        Project project = new Project();
        project.setId(1L);
        ProjectResponseDTO dto = new ProjectResponseDTO(1, "Test Project", "Test Description");
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.toDto(project)).thenReturn(dto);
        //Act
        ProjectResponseDTO result = projectService.getProjectById(1L);
        //Assert
        assertThat(result).isSameAs(dto);

    }

}
