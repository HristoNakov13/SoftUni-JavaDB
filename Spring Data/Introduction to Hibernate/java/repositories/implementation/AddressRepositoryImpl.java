package repositories.implementation;

import entities.Address;
import entities.Employee;
import repositories.AddressRepository;
import repositories.EmployeeRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class AddressRepositoryImpl extends RepositoryImpl<Address> implements AddressRepository {
    private EmployeeRepository employeeRepository;

    public AddressRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Address.class);
        this.employeeRepository = new EmployeeRepositoryImpl(entityManager);
    }

    @Override
    public Address findById(int id) {
        return super.findById(id);
    }

    @Override
    public List<Address> findAll() {
        return super.findAll();
    }

    @Override
    public void save(Address address) {
        super.save(address);
    }

    @Override
    public Address saveAndFlush(Address address) {
        return super.saveAndFlush(address);
    }

    @Override
    public List<Address> findTopTenByEmployeeCount() {
        String queryString = "SELECT a FROM Address a " +
                "INNER JOIN a.employees e " +
                "GROUP BY a.id " +
                "ORDER BY COUNT(e.id) DESC";

        return getEntityManager()
                .createQuery(queryString, Address.class)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void deleteById(int id) {
        List<Employee> employees = this.employeeRepository.findByAddressId(id);
        employees.forEach(employee -> {
            employee.setAddress(null);
            this.employeeRepository.save(employee);
        });

        super.deleteById(id);
    }
}
