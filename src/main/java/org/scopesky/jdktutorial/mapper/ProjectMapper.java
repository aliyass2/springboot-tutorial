package org.scopesky.jdktutorial.mapper;

import org.mapstruct.Mapper;
import org.scopesky.jdktutorial.Project;
import org.scopesky.jdktutorial.dto.ProjectRequestDTO;
import org.scopesky.jdktutorial.dto.ProjectResponseDTO;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    // RequestDTO → Entity (for saving)
    Project toEntity(ProjectRequestDTO project);
    // Return
    ProjectResponseDTO toDto(Project entity);
}
