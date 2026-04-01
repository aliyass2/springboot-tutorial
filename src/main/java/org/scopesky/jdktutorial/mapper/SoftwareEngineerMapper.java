package org.scopesky.jdktutorial.mapper;

import org.mapstruct.Mapper;
import org.scopesky.jdktutorial.SoftwareEngineer;
import org.scopesky.jdktutorial.dto.SoftwareEngineerRequestDTO;
import org.scopesky.jdktutorial.dto.SoftwareEngineerResponseDto;

@Mapper(componentModel = "spring")
public interface SoftwareEngineerMapper {
    // RequestDTO → Entity (for saving)
    SoftwareEngineer toEntity(SoftwareEngineerRequestDTO dto);

    // Entity → ResponseDTO (for returning)
    SoftwareEngineerResponseDto toDto(SoftwareEngineer entity);
}
