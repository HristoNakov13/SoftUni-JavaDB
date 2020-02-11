package demo.shop.util.exporters;

import demo.shop.util.parsers.Parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class JsonFileWriter {
    private final String EXPORTS_DIRECTORY_PATH = "src\\main\\resources\\exports\\";
    private final String JSON_EXTENSION = ".json";
    Parser parser;

    public JsonFileWriter(Parser parser) {
        this.parser = parser;
    }

    public void exportDataToFile(String filename, Object data) throws IOException {
        File file = this.createResourceFile(filename);
        String dataString = this.parser.toJson(data);

        this.writeData(dataString, file);
    }

    private void writeData(String data, File file) {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
            output.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createResourceFile(String filename) throws IOException {
        String path = this.createFilePath(filename);
        File file = new File(path);

        if (file.exists()) {
            throw new IllegalArgumentException("File with that name already exists.");
        }
        file.createNewFile();

        return file;
    }

    private String createFilePath(String filename) {
        return String.format("%s%s%s", EXPORTS_DIRECTORY_PATH, filename, JSON_EXTENSION);
    }
}
