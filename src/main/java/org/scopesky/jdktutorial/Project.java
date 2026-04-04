package org.scopesky.jdktutorial;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "project_engineers",             // junction table name
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "engineer_id")
    )
    private Set<SoftwareEngineer> engineers = new HashSet<>();


    private LocalDateTime createdAt = LocalDateTime.now();
    public Project() {}
    public Project(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void addEngineer(SoftwareEngineer engineer) {
        engineers.add(engineer);
        engineer.getProjects().add(this);
    }
    public void removeEngineer(SoftwareEngineer engineer) {
        engineers.remove(engineer);
        engineer.getProjects().remove(this);
    }
}
