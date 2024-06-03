package dao;

import model.Chef;
import model.Livreur;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class LivreurDao {
    private SessionFactory sessionFactory;

    public LivreurDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<Livreur> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Livreur", Livreur.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Livreur findById(int livreurId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Livreur.class, livreurId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    public boolean update(Livreur livreur) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(livreur);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    
    
    public boolean updateLivreurAndChef(Livreur livreur, String newSituation) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Update Livreur situation
            livreur.setSituation(newSituation);
            session.update(livreur);

            // Update Chef situation if necessary
            Chef chef = livreur.getChef();
            if (chef != null) {
                chef.setSituation(newSituation);
                session.update(chef);
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    
    
    
    
    

    public boolean create(Livreur livreur) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(livreur);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Consider using a logger here instead of printStackTrace
            return false;
        }
    }
}

