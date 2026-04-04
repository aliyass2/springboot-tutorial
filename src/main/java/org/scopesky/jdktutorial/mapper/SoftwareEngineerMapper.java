package org.scopesky.jdktutorial.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.scopesky.jdktutorial.SoftwareEngineer;
import org.scopesky.jdktutorial.dto.SoftwareEngineerRequestDTO;
import org.scopesky.jdktutorial.dto.SoftwareEngineerResponseDto;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public interface SoftwareEngineerMapper {
    // RequestDTO → Entity (for saving)
    SoftwareEngineer toEntity(SoftwareEngineerRequestDTO dto);

    // Entity → ResponseDTO (for returning)
    @Mapping(target = "projectIds", expression = "java(entity.getProjects().stream().map(p -> p.getId()).collect(Collectors.toSet()))")
    SoftwareEngineerResponseDto toDto(SoftwareEngineer entity);
}
