package minions.services;

import minions.repositories.MinionRepository;
import minions.util.DatabaseConnector;

public class MinionService {
    private DatabaseConnector databaseConnector;
    private MinionRepository minionRepository;

    public MinionService(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    private DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }
}
