package controller;

import dao.ChefDao;
import dao.CommandeDao;
import dao.LivreurDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Chef;
import model.Livreur;
import util.HibernateUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/chefController")
public class ChefController extends HttpServlet {
    private ChefDao chefDao = new ChefDao();
    private CommandeDao commandeDao = new CommandeDao();
    private LivreurDao livreurDao; // Declare the LivreurDao instance

    public void init() {
        // Initialize LivreurDao with SessionFactory during servlet initialization
        this.livreurDao = new LivreurDao(HibernateUtil.getSessionFactory());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String commandeId = request.getParameter("commandeId");
        String filter = request.getParameter("filter");

        if (action != null && commandeId != null) {
            try {
                long cmdId = Long.parseLong(commandeId);
                Chef chef = chefDao.findByCommandeId(cmdId);

                if (chef != null) {
                    if ("start".equals(action)) {
                        commandeDao.updateSituation(cmdId, "en cours");
                        chef.setSituation("en cours");
                        chefDao.update(chef);
                    } else if ("finalize".equals(action)) {
                        commandeDao.updateSituation(cmdId, "prête");
                        chef.setSituation("prête");
                        chefDao.update(chef);

                        // Create and save a new Livreur entry
                        Livreur newLivreur = new Livreur();
                        newLivreur.setUser(chef.getUser()); // Assuming the Livreur is the same user as the Chef
                        newLivreur.setCommande(chef.getCommande());
                        newLivreur.setChef(chef);
                        newLivreur.setSituation("prête");
                        newLivreur.setDateConfirme(new java.util.Date());
                        if (!livreurDao.create(newLivreur)) {
                            System.err.println("Failed to create Livreur entry.");
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing commande ID: " + e.getMessage());
            }
        }
                    
                    
       
        
        


        // Handle filter actions for displaying chefs
        List<Chef> chefs;
        if (filter == null || filter.equals("tout")) {
            chefs = chefDao.findAll();
        } else {
            chefs = chefDao.findBySituation(filter);
        }

        request.setAttribute("chefs", chefs);
        request.setAttribute("currentFilter", filter);
        request.getRequestDispatcher("chefView.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Implement if needed for form submissions
    }
}