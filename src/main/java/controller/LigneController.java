package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Commande;
import model.LingeDeCommande;
import model.Panier;
import model.Pizza;
import model.User;
import dao.CommandeDao;
import dao.LigneCommandeDao;
import dao.PizzaDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ligneController")
public class LigneController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CommandeDao commandeDao;
    private LigneCommandeDao ligneCommandeDao;
    private PizzaDao pizzaDao;

    public LigneController() {
        super();
        commandeDao = new CommandeDao();
        ligneCommandeDao = new LigneCommandeDao();
        pizzaDao = new PizzaDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Panier> panier = (List<Panier>) session.getAttribute("paniers");

        Long commandeId = commandeDao.getLastID();
        System.out.println("Identifiant de la commande : " + commandeId);

        for (Panier elementPanier : panier) {
            Pizza pizza = pizzaDao.findById(elementPanier.getPizzaId());
            if (pizza != null) {
                Commande commande = commandeDao.findById(commandeId);
                if (commande != null) {
                    commande.getLigneCommandes().size(); // Chargez explicitement la collection ligneCommandes

                    LingeDeCommande nouvelleLigne = new LingeDeCommande();
                    nouvelleLigne.setCommande(commande);
                    nouvelleLigne.setPizza(pizza);
                    nouvelleLigne.setQuantite(elementPanier.getQuantite());
                    nouvelleLigne.setPrix(elementPanier.getprix());
                    nouvelleLigne.setMessage(elementPanier.getMessage());
                    nouvelleLigne.setsize(elementPanier.getsize());

                    

                    ligneCommandeDao.create(nouvelleLigne);
                } else {
                    System.out.println("Échec de récupération de la commande avec l'ID : " + commandeId);
                }
            } else {
                System.out.println("Échec de récupération de la pizza avec l'ID : " + elementPanier.getPizzaId());
            }
        }

        // Maintenant, récupérez uniquement la commande associée au nouveau commandeId
        Commande nouvelleCommande = commandeDao.findById(commandeId);
        List<Commande> commandes = new ArrayList<>();
        if (nouvelleCommande != null) {
            commandes.add(nouvelleCommande);
        }

        // Transmettez les commandes à votre JSP pour affichage
        request.setAttribute("commandes", commandes);
        request.getRequestDispatcher("Commande.jsp").forward(request, response);
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
