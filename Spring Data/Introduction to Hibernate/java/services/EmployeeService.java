package services;

import entities.Employee;
import repositories.EmployeeRepository;
import repositories.implementation.EmployeeRepositoryImpl;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.stream.Collectors;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EntityManager entityManager) {
        this.employeeRepository = new EmployeeRepositoryImpl(entityManager);
    }

    public String containsEmployee(String fullName) {
        return this.employeeRepository.findByFullName(fullName)
                .isEmpty() ? "No" : "Yes";
    }

    public String getFirstNamesBySalaryGreaterThan(BigDecimal salary) {
        return this.employeeRepository.findBySalaryGreaterThan(salary)
                .stream()
                .map(Employee::getFirstName)
                .collect(Collectors.joining("\r\n"));
    }

    public String getEmployeesInfoFromDepartment(String departmentName) {
        return this.employeeRepository.findByDepartment(departmentName)
                .stream()
                .map(employee ->
                        String.format("%s %s from %s - $%s",
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getDepartment().getName(),
                            employee.getSalary()))
                .collect(Collectors.joining("\r\n"));
    }
}
