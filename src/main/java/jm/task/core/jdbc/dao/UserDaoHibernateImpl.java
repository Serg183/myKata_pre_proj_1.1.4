package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null;

    public UserDaoHibernateImpl() throws SQLException {

    }


    @Override
    public void createUsersTable() {
            try (Session session = Util.getOpenSession()) {
                transaction = session.beginTransaction();
                session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users1 (" +
                        "id int auto_increment primary key," +
                        "name varchar(45) not null," +
                        "lastName varchar(45) not null," +
                        "age int not null)").executeUpdate();
                System.out.println("table created");
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getOpenSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users1").executeUpdate();
            System.out.println("table droped");
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getOpenSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            System.out.println("User with name – " + name + " was added in DB");
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
            try (Session session = Util.getOpenSession()) {
                transaction = session.beginTransaction();
                session.remove(session.get(User.class, id));
                System.out.println("User with name –  deleted");
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = Util.getOpenSession()) {
            transaction = session.beginTransaction();
            list = session.createQuery("FROM User", User.class).getResultList();
            System.out.println(list);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getOpenSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate table users1").executeUpdate();
            System.out.println("Users deleted clean users1 table");
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}