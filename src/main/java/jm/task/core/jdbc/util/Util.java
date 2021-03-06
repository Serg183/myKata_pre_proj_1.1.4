package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
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
    private static final String PASSWORD = "root";
    private static final String USER_NAME = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static SessionFactory getHibernateConnection() {
        SessionFactory sessionFactory = null;

        try {
            Configuration configuration = new Configuration()

                    .setProperty("hibernate.connection.url", URL)
                    .setProperty("hibernate.connection.username", USER_NAME)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            throw new NullPointerException("sessionFactory == null");
        }
        return sessionFactory;
    }

    public static Session getOpenSession() {
        Session session = null;
        try {
            SessionFactory sessionFactory = getHibernateConnection();
            session = sessionFactory.openSession();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return session;
    }
}