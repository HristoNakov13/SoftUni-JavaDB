package repositories;

import entities.Address;

import java.util.List;

public interface AddressRepository {
    Address findById(int id);

    List<Address> findAll();

    void save(Address address);

    Address saveAndFlush(Address address);

    List<Address> findTopTenByEmployeeCount();

    void deleteById(int id);
}
