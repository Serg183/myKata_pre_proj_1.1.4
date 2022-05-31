package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //реализуйте алгоритм здесь
        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("name1", "familia1", (byte) 1);
        userDao.saveUser("name1", "familia2", (byte) 12);
        userDao.saveUser("name2", "familia3", (byte) 19);
        userDao.saveUser("name3", "familia4", (byte) 101);

        userDao.removeUserById(1);
        userDao.getAllUsers();


        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
