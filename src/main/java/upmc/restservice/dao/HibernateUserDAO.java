package upmc.restservice.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import upmc.restservice.dto.User;
import upmc.restservice.dao.UserDAO;
import upmc.restservice.util.HibernateUtil;

public class HibernateUserDAO implements UserDAO {

    private SessionFactory sessionFactory;

    public HibernateUserDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void addUser(User user) {
        sessionFactory.openSession().save(user);
    }

    public User findUserByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("key", name));
        return (User) criteria.uniqueResult();
    }

    public User find(String name, String pw) {
        //Session session = sessionFactory.getCurrentSession();
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("name", name));
        criteria.add(Restrictions.eq("password", pw));
        return (User) criteria.uniqueResult();
    }

    public void deleteUser(String key) {
        sessionFactory.getCurrentSession().delete(findUserByName(key));
    }

    public void updateUser(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    public List<User> listUser() {
        return sessionFactory.getCurrentSession().createCriteria(User.class).list();
    }
}