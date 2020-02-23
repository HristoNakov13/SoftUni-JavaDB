package repositories.implementation;

import entities.Department;
import repositories.DepartmentRepository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class DepartmentRepositoryImpl extends RepositoryImpl<Department> implements DepartmentRepository {
    public DepartmentRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Department.class);
    }

    @Override
    public Department findById(int id) {
        return super.findById(id);
    }

    @Override
    public List<Department> findAll() {
        return super.findAll();
    }

    @Override
    public void save(Department department) {
        super.save(department);
    }

    @Override
    public Department saveAndFlush(Department department) {
        return super.saveAndFlush(department);
    }

    @Override
    public Department findByName(String departmentName) {
        String queryString = "SELECT d FROM Department d WHERE d.name = :departmentName";

        return getEntityManager()
                .createQuery(queryString, Department.class)
                .setParameter("departmentName", departmentName)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Department> findByMaxSalaryBetween(BigDecimal from, BigDecimal to) {
        String queryString = "SELECT d FROM Department d " +
                "INNER JOIN d.employees e " +
                "GROUP BY d.id " +
                "HAVING MAX(e.salary) NOT BETWEEN :from AND :to";

        return super.getEntityManager()
                .createQuery(queryString, Department.class)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
    }
}
