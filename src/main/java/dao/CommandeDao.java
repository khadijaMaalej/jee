package dao;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Commande;
import model.User;
import util.HibernateUtil;

import java.util.Date;
import java.util.List;

public class CommandeDao {
    private SessionFactory sessionFactory;

    public CommandeDao() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }
    
   
    

    public void updateSituation(long commandeId, String newSituation) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Commande commande = session.get(Commande.class, commandeId);
            if (commande != null) {
                commande.setSituation(newSituation);
                session.update(commande);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    
    
    public List<Commande> listCommandesOrderByDate() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Commande ORDER BY date", Commande.class).list();
        }
    }
    
    
    public boolean update(Commande commande) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(commande);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    
    
    
    
    
    
    public boolean create(Commande commande) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean success = false;
        try {
            transaction = session.beginTransaction();
            session.persist(commande);
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

    public boolean delete(Commande commande) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean success = false;
        try {
            transaction = session.beginTransaction();
            session.delete(commande);
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

    public List<Commande> getAllCommandes() {
        Session session = sessionFactory.openSession();
        List<Commande> commandes = session.createQuery("from Commande", Commande.class).getResultList();
        session.close();
        return commandes;
    }
    public List<Commande> getCommandesBySituation(String situation) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String queryString = "FROM Commande WHERE situation = :situation";
            Query<Commande> query = session.createQuery(queryString, Commande.class);
            query.setParameter("situation", situation);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Commande> getCommandesByUserId(Long userId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String queryString = "FROM Commande WHERE user.id = :userId";
            Query<Commande> query = session.createQuery(queryString, Commande.class);
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Commande getLastCommandeByUserId(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            // Créez la requête SQL pour sélectionner la dernière commande de l'utilisateur
            String queryString = "FROM Commande WHERE user_id = :userId ORDER BY date DESC";
            Query<Commande> query = session.createQuery(queryString, Commande.class);
            query.setParameter("userId", userId);
            query.setMaxResults(1); // Limitez les résultats à une seule commande (la dernière)
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    
  

    public Commande findById(Long id) {
        Session session = sessionFactory.openSession();
        Commande commande = session.get(Commande.class, id);
        session.close();
        return commande;
    }

    public Long getLastID() {
        Session session = sessionFactory.openSession();
        Long lastId = (Long) session.createQuery("select max(id) from Commande").uniqueResult();
        session.close();
        return lastId;
    }
    
    public List<Commande> getCommandesBySituationEtLivreur(String situation, Long livreurId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String queryString = "FROM Commande WHERE situation = :situation AND livreur.id = :livreurId";
            Query<Commande> query = session.createQuery(queryString, Commande.class);
            query.setParameter("situation", situation);
            query.setParameter("livreurId", livreurId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean updateSituationcom(Long id, String newSituation) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        boolean success = false;
        try {
            tx = session.beginTransaction();
            Commande commande = session.get(Commande.class, id);
            if (commande != null) {
                commande.setSituation(newSituation);
                
                session.update(commande);
                tx.commit();
                success = true;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return success;
    }

    

}
