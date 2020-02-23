package services;

import entities.Project;
import repositories.ProjectRepository;
import repositories.implementation.ProjectRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ProjectService {
    private ProjectRepository projectRepository;

    public ProjectService(EntityManager entityManager) {
        this.projectRepository = new ProjectRepositoryImpl(entityManager);
    }

    public String getLastTenProjectsInfo() {
        return this.projectRepository.findLastTenStarted()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .map(project -> String.format("Project name: %s" +
                                "\r\n\tProject Description: %s" +
                                "\r\n\tProject Start Date: %s" +
                                "\r\n\tProject End Date: %s",
                        project.getName(),
                        project.getDescription(),
                        project.getStartDate().toString(),
                        project.getEndDate() == null ? "null" : project.getEndDate().toString()
                )).collect(Collectors.joining("\r\n"));
    }
}
