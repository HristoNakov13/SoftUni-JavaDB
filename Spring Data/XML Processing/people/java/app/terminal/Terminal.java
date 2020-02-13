package app.terminal;

import app.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Terminal implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    @Override
    public void run(String... strings) throws Exception {

    }
}
