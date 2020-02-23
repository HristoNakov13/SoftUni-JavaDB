package repositories.implementation;

import entities.Employee;
import repositories.EmployeeRepository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class EmployeeRepositoryImpl extends RepositoryImpl<Employee> implements EmployeeRepository {
    public EmployeeRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Employee.class);
    }

    @Override
    public Employee findById(int id) {
        return super.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return super.findAll();
    }

    @Override
    public void save(Employee employee) {
        super.save(employee);
    }

    @Override
    public Employee saveAndFlush(Employee employee) {
        return super.saveAndFlush(employee);
    }

    @Override
    public List<Employee> findByFullName(String fullName) {
        String queryString = "SELECT e FROM Employee e WHERE CONCAT(e.firstName, ' ', e.lastName) = :fullName";

        return super.getEntityManager()
                .createQuery(queryString, Employee.class)
                .setParameter("fullName", fullName)
                .getResultList();
    }

    @Override
    public List<Employee> findBySalaryGreaterThan(BigDecimal salary) {
        String queryString = "SELECT e FROM Employee e WHERE e.salary > :salaryParam";

        return super.getEntityManager()
                .createQuery(queryString, Employee.class)
                .setParameter("salaryParam", salary)
                .getResultList();
    }

    @Override
    public List<Employee> findByDepartment(String departmentName) {
        String queryString = "SELECT e FROM Employee e INNER JOIN e.department d WHERE d.name = :departmentName";

        return super.getEntityManager()
                .createQuery(queryString, Employee.class)
                .setParameter("departmentName", departmentName)
                .getResultList();
    }

    @Override
    public List<Employee> findByLastName(String lastName) {
        String queryString = "SELECT e FROM Employee e WHERE e.lastName = :lastNameParam";

        return super.getEntityManager()
                .createQuery(queryString, Employee.class)
                .setParameter("lastNameParam", lastName)
                .getResultList();
    }

    @Override
    public List<Employee> findByAddressId(int addressId) {
        String queryString = "SELECT e FROM Employee e INNER JOIN e.address a WHERE a.id = :addressId";

        return super.getEntityManager()
                .createQuery(queryString, Employee.class)
                .setParameter("addressId", addressId)
                .getResultList();
    }

    @Override
    public List<Employee> findByFirstNameStartingWith(String startingWith) {
        String queryString = "SELECT e FROM Employee e WHERE e.firstName LIKE CONCAT(:startingWith, '%')";

        return super.getEntityManager()
                .createQuery(queryString, Employee.class)
                .setParameter("startingWith", startingWith)
                .getResultList();
    }
}
