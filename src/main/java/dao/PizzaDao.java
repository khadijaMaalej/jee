package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.Pizza;
import util.HibernateUtil;

import java.util.List;

public class PizzaDao {

    private SessionFactory sessionFactory;

    public PizzaDao() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Pizza findById(long id) {
        Session session = sessionFactory.openSession();
        Pizza pizza = session.get(Pizza.class, id);
        session.close();
        return pizza;
    }

    public List<Pizza> findAll() {
        List<Pizza> pizzas = null;
        try (Session session = sessionFactory.openSession()) {
            pizzas = session.createQuery("from Pizza", Pizza.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception to understand if there's an issue during query execution
        }
        return pizzas;
    }

}

