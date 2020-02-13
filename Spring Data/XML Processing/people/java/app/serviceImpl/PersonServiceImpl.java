package app.serviceImpl;

import app.domain.model.Person;
import app.repository.PersonRepository;
import app.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;


    @Override
    public void create(Person person) {
        this.personRepository.saveAndFlush(person);
    }

    @Override
    public Person findById(long id) {
        return this.personRepository.findById(id).get();
    }

    @Override
    public List<Person> findByCountry(String country) {
        return this.personRepository.findByCountry(country);
    }
}
