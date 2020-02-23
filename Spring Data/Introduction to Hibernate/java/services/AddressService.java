package services;

import entities.Address;
import entities.Town;
import repositories.AddressRepository;
import repositories.implementation.AddressRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class AddressService {
    private AddressRepository addressRepository;

    public AddressService(EntityManager entityManager) {
        this.addressRepository = new AddressRepositoryImpl(entityManager);
    }

    public Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);

        return this.addressRepository.saveAndFlush(address);
    }

    public String getInfoForTopTenByEmployeesCount() {
        return this.addressRepository
                .findTopTenByEmployeeCount()
                .stream()
                .filter(address -> address.getEmployees() != null)
                .map(address -> {
                    String townName = address.getTown() == null ? "" : ", " + address.getTown().getName();

                    return String.format("%s%s - %s employees",
                            address.getText(),
                            townName,
                            address.getEmployees().size());
                        }
                ).collect(Collectors.joining("\r\n"));
    }
}
