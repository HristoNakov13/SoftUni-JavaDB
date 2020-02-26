package services;

import entities.Department;
import entities.Employee;
import repositories.DepartmentRepository;
import repositories.implementation.DepartmentRepositoryImpl;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentService {
    private DepartmentRepository departmentRepository;

    public DepartmentService(EntityManager entityManager) {
        this.departmentRepository = new DepartmentRepositoryImpl(entityManager);
    }

    public String getDepartmentMaxSalaryNotInRange(BigDecimal from, BigDecimal to) {
        List<Department> departments = this.departmentRepository.findByMaxSalaryNotBetween(from, to);

        if (departments.isEmpty()) {
            return String.format("No departments found with max salary not in range from: %s to: %s",
                    from,
                    to);
        }

        return departments.stream()
                .map(department -> {
                    BigDecimal maxSalary = department.getEmployees()
                            .stream()
                            .map(Employee::getSalary)
                            .max(Comparator.naturalOrder())
                            .orElse(BigDecimal.valueOf(0));

                    return String.format("%s %s",
                            department.getName(),
                            maxSalary);
                }).collect(Collectors.joining("\r\n"));
    }
}