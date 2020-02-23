package repositories;

import entities.Address;
import entities.Project;

import java.util.List;

public interface ProjectRepository {
    Project findById(int id);

    List<Project> findAll();

    void save(Project project);

    Project saveAndFlush(Project project);

    List<Project> findLastTenStarted();
}
