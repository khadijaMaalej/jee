package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import dao.UserDAO;
import java.io.IOException;
import java.util.List;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO pdao;

    public UserController() {
        super();
        this.pdao = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> results = pdao.findAll();
            request.setAttribute("listPersonne", results);
        } catch (Exception e) {
            String message = "Erreur lors de la récupération des utilisateurs: " + e.getMessage();
            request.setAttribute("message", message);
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/user/ListPersonne.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String createParam = request.getParameter("create");

        if ("true".equals(createParam)) {
            handleCreateUser(request, response);
        } else {
            handleLogin(request, response);
        }
    }

    private void handleCreateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String adresse = request.getParameter("adresse");
        String pass = request.getParameter("pass");
        String telephoneStr = request.getParameter("telephone");
        int telephone = 0;

        try {
            telephone = Integer.parseInt(telephoneStr);
            if (!nom.isEmpty() && !adresse.isEmpty() && !pass.isEmpty()) {
                if (pdao.checkNomExistence(nom)) {
                    request.setAttribute("message", "Ce nom existe déjà");
                } else {
                    User newUser = new User(nom, adresse, telephone, pass);
                    boolean created = pdao.create(newUser);
                    request.setAttribute("message", created ? "Utilisateur créé avec succès" : "Erreur lors de la création de l'utilisateur");
                }
            } else {
                request.setAttribute("message", "Veuillez remplir tous les champs requis");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Erreur de format du numéro de téléphone.");
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Login.jsp");
        rd.forward(request, response);
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String pass = request.getParameter("pass");

        if ("chef".equals(nom) && "chef".equals(pass)) {
            response.sendRedirect("ChefController");
        } else if ("livreur".equals(nom) && "livreur".equals(pass)) {
            response.sendRedirect("LivreurController");
        } else {
            User user = pdao.getUserByNom(nom);
            if (user != null && user.getPass().equals(pass)) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUser", user);
                response.sendRedirect("PizzaController");
            } else {
                request.setAttribute("message", "Nom d'utilisateur ou mot de passe incorrect.");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            }
        }
    }
}
