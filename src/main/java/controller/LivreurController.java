package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Livreur;
import dao.LivreurDao;
import dao.CommandeDao;
import util.HibernateUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/LivreurController")
public class LivreurController extends HttpServlet {

    private LivreurDao livreurDao;
    private CommandeDao commandeDao;

    public void init() {
        this.livreurDao = new LivreurDao(HibernateUtil.getSessionFactory());
        this.commandeDao = new CommandeDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Livreur> livreurs = livreurDao.findAll();
        request.setAttribute("livreurs", livreurs);
        request.getRequestDispatcher("/livreurView.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String livreurId = request.getParameter("livreurId");

        if (action != null && livreurId != null) {
            processAction(action, Integer.parseInt(livreurId));
        }
        response.sendRedirect("LivreurController");  // Redirect to doGet to refresh data
    }



    private void processAction(String action, int livreurId) {
        System.out.println("Action: " + action + ", Livreur ID: " + livreurId);  // Debug statement
        Livreur livreur = livreurDao.findById(livreurId);
        if (livreur != null && livreur.getCommande() != null) {
            long cmdId = livreur.getCommande().getId();
            String newSituation = "";

            switch (action) {
                case "startDelivery":
                    System.out.println("Current situation: " + livreur.getSituation());  // Debug statement
                    if ("prête".equals(livreur.getSituation())) {
                        newSituation = "en cours de livraison";
                        livreur.setSituation(newSituation);
                        commandeDao.updateSituation(cmdId, "en cours de livraison");
                        System.out.println("Updated to en cours de livraison");  // Debug statement
                    }
                    break;
                case "finalizeDelivery":
                    if ("en cours de livraison".equals(livreur.getSituation())) {
                        newSituation = "commande livrée";
                        livreur.setSituation(newSituation);
                        commandeDao.updateSituation(cmdId, "livrée");
                        System.out.println("Updated to commande livrée");  // Debug statement
                    }
                    break;
            }

            if (!newSituation.isEmpty()) {
                livreurDao.update(livreur);
            }
        } else {
            System.err.println("Livreur not found with ID: " + livreurId);
        }
    }

}
