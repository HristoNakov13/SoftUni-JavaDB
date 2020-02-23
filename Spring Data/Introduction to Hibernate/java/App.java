import entities.Address;
import entities.Employee;
import repositories.AddressRepository;
import repositories.DepartmentRepository;
import repositories.EmployeeRepository;
import repositories.implementation.AddressRepositoryImpl;
import repositories.implementation.DepartmentRepositoryImpl;
import repositories.implementation.EmployeeRepositoryImpl;
import services.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        //INIT

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TownService townService = new TownService(entityManager);
        EmployeeService employeeService = new EmployeeService(entityManager);
        AddressService addressService = new AddressService(entityManager);
        ProjectService projectService = new ProjectService(entityManager);
        DepartmentService departmentService= new DepartmentService(entityManager);


        //------------------------INIT


//        #2
//        int nameShorterThan = 6;
//        System.out.println(townService.setNamesToLowerCaseByNameShorterThan(nameShorterThan));
//
//
////        #3
//        String validName = "Svetlin Nakov";
//        String invalidName = "qjwejkqwejkq";
//
//        System.out.println(employeeService.containsEmployee(validName));
//        System.out.println("------------------");
//        System.out.println(employeeService.containsEmployee(invalidName));
//
////        #4
//        BigDecimal salary = BigDecimal.valueOf(50000);
//
//        System.out.println(employeeService.getFirstNamesBySalaryGreaterThan(salary));
//
////        #5
//        String rndDepartment = "Research and Development";
////
//        System.out.println(employeeService.getEmployeesInfoFromDepartment(rndDepartment));
//
////        #6
//        String lastName = "Brown";
//        String addressText = "Vitoshka 15";
//        Address address = addressService.createAddress(addressText);
//
//        System.out.println(employeeService.changeAddressByLastName(lastName, address));
//
////        #7
//        System.out.println(addressService.getInfoForTopTenByEmployeesCount());
//
////        #8
//        int employeeId = 147;
//        int invalidId = 5414214;
//        System.out.println(employeeService.getEmployeeProjectsById(employeeId));
//        System.out.println("---------------------");
//        System.out.println(employeeService.getEmployeeProjectsById(invalidId));
//
////        #9
//        System.out.println(projectService.getLastTenProjectsInfo());
//
////        #10
//        double percentage = 12;
//        String[] departments = {"Engineering", "Tool Design", "Marketing", "Information Services"};
//
//        Arrays.stream(departments)
//                .map(department -> employeeService.raiseSalaryByPercentageForDepartment(percentage, department))
//                .forEach(System.out::println);
//
//        System.out.println("-------------");
//        String invalidDepartment = "qweqweqweqwe";
//        System.out.println(employeeService.raiseSalaryByPercentageForDepartment(percentage, invalidDepartment));
//
////
////        #11
//        String townName = "Seattle";
//        System.out.println(townService.deleteTownAndItsAddresses(townName));
//
////        #12
//        String startingWith = "SA";
//        String invalid = "123123";
//        System.out.println(employeeService.getEmployeeInfoByNameStartingWith(startingWith));
//        System.out.println("---------------");
//        System.out.println(employeeService.getEmployeeInfoByNameStartingWith(invalid));
//
////        #13
//        BigDecimal from = BigDecimal.valueOf(30000);
//        BigDecimal to = BigDecimal.valueOf(70000);
//
//        System.out.println(departmentService.getDepartmentMaxSalaryNotInRange(from, to));
    }
}
