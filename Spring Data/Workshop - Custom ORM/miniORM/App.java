import entities.User;
import orm.Connector;
import orm.DbManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection connection = Connector.getConnection("root", "1234", "internet_cafe");

        DbManager<User> dbManager = new DbManager<>(connection, User.class);



        String whereQuery = "WHERE age = 16";

//        Iterable<User> users = dbManager.getAll(whereQuery);
//        System.out.println("-------------");
//        users.forEach(System.out::println);
//
//        System.out.println("-------------");
//        User first = dbManager.findFirst();
//        System.out.println(first);
//        System.out.println("-------------");
//        User firstWhere = dbManager.findFirst(whereQuery);
//        System.out.println(firstWhere);

        User ivo = new User("Ivo", 25, Date.valueOf("2018-01-01"));
        dbManager.insert(ivo);
        System.out.println("-------------");

        User user = dbManager.findById(3);
        System.out.println(user);
    }
}
