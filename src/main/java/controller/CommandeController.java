package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Chef;
import model.Commande;
import model.LingeDeCommande;
import model.Panier;
import model.Pizza;
import model.User;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import dao.ChefDao;
import dao.CommandeDao;
import dao.LigneCommandeDao;
import dao.PizzaDao;
import dao.UserDAO;

/**
 * Servlet implementation class CommandeController
 */
public class CommandeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommandeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
   
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           
        	 HttpSession session = request.getSession();
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if (loggedInUser == null )  {
                String message = "Vous n'avez pas de compte. Veuillez vous connecter pour accéder à vos commandes.";
                request.setAttribute("message", message);
                request.getRequestDispatcher("Login.jsp").forward(request, response);
                return;
            }
            CommandeDao commandeDao = new CommandeDao();
            UserDAO userdao = new UserDAO();
          
            	 List<Commande> command = commandeDao.getCommandesByUserId(loggedInUser.getId());   
            	 request.setAttribute("commandes", command);
            	 request.getRequestDispatcher("Commande.jsp").forward(request, response);
                 return;
            
            
            
            	
            }


        
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession();
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if (loggedInUser == null) {
                response.sendRedirect("Login.jsp");
                return;
            }

            CommandeDao commandeDao = new CommandeDao();
            ChefDao chefDao = new ChefDao(); 

            // Créez une nouvelle commande
            Commande nouvelleCommande = new Commande();
            nouvelleCommande.setUser(loggedInUser);
            nouvelleCommande.setSituation("en attente");
            nouvelleCommande.setDate(new Date());
            double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
            nouvelleCommande.setprix(totalPrice);

            // Enregistrez la nouvelle commande dans la base de données
            if (commandeDao.create(nouvelleCommande)) {
                // Si la commande est créée avec succès, créez également un enregistrement dans Chef
                Chef newChefEntry = new Chef();
                newChefEntry.setCommande(nouvelleCommande);
                newChefEntry.setUser(loggedInUser); // Ou toute autre logique pour déterminer le chef
                newChefEntry.setSituation("en attente"); // Initialement en attente
                newChefEntry.setDateConfirme(new Date()); // Date de confirmation

                if (!chefDao.create(newChefEntry)) {
                    // Gérer l'échec de la création de l'entrée du chef
                    System.err.println("Failed to create chef entry for the order.");
                }
            } else {
                // Gérer l'échec de la création de la commande
                System.err.println("Failed to create order.");
            }

            response.sendRedirect("LigneController");
        }

    	}


