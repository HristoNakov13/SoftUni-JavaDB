package minions.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private String username;
    private String password;
    private String databaseName;
    private String databaseUrl;
    private Connection connection;

    public DatabaseConnector(String username, String password, String databaseName, String databaseUrl) {
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.databaseUrl = databaseUrl;
    }

    public void setupConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", this.username);
        props.setProperty("password", this.password);

        this.connection = DriverManager.getConnection(this.databaseUrl + this.databaseName, props);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public Connection getConnection() {
        return connection;
    }
}
