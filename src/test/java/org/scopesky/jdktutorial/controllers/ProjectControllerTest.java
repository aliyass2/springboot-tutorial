package org.scopesky.jdktutorial.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.scopesky.jdktutorial.config.SecurityConfig;
import org.scopesky.jdktutorial.dto.ProjectRequestDTO;
import org.scopesky.jdktutorial.dto.ProjectResponseDTO;
import org.scopesky.jdktutorial.security.JwtAuthEntryPoint;
import org.scopesky.jdktutorial.services.ProjectService;
import org.scopesky.jdktutorial.services.UserDetailsServiceImpl;
import org.scopesky.jdktutorial.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
@Import(SecurityConfig.class)
public class ProjectControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    JwtUtil jwtUtil;
    @MockitoBean
    JwtAuthEntryPoint jwtAuthEntryPoint;
    @MockitoBean
    UserDetailsServiceImpl userDetailsService;
    ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    ProjectService projectService;

    @BeforeEach
    void setUp() throws Exception {
        doAnswer(inv -> {
            inv.<HttpServletResponse>getArgument(1).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }).when(jwtAuthEntryPoint).commence(any(), any(), any());

        UserDetails adminUser = User.withUsername("admin").password("").roles("ADMIN").build();
        UserDetails regularUser = User.withUsername("user").password("").roles("USER").build();

        when(jwtUtil.extractUsername("admin-token")).thenReturn("admin");
        when(jwtUtil.extractUsername("user-token")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("admin")).thenReturn(adminUser);
        when(userDetailsService.loadUserByUsername("user")).thenReturn(regularUser);
        when(jwtUtil.isTokenValid("admin-token", adminUser)).thenReturn(true);
        when(jwtUtil.isTokenValid("user-token", regularUser)).thenReturn(true);
    }

    @Test
    void getAllProjects_returnsPageOfProjects() throws Exception {
        ProjectResponseDTO dto = new ProjectResponseDTO(1, "Alpha", "Desc");
        when(projectService.getAllProjects(0, 10, "id", "asc"))
                .thenReturn(new PageImpl<>(List.of(dto), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/api/v1/project")
                        .header("Authorization", "Bearer user-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Alpha"))
                .andExpect(jsonPath("$.content[0].description").value("Desc"));
    }

    @Test
    void getProjectById_returnsProject_whenExists() throws Exception {
        ProjectResponseDTO dto = new ProjectResponseDTO(1, "Alpha", "Desc");
        when(projectService.getProjectById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/project/1")
                        .header("Authorization", "Bearer user-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alpha"))
                .andExpect(jsonPath("$.description").value("Desc"));
    }

    @Test
    void saveProject_returnsCreatedProject() throws Exception {
        ProjectRequestDTO request = new ProjectRequestDTO();
        request.setName("Alpha");
        request.setDescription("Desc");

        ProjectResponseDTO response = new ProjectResponseDTO(1, "Alpha", "Desc");
        when(projectService.saveProject(any(ProjectRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/project")
                        .header("Authorization", "Bearer admin-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alpha"));
    }

    @Test
    void updateProject_returnsUpdatedProject() throws Exception {
        ProjectRequestDTO request = new ProjectRequestDTO();
        request.setName("Beta");
        request.setDescription("New Desc");

        ProjectResponseDTO response = new ProjectResponseDTO(1, "Beta", "New Desc");
        when(projectService.updateProject(eq(1L), any(ProjectRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/project/1")
                        .header("Authorization", "Bearer admin-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Beta"))
                .andExpect(jsonPath("$.description").value("New Desc"));
    }

    @Test
    void deleteProject_returns200_whenExists() throws Exception {
        mockMvc.perform(delete("/api/v1/project/1")
                        .header("Authorization", "Bearer admin-token"))
                .andExpect(status().isOk());

        verify(projectService).deleteProject(1L);
    }

    @Test
    void getAllProjects_returns401_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/project"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void saveProject_returns403_whenNotAdmin() throws Exception {
        ProjectRequestDTO request = new ProjectRequestDTO();
        request.setName("Alpha");
        request.setDescription("Desc");

        mockMvc.perform(post("/api/v1/project")
                        .header("Authorization", "Bearer user-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }
}
