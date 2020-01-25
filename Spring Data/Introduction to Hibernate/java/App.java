import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        ExerciseTasks exerciseTasks = new ExerciseTasks(entityManager, scanner);

        // #2

//        System.out.println(exerciseTasks.removeObjects());

        // #3
//        String fullName = scanner.nextLine();
//        System.out.println(exerciseTasks.containsEmployee(fullName));

        // #4
//        BigDecimal salaryOver = BigDecimal.valueOf(Double.parseDouble(scanner.nextLine()));
//        System.out.println(exerciseTasks.employeesWithSalaryOver(salaryOver));

        // #5
//        System.out.println(exerciseTasks.employeesFromResearchAndDevelopment());

        // #6
//        String employeeLastName = scanner.nextLine();
//        System.out.println(exerciseTasks.addAddressToEmployee(employeeLastName));

        // #7
//        System.out.println(exerciseTasks.topTenAddressesByEmployeeCount());

        // #8
//        int employeeId = Integer.parseInt(scanner.nextLine());
//        System.out.println(exerciseTasks.getEmployeeProjectsById(employeeId));
    }
}
