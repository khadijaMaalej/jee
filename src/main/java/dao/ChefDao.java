package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Chef;
import model.Livreur;
import util.HibernateUtil;

import java.util.List;

public class ChefDao {
    private SessionFactory sessionFactory;

    public ChefDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    
    
    public List<Chef> findBySituation(String situation) {
        try (Session session = sessionFactory.openSession()) {
            Query<Chef> query = session.createQuery("FROM Chef WHERE situation = :situation", Chef.class);
            query.setParameter("situation", situation);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    

    
    
    
    public Chef findByCommandeId(long commandeId) {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "FROM Chef c WHERE c.commande.id = :commandeId";
            Query<Chef> query = session.createQuery(queryString, Chef.class);
            query.setParameter("commandeId", commandeId);
            return query.uniqueResult();  // assumes there is one chef per commande
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // Méthode pour créer une nouvelle entrée Chef
    public boolean create(Chef chef) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(chef);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour mettre à jour un Chef existant
    public boolean update(Chef chef) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(chef);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour supprimer un Chef
    public boolean delete(Chef chef) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(chef);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour trouver un Chef par son ID
    public Chef findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Chef.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour lister tous les chefs
    public List<Chef> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Chef", Chef.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour récupérer les commandes gérées par un chef spécifique
    public List<Chef> findByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Chef> query = session.createQuery("FROM Chef c WHERE c.user.id = :userId", Chef.class);
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

