package repositories.implementation;

import entities.Project;
import repositories.ProjectRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class ProjectRepositoryImpl extends RepositoryImpl<Project> implements ProjectRepository {
    public ProjectRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Project.class);
    }

    @Override
    public Project findById(int id) {
        return super.findById(id);
    }

    @Override
    public List<Project> findAll() {
        return super.findAll();
    }

    @Override
    public void save(Project project) {
        super.save(project);
    }

    @Override
    public Project saveAndFlush(Project project) {
        return super.saveAndFlush(project);
    }

    @Override
    public List<Project> findLastTenStarted() {
        String queryString = "SELECT p FROM Project p ORDER BY p.id DESC";

        return super.getEntityManager()
                .createQuery(queryString, Project.class)
                .setMaxResults(10)
                .getResultList();
    }
}
