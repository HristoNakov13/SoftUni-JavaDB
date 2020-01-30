package com.example.demo.util.filereader;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileReaderImpl implements FileReader {

    public FileReaderImpl() {
    }

    private File getResourceFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        System.out.println(file.getName());

        return file;
    }

    public List<String> getFileContent(String fileName) throws IOException {
        File file = this.getResourceFile(fileName);

        BufferedReader reader = new BufferedReader(new java.io.FileReader(file));

        List<String> content = new ArrayList<>();
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.isEmpty()) {
                continue;
            }

            content.add(currentLine);
        }

        return content;
    }
}
