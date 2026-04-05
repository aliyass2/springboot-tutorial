package org.scopesky.jdktutorial.dto;

import java.util.Set;

public class SoftwareEngineerResponseDto {
    private Integer id;
    private String name;
    private String techStack;
    private Set<Long> projectIds;
    private Set<String> projectNames;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTechStack() { return techStack; }
    public void setTechStack(String techStack) { this.techStack = techStack; }
    public Set<Long> getProjectIds() { return projectIds; }
    public void setProjectIds(Set<Long> projectIds) { this.projectIds = projectIds; }
    public Set<String> getProjectNames() { return projectNames; }
    public void setProjectNames(Set<String> projectNames) { this.projectNames = projectNames; }
}
