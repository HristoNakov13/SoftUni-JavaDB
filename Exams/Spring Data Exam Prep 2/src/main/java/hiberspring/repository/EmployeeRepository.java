package hiberspring.repository;

import hiberspring.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e " +
            "FROM Employee e " +
            "INNER JOIN Branch b " +
            "ON e.branch.id = b.id " +
            "INNER JOIN Product p " +
            "ON b.id = p.branch.id " +
            "GROUP BY b.id " +
            "HAVING COUNT(p.id) > 0 " +
            "ORDER BY CONCAT(e.firstName, ' ', e.lastName), LENGTH(e.position) DESC")
    List<Employee> findEmployeesWorkingInBranchWithProducts();
}
