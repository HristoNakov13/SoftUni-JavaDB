package com.example.demo.util.filereader;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtilImpl implements FileUtil {
    public FileUtilImpl() {
    }

    @Override
    public List<String> getFileContent(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

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
