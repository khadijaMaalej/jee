package dao;

import java.util.List;
import org.hibernate.query.Query;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Commande;
import model.User;
import util.HibernateUtil;

public class UserDAO {
   
    SessionFactory sessionFactory;

    public UserDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public User findById(long id) {
        Session session = sessionFactory.openSession();
        User p = session.get(User.class, id);
        session.close();
        return p;
    }
    
    

    public boolean checkNomExistence(String nom) {
        Session session = sessionFactory.openSession();
        boolean exists = false;
        try {
            Query query = session.createQuery("SELECT COUNT(*) FROM User WHERE nom = :nom");
            query.setParameter("nom", nom);
            long count = (Long) query.uniqueResult();
            exists = count > 0;
        } finally {
            session.close();
        }
        return exists;
    }

    public User getUserByNom(String nom) {
        Session session = sessionFactory.openSession();
        User user = null;
        try {
            Query query = session.createQuery("FROM User WHERE nom = :nom");
            query.setParameter("nom", nom);
            user = (User) query.uniqueResult();
        } finally {
            session.close();
        }
        return user;
    }

    public boolean create(User u) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        boolean success = false;
        try {
            tx = session.beginTransaction();
            session.persist(u);
            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return success;
    }

   
    

    public List<User> findAll() {
        Session session = sessionFactory.openSession();
        List<User> results = session.createQuery("from User", User.class).getResultList();
        session.close();
        return results;
    }
}
