package com.example.demo.util.filereader;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface FileReader {
    List<String> getFileContent(String fileName) throws IOException;
}
