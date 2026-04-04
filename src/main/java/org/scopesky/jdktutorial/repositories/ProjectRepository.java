package org.scopesky.jdktutorial.repositories;

import org.scopesky.jdktutorial.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
