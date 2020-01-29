package com.example.demo.service.dbseeder;

import com.example.demo.util.datamap.EntityMapper;
import com.example.demo.util.filereader.FileUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public abstract class DbSeederImpl implements DbSeeder {
    private EntityMapper entityMapper;
    private FileUtil fileReader;

    public DbSeederImpl(EntityMapper entityMapper, FileUtil fileReader) {
        this.entityMapper = entityMapper;
        this.fileReader = fileReader;
    }

    private File getResourceFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        System.out.println(file.getName());

        return file;
    }

    protected List<String> getFileContent(String fileName) throws IOException {
        File file = this.getResourceFile(fileName);

        return this.getFileReader().getFileContent(file);
    }

    protected FileUtil getFileReader() {
        return fileReader;
    }

    protected EntityMapper getEntityMapper() {
        return entityMapper;
    }
}
