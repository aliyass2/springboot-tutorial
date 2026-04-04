package org.scopesky.jdktutorial.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class SoftwareEngineerRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Tech stack cannot be blank")
    private String techStack;
    private Set<Long> projectIds;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTechStack() { return techStack; }
    public void setTechStack(String techStack) { this.techStack = techStack; }
    public Set<Long> getProjectIds() { return projectIds; }
    public void setProjectIds(Set<Long> projectIds) { this.projectIds = projectIds; }

}
