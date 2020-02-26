package services;

import entities.Address;
import entities.Department;
import entities.Employee;
import entities.Project;
import repositories.DepartmentRepository;
import repositories.EmployeeRepository;
import repositories.implementation.DepartmentRepositoryImpl;
import repositories.implementation.EmployeeRepositoryImpl;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;

    public EmployeeService(EntityManager entityManager) {
        this.employeeRepository = new EmployeeRepositoryImpl(entityManager);
        this.departmentRepository = new DepartmentRepositoryImpl(entityManager);
    }

    public String containsEmployee(String fullName) {
        return this.employeeRepository.findByFullName(fullName)
                .isEmpty() ? "No" : "Yes";
    }

    public String getFirstNamesBySalaryGreaterThan(BigDecimal salary) {
        return this.employeeRepository
                .findBySalaryGreaterThan(salary)
                .stream()
                .map(Employee::getFirstName)
                .collect(Collectors.joining("\r\n"));
    }

    public String getEmployeesInfoFromDepartment(String departmentName) {
        return this.employeeRepository
                .findByDepartment(departmentName)
                .stream()
                .sorted((e1, e2) -> {
                    int sort = (e1.getSalary().subtract(e2.getSalary())).intValue();
                    if (sort == 0) {
                        sort = e1.getId() - e2.getId();
                    }

                    return sort;
                })
                .map(employee ->
                        String.format("%s %s from %s - $%s",
                                employee.getFirstName(),
                                employee.getLastName(),
                                employee.getDepartment().getName(),
                                employee.getSalary()))
                .collect(Collectors.joining("\r\n"));
    }

    public String changeAddressByLastName(String lastName, Address address) {
        List<Employee> employees = this.employeeRepository.findByLastName(lastName);

        return employees.stream()
                .map(employee -> {
                    employee.setAddress(address);
                    Employee editedEmployee = this.employeeRepository.saveAndFlush(employee);

                    return String.format("Employee: %s %s, Address: %s",
                            editedEmployee.getFirstName(),
                            editedEmployee.getLastName(),
                            editedEmployee.getAddress().getText());
                }).collect(Collectors.joining("\r\n"));
    }

    public String getEmployeeProjectsById(int employeeId) {
        Employee employee = this.employeeRepository.findById(employeeId);

        if (employee == null) {
            return String.format("No employee found with ID: %s", employeeId);
        }

        String employeeNameAndJob = String.format("%s %s - %s\r\n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle());

        return employeeNameAndJob +
                this.employeeRepository.findById(employeeId)
                        .getProjects()
                        .stream()
                        .sorted(Comparator.comparing(Project::getName))
                        .map(project -> String.format("\t%s", project.getName()))
                        .collect(Collectors.joining("\r\n"));
    }

    public String raiseSalaryByPercentageForDepartment(double percentage, String departmentName) {
        List<Employee> employees = this.employeeRepository.findByDepartment(departmentName);

        if (employees.isEmpty()) {
            Department department = this.departmentRepository.findByName(departmentName);

            return department == null
                    ? String.format("Department %s does not exist.", departmentName)
                    : String.format("No employees for %s department", departmentName);
        }

        return employees.stream()
                .map(employee -> {
                    BigDecimal increasedSalary = this.calculateSalaryRaise(percentage, employee.getSalary());
                    employee.setSalary(increasedSalary);
                    Employee editedEmployee = this.employeeRepository.saveAndFlush(employee);

                    return String.format("%s %s ($%s)",
                            editedEmployee.getFirstName(),
                            editedEmployee.getLastName(),
                            editedEmployee.getSalary());
                }).collect(Collectors.joining("\r\n"));
    }

    private BigDecimal calculateSalaryRaise(double percentage, BigDecimal salary) {
        BigDecimal asDecimalValue = BigDecimal.valueOf(percentage / 100);

        return salary.add(salary.multiply(asDecimalValue));
    }

    public String getEmployeeInfoByNameStartingWith(String startingWith) {
        List<Employee> employees = this.employeeRepository.findByFirstNameStartingWith(startingWith);

        if (employees.isEmpty()) {
            return String.format("No employees with names starting with: \"%s\" found.", startingWith);
        }

        return employees
                .stream()
                .map(employee ->
                        String.format("%s %s - %s - ($%s)",
                                employee.getFirstName(),
                                employee.getLastName(),
                                employee.getJobTitle(),
                                employee.getSalary()))
                .collect(Collectors.joining("\r\n"));
    }
}