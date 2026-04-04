package org.scopesky.jdktutorial.dto;

public class ProjectResponseDTO {
    private Integer id;
    private String name;
    private String description;


    public ProjectResponseDTO(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
