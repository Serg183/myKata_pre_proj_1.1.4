package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection connection = Util.getConnection();

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
        try (PreparedStatement sqlQueryWithVariebles = connection.prepareStatement("INSERT INTO Users1 (name, lastName, age) VALUES (?, ?, ?)")) {
            sqlQueryWithVariebles.setString(1, name);
            sqlQueryWithVariebles.setString(2, lastName);
            sqlQueryWithVariebles.setByte(3, age);
            sqlQueryWithVariebles.executeUpdate();
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
