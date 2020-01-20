import java.sql.*;
import java.util.Properties;

public class DBConnector {
    public static void main(String[] args) throws SQLException {

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        PreparedStatement stmt =
                connection.prepareStatement("SELECT name FROM villains");

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            System.out.println(rs.getString("name"));
        }
        connection.close();
    }
}