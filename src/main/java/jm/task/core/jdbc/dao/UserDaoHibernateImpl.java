package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getHibernateConnection();

    public UserDaoHibernateImpl() throws SQLException {

    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users2 (" +
                "id int auto_increment primary key," +
                "name varchar(45) not null," +
                "lastName varchar(45) not null," +
                "age int not null)").executeUpdate();
        System.out.println("table created");
        session.getTransaction().commit();
        session.close();


    }

    @Override
    public void dropUsersTable() {

        Session session = sessionFactory.openSession();

        //session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS Users2").executeUpdate();

        session.getTransaction().commit();
        System.out.println("DROP TABLE IF EXISTS Users2");
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        System.out.println("User with name – " + name + " was added in DB");

        session.close();


    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.remove(user);
        session.getTransaction().commit();
        System.out.println("User with name – " + user.getName() + " delited");
        session.close();

    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        //Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
;
        List<User> list = session.createQuery("FROM User", User.class).getResultList();

        session.getTransaction().commit();
        System.out.println(list);
        session.close();
        //sessionFactory.close();

        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        //Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("truncate table users1").executeUpdate();
        System.out.println("Users deleted clean users1 table");
        session.getTransaction().commit();

        session.close();
    }
}
