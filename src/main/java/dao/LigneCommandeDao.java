package dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import model.LingeDeCommande;
import util.HibernateUtil;

import java.util.List;

public class LigneCommandeDao {
    private SessionFactory sessionFactory;

    public LigneCommandeDao() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public boolean create(LingeDeCommande ligneDeCommande) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean success = false;
        try {
            transaction = session.beginTransaction();
            session.persist(ligneDeCommande);
            transaction.commit();
            success = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return success;
    }

    public boolean delete(LingeDeCommande ligneDeCommande) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean success = false;
        try {
            transaction = session.beginTransaction();
            session.delete(ligneDeCommande);
            transaction.commit();
            success = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return success;
    }

    public List<LingeDeCommande> getAllLignesDeCommande() {
        Session session = sessionFactory.openSession();
        List<LingeDeCommande> lignesDeCommande = session.createQuery("from LigneDeCommande", LingeDeCommande.class).getResultList();
        session.close();
        return lignesDeCommande;
    }
}
