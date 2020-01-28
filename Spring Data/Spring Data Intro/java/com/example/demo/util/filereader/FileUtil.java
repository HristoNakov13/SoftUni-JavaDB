package com.example.demo.util.filereader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileUtil {
    List<String> getFileContent(File file) throws IOException;
}
