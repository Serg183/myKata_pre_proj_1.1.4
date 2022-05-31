package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // реализуйте настройку соеденения с БД
    public static final String PASSWORD = "root";
    public static final String USER_NAME = "root";
    public static final String URL = "jdbc:mysql://localhost:3306/mysql";
    public static Connection connection;

    public static Connection getConnection () {
        try {
            return connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }
}