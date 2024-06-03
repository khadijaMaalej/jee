package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Panier;
import util.HibernateUtil;

import java.util.List;

public class PanierDao {
    private SessionFactory sessionFactory;

    public PanierDao() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public boolean create(Panier p) {

    	Session session=sessionFactory.openSession();
    	Transaction tx=null;
    	boolean success = false;
    	try {

    	tx = session.beginTransaction();
    	session.persist(p);
    	tx.commit();
    	success=true;

    	}
    	catch (Exception e) { if (tx!=null) tx.rollback(); throw e; }
    	finally { session.close(); }
    	return success;

    	}

    public boolean supprimerDuPanier(Long pizzaId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean success = false;
        try {
            transaction = session.beginTransaction();
            Panier panierItem = session.get(Panier.class, pizzaId);
            if (panierItem != null) {
                session.delete(panierItem);
                success = true;
            }
            transaction.commit();
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

    public List<Panier> obtenirContenuPanier() {
        Session session = sessionFactory.openSession();
        List<Panier> panierItems = session.createQuery("from PanierItem", Panier.class).getResultList();
        session.close();
        return panierItems;
    }
}

