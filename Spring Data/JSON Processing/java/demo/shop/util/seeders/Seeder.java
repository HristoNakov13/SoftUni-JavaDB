package demo.shop.util.seeders;

import java.io.IOException;

public interface Seeder {
    void jsonSeedDb(String resourceFilename) throws IOException;
}
