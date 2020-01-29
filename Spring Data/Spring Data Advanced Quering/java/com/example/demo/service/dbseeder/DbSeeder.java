package com.example.demo.service.dbseeder;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Component
public interface DbSeeder {
    void seedDb(String fileName) throws IOException;
}
