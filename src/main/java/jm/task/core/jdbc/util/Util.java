package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // реализуйте настройку соеденения с БД
    public static final String PASSWORD = "root";
    public static final String USER_NAME = "root";
    public static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    public static Connection connection;
    public static String dr_man;
    private static SessionFactory sessionFactory = null;

    static {
        try {
            dr_man = String.valueOf(DriverManager.getDriver(URL));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection () {
        try {
            return connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }
    public static SessionFactory getHibernateConnection () throws SQLException {


        try {
            Configuration configuration = new Configuration()

                    .setProperty("hibernate.connection.url", URL)
                    .setProperty("hibernate.connection.username", USER_NAME)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

}