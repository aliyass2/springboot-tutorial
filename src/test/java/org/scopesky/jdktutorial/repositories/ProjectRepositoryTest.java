package org.scopesky.jdktutorial.repositories;

import org.junit.jupiter.api.Test;
import org.scopesky.jdktutorial.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void findById_returnsSavedProject() {
        //Arrange
        Project project = new Project();
        project.setName("API");
        project.setDescription("Backend project");
        //Act

        Project savedProject = projectRepository.save(project);
        Optional<Project> result = projectRepository.findById(savedProject.getId());
        //Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("API");
    }
}
