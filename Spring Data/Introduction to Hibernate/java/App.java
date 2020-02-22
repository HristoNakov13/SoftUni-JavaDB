import services.EmployeeService;
import services.TownService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class App {
    public static void main(String[] args) {
        //INIT


        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TownService townService = new TownService(entityManager);
        EmployeeService employeeService = new EmployeeService(entityManager);


        //------------------------INIT





//        #2
//        int nameShorterThan = 6;
//        System.out.println(townService.setNamesToLowerCaseByNameShorterThan(nameShorterThan));


//        #3
//        String validName = "Svetlin Nakov";
//        String invalidName = "qjwejkqwejkq";

//        System.out.println(employeeService.containsEmployee(validName));
//        System.out.println("------------------");
//        System.out.println(employeeService.containsEmployee(invalidName));

//        #4
//        BigDecimal salary = BigDecimal.valueOf(50000);
//
//        System.out.println(employeeService.getFirstNamesBySalaryGreaterThan(salary));

//        #5
//        String rndDepartment = "Research and Development";
//
//        System.out.println(employeeService.getEmployeesInfoFromDepartment(rndDepartment));

//        #6





    }
}
