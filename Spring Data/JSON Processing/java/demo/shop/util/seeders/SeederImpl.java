package demo.shop.util.seeders;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public abstract class SeederImpl implements Seeder {

    protected String getFileContent(File file) throws IOException {
        BufferedReader bfr = new BufferedReader(new java.io.FileReader(file));
        StringBuilder content = new StringBuilder();

        String line;
        while ((line = bfr.readLine()) != null) {
            content.append(line);
        }

        return content.toString();
    }
}
