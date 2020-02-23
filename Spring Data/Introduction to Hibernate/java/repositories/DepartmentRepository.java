package repositories;

import entities.Department;

import java.math.BigDecimal;
import java.util.List;

public interface DepartmentRepository {
    Department findById(int id);

    List<Department> findAll();

    void save(Department department);

    Department saveAndFlush(Department department);

    Department findByName(String departmentName);

    List<Department> findByMaxSalaryBetween(BigDecimal from, BigDecimal to);
}
