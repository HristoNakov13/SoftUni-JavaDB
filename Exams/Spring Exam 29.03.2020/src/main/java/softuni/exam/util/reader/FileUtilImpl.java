package softuni.exam.util.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtilImpl implements FileUtil{
    @Override
    public String getFileContent(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}
