package org.scopesky.jdktutorial.dto;

import jakarta.validation.constraints.NotBlank;

public class SoftwareEngineerRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Tech stack cannot be blank")
    private String techStack;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTechStack() { return techStack; }
    public void setTechStack(String techStack) { this.techStack = techStack; }
}
