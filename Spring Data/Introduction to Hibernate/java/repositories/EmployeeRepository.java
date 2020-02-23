package repositories;

import entities.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeRepository {
    Employee findById(int id);

    List<Employee> findAll();

    void save(Employee employee);

    Employee saveAndFlush(Employee employee);

    List<Employee> findByFullName(String fullName);

    List<Employee> findBySalaryGreaterThan(BigDecimal salary);

    List<Employee> findByDepartment(String departmentName);

    List<Employee> findByLastName(String lastName);

    List<Employee> findByAddressId(int addressId);

    List<Employee> findByFirstNameStartingWith(String startingWith);
}
