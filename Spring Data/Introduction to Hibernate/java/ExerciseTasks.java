//import entities.*;
//
//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import java.math.BigDecimal;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Scanner;
//
//public class ExerciseTasks {
//    private EntityManager entityManager;
//    private Scanner scanner;
//
//    public ExerciseTasks(EntityManager entityManager, Scanner scanner) {
//        this.entityManager = entityManager;
//        this.scanner = scanner;
//    }
//
//    //    #2
//    public String removeObjects() {
//        List<Town> towns = entityManager.createQuery("FROM Town", Town.class).getResultList();
//        towns.forEach(town -> {
//            if (town.getName().length() > 5) {
//                entityManager.detach(town);
//            } else {
//                town.setName(town.getName().toLowerCase());
//                entityManager.persist(town);
//            }
//        });
//
//        StringBuilder result = new StringBuilder();
//        towns.forEach(town ->
//                result.append(town.getName()).append(System.lineSeparator()));
//
//        return result.toString();
//    }
//
//    //    #3
//    public String containsEmployee(String fullName) {
//        try {
//            Employee employee = this.entityManager
//                    .createQuery("FROM Employee " +
//                            "WHERE CONCAT_WS(' ', first_name, last_name) = :fullName",
//                            Employee.class)
//                    .setParameter("fullName", fullName)
//                    .getSingleResult();
//
//            return "Yes";
//        } catch (NoResultException e) {
//            return "No";
//        }
//    }
//
//    //    #4
//    public String employeesWithSalaryOver(BigDecimal salaryParam) {
//        List<Employee> employees = this.entityManager
//                .createQuery("FROM Employee " +
//                        "WHERE salary > :salaryParam",
//                        Employee.class)
//                .setParameter("salaryParam", salaryParam)
//                .getResultList();
//
//        StringBuilder result = new StringBuilder();
//        employees.forEach(employee -> result.append(employee.getFirstName()).append(System.lineSeparator()));
//
//        return result.toString();
//    }
//
//    //    #5
//    public String employeesFromResearchAndDevelopment() {
//        String RESEARCH_DEVELOPMENT_DEP_NAME = "Research and Development";
//        Department department = this.entityManager
//                .createQuery("FROM Department " +
//                        "WHERE name = :rndDepartment"
//                        , Department.class)
//                .setParameter("rndDepartment", RESEARCH_DEVELOPMENT_DEP_NAME)
//                .getSingleResult();
//
//        StringBuilder result = new StringBuilder();
//
//        department.getEmployees().stream()
//                .sorted(Comparator.comparing(Employee::getSalary))
//                .forEach(employee -> {
//                    result.append(employee.getFirstName())
//                            .append(" ").append(employee.getLastName())
//                            .append(" from ").append(employee.getDepartment().getName())
//                            .append(" - $").append(employee.getSalary())
//                            .append(System.lineSeparator());
//                });
//
//        return result.toString();
//    }
//
//    //    #6
//    public String addAddressToEmployee(String employeeLastName) {
//        String ADDRESS_TEXT = "Vitoshka 15";
//        Town sofia = entityManager.createQuery("FROM Town " +
//                "WHERE name = 'Sofia'", Town.class).getSingleResult();
//
//        Address address = new Address();
//        address.setText(ADDRESS_TEXT);
//        address.setTown(sofia);
//
//        entityManager.getTransaction().begin();
//
//        entityManager.persist(address);
//
//        Employee employee = entityManager.createQuery("FROM Employee " +
//                "WHERE last_name = :lastName", Employee.class)
//                .setParameter("lastName", employeeLastName)
//                .getSingleResult();
//
//        entityManager.detach(employee);
//        employee.setAddress(address);
//        entityManager.merge(employee);
//
//        entityManager.getTransaction().commit();
//
//        return String.format("Name: %s %s, Address text: %s, Town: %s",
//                employee.getFirstName(),
//                employee.getLastName(),
//                employee.getAddress().getText(),
//                employee.getAddress().getTown().getName());
//    }
//
//    //    #7
//    public String topTenAddressesByEmployeeCount() {
//        List<Address> addresses = entityManager
//                .createQuery("FROM Address", Address.class)
//                .getResultList();
//
//        StringBuilder result = new StringBuilder();
//
//        addresses.stream().sorted((a1, a2) -> {
//            int sort = a2.getEmployees().size() - a1.getEmployees().size();
//            if (sort == 0 && a1.getTown() != null && a2.getTown() != null) {
//                sort = a1.getTown().getId() - a2.getTown().getId();
//            }
//
//            return sort;
//        }).limit(10).forEach(address -> {
//            result.append(address.getText())
//                    .append(", ").append(address.getTown().getName())
//                    .append(" - ").append(address.getEmployees().size()).append(" employees")
//                    .append(System.lineSeparator());
//        });
//
//        return result.toString();
//    }
//
//    //    #8
//    public String getEmployeeProjectsById(int employeeId) {
//        Employee employee = entityManager.createQuery("FROM Employee " +
//                "WHERE employee_id = :employeeId",
//                Employee.class)
//                .setParameter("employeeId", employeeId)
//                .getSingleResult();
//
//        StringBuilder projectNames = new StringBuilder();
//        if (employee.getProjects().isEmpty()) {
//            projectNames.append("Employee has no projects");
//        } else {
//            employee.getProjects()
//                    .stream()
//                    .sorted(Comparator.comparing(Project::getName))
//                    .forEach(project -> projectNames.append("\t").append(project.getName()).append(System.lineSeparator()));
//        }
//
//        return String.format("%s %s - %s\r\n%s",
//                employee.getFirstName(),
//                employee.getLastName(),
//                employee.getJobTitle(),
//                projectNames.toString());
//
//    }
//
//    //    #9
//
//
//}
