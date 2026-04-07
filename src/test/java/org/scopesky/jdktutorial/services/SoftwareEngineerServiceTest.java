package org.scopesky.jdktutorial.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.scopesky.jdktutorial.Project;
import org.scopesky.jdktutorial.SoftwareEngineer;
import org.scopesky.jdktutorial.SoftwareEngineerRepository;
import org.scopesky.jdktutorial.SoftwareEngineerService;
import org.scopesky.jdktutorial.dto.SoftwareEngineerRequestDTO;
import org.scopesky.jdktutorial.dto.SoftwareEngineerResponseDto;
import org.scopesky.jdktutorial.exception.ResourceNotFoundException;
import org.scopesky.jdktutorial.mapper.SoftwareEngineerMapper;
import org.scopesky.jdktutorial.repositories.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SoftwareEngineerServiceTest {
    @Mock
    SoftwareEngineerMapper softwareEngineerMapper;
    @Mock
    SoftwareEngineerRepository softwareEngineerRepository;
    @InjectMocks
    SoftwareEngineerService softwareEngineerService;
    @Mock
    ProjectRepository projectRepository;


    @Test
    void getAllSoftwareEngineers_returnsPagedDtos_whenEngineersExist() {
        // Arrange
        SoftwareEngineer engineer = new SoftwareEngineer(1, "Alice", "Java");
        SoftwareEngineerResponseDto dto = new SoftwareEngineerResponseDto();
        dto.setId(1);
        dto.setName("Alice");
        dto.setTechStack("Java");

        when(softwareEngineerRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(engineer)));
        when(softwareEngineerMapper.toDto(engineer)).thenReturn(dto);

        // Act
        Page<SoftwareEngineerResponseDto> result =
                softwareEngineerService.getAllSoftwareEngineers(0, 10, "name", "asc");

        // Assert
        assertThat(result.getContent()).hasSize(1);
        SoftwareEngineerResponseDto resultDto = result.getContent().getFirst();
        assertThat(resultDto.getId()).isEqualTo(1);
        assertThat(resultDto.getName()).isEqualTo("Alice");
        assertThat(resultDto.getTechStack()).isEqualTo("Java");
        verify(softwareEngineerRepository).findAll(any(Pageable.class));
        verify(softwareEngineerMapper).toDto(engineer);
    }
    @Test
    void saveSoftwareEngineer_savesEngineer_whenNoProjectIds() {
        // Arrange
        SoftwareEngineerRequestDTO dto = new SoftwareEngineerRequestDTO();
        dto.setName("Alice");
        dto.setTechStack("Java");
        // projectIds left null intentionally

        SoftwareEngineer engineer = new SoftwareEngineer(1, "Alice", "Java");

        when(softwareEngineerMapper.toEntity(dto)).thenReturn(engineer);

        // Act
        softwareEngineerService.saveSoftwareEngineer(dto);

        // Assert
        verify(softwareEngineerRepository).save(engineer);
        verifyNoInteractions(projectRepository);
    }

    @Test
    void saveSoftwareEngineer_assignsEngineerToProjects_whenProjectIdsProvided() {
        // Arrange
        SoftwareEngineerRequestDTO dto = new SoftwareEngineerRequestDTO();
        dto.setName("Alice");
        dto.setTechStack("Java");
        dto.setProjectIds(Set.of(10L, 20L));

        SoftwareEngineer engineer = new SoftwareEngineer(1, "Alice", "Java");
        Project project1 = new Project(10L, "Project Alpha", "Description");
        Project project2 = new Project(20L, "Project Beta", "Description");

        when(softwareEngineerMapper.toEntity(dto)).thenReturn(engineer);
        when(projectRepository.findById(10L)).thenReturn(Optional.of(project1));
        when(projectRepository.findById(20L)).thenReturn(Optional.of(project2));

        // Act
        softwareEngineerService.saveSoftwareEngineer(dto);

        // Assert
        verify(softwareEngineerRepository).save(engineer);
        verify(projectRepository).findById(10L);
        verify(projectRepository).findById(20L);
        verify(projectRepository, times(2)).save(any(Project.class));
    }
    @Test
    void saveSoftwareEngineer_throwsResourceNotFoundException_whenProjectNotFound() {
        // Arrange
        SoftwareEngineerRequestDTO dto = new SoftwareEngineerRequestDTO();
        dto.setName("Alice");
        dto.setTechStack("Java");
        dto.setProjectIds(Set.of(99L));

        SoftwareEngineer engineer = new SoftwareEngineer(1, "Alice", "Java");

        when(softwareEngineerMapper.toEntity(dto)).thenReturn(engineer);
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> softwareEngineerService.saveSoftwareEngineer(dto));
    }
}
