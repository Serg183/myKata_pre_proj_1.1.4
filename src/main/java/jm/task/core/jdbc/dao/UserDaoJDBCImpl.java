package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public static final String PASSWORD = "root";
    public static final String USER_NAME = "root";
    public static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    public static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users1 (" +
                    "id int auto_increment primary key," +
                    "name varchar(45) not null," +
                    "lastName varchar(45) not null," +
                    "age int not null)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Users1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        /*try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO Users1 (name, lastName, age) values ("
                    + name + ", " + lastName + ", " + (int) age + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        try (PreparedStatement pstm = connection.prepareStatement("INSERT INTO Users1 (name, lastName, age) VALUES (?, ?, ?)")) {
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement prepared_statement = connection.prepareStatement("DELETE FROM Users1 WHERE id= ? ")) {
            prepared_statement.setString(1, String.valueOf(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users1");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM Users1");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
