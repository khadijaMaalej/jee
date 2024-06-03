package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Panier;
import model.Pizza;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dao.PanierDao;
import dao.PizzaDao;

/**
 * Servlet implementation class PanierController
 */
public class PanierController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PanierDao pdao;
    private PizzaDao pizzaDao;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PanierController() {
        super();
        pdao = new PanierDao();
        pizzaDao = new PizzaDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Récupérer la session
        List<Panier> paniers = (List<Panier>) session.getAttribute("paniers"); // Récupérer le tableau de paniers depuis la session
        String editButton = request.getParameter("quantity");

        // Afficher la valeur de editButton dans la console
        System.out.println("Valeur de editButton : " + editButton);

        // Vérifier si le bouton d'édition a été cliqué
        if (editButton != null) {
            String a = request.getParameter("id");
            System.out.println(a);
            if (request.getParameter("id") != null && paniers != null) {
                long id = Long.parseLong(request.getParameter("id").trim()); // Supprimer les espaces autour de la chaîne

                // Parcourir le tableau pour trouver l'élément correspondant à l'ID
                for (Panier panier : paniers) {
                    if (panier.getId() == id) {
                        request.setAttribute("Panier", panier); // Définir l'attribut "Panier" dans la requête avec le panier sélectionné
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Updatepizza.jsp");
                        rd.forward(request, response);
                        return; // Terminer la méthode doGet une fois que la redirection est effectuée
                    }
                }
            }
        } else {
            // Si le bouton de suppression a été cliqué
            if (request.getParameter("id") != null && paniers != null) {
                try {
                    long id = Long.parseLong(request.getParameter("id").trim()); // Supprimer les espaces autour de la chaîne

                    // Parcourir le tableau pour trouver et supprimer l'élément correspondant à l'ID
                    Iterator<Panier> iterator = paniers.iterator();
                    while (iterator.hasNext()) {
                        Panier panier = iterator.next();
                        if (panier.getId() == id) {
                            iterator.remove(); // Supprimer l'élément du tableau
                            break;
                        }
                    }

                    session.setAttribute("paniers", paniers); // Mettre à jour le tableau de paniers dans la session
                } catch (NumberFormatException e) {
                    // Gérer l'exception si l'ID n'est pas un nombre valide
                    e.printStackTrace();
                    // Rediriger vers une page d'erreur
                    response.sendRedirect("erreur.jsp");
                    return; // Terminer la méthode doGet après la redirection vers la page d'erreur
                }
            }

            // Redirection vers la page "panier.jsp"
            response.sendRedirect("Pannier.jsp");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les informations du formulaire
        String createParam = request.getParameter("create");
        if (createParam != null && createParam.equals("true")) {
            // Récupérer les autres informations du formulaire
            String idPizzaParam = request.getParameter("pizzaId");
            String sizeValueParam = request.getParameter("size");
            String message = request.getParameter("message");
            String quantityParam = request.getParameter("quantity");
            String size = request.getParameter("size");

            // Vérifier si les autres paramètres ne sont pas nuls
            if (idPizzaParam != null && sizeValueParam != null && quantityParam != null) {
                try {
                    // Convertir les paramètres en types appropriés
                    Long idPizza = Long.parseLong(idPizzaParam);
                    double sizeValue = Double.parseDouble(sizeValueParam);
                    int quantity = Integer.parseInt(quantityParam);

                    // Calculer le prix total
                    Pizza pizza = pizzaDao.findById(idPizza);
                    double prixPizza = pizza.getPrix();
                    double prixTotal = (prixPizza + sizeValue) * quantity;

                    // Créer une nouvelle instance de Panier
                    Panier panier = new Panier(idPizza, quantity, message, prixTotal, size);

                    // Récupérer la session de la requête
                    HttpSession session = request.getSession();

                    // Récupérer la liste de paniers depuis la session
                    List<Panier> paniers = (List<Panier>) session.getAttribute("paniers");

                    // Vérifier si la liste de paniers existe déjà dans la session
                    if (paniers == null) {
                        // Si la liste n'existe pas, créer une nouvelle liste
                        paniers = new ArrayList<>();
                    }

                    // Ajouter le panier à la liste
                    paniers.add(panier);

                    // Mettre à jour la liste dans la session
                    session.setAttribute("paniers", paniers);

                    // Rediriger vers la page du panier
                    response.sendRedirect("Pannier.jsp");
                } catch (NumberFormatException e) {
                    // Gérer l'exception de conversion
                    e.printStackTrace();
                    // Rediriger vers une page d'erreur
                    response.sendRedirect("erreur.jsp");
                }
            } else {
                // Paramètres manquants, gérer l'erreur
                response.sendRedirect("erreur.jsp");
            }
        } else {
            String idPizzaParam = request.getParameter("pizzaId");
            String sizeValueParam = request.getParameter("size");
            String message = request.getParameter("message");
            String quantityParam = request.getParameter("quantity");
            String size = request.getParameter("size");

            System.out.println("ID du panier: " + idPizzaParam);
            System.out.println("ID : " + sizeValueParam);
            System.out.println("Message: " + message);
            System.out.println("Prix total: " + quantityParam);
            Long idPizza = Long.parseLong(idPizzaParam);
            double sizeValue = Double.parseDouble(sizeValueParam);
            int quantity = Integer.parseInt(quantityParam);

            // Calculer le prix total
            Pizza pizza = pizzaDao.findById(idPizza);
            double prixPizza = pizza.getPrix();
            double prixTotal = (prixPizza + sizeValue) * quantity;

            HttpSession session = request.getSession();

            // Récupérer la liste de paniers depuis la session
            List<Panier> paniers = (List<Panier>) session.getAttribute("paniers");

            // Vérifier si la liste de paniers existe déjà dans la session
            if (paniers != null && !paniers.isEmpty()) {
                // Parcourir la liste des paniers pour trouver celui à mettre à jour
                for (Panier panier : paniers) {
                    String a = request.getParameter("Id");
                    System.out.println(" total: " + a);
                    Long aa = Long.parseLong(a);
                    System.out.println(" total: " + aa);
                    // Comparer l'ID du panier avec celui envoyé depuis le formulaire
                    if (panier.getId() == aa) {
                        // Mettre à jour les valeurs du panier avec les nouvelles valeurs du formulaire
                        panier.setQuantite(quantity);
                        panier.setMessage(message);
                        panier.setprix(prixTotal);

                        // Afficher les valeurs mises à jour dans la console

                        // Sortir de la boucle car le panier a été trouvé et mis à jour
                        break;
                    }
                }
                // Mettre à jour la liste de paniers dans la session avec la nouvelle liste mise à jour
                session.setAttribute("paniers", paniers);

                // Rediriger vers la page du panier ou une autre page appropriée
                response.sendRedirect("Pannier.jsp");
            } else {
                // Si la liste de paniers est vide ou n'existe pas, vous pouvez gérer cela en conséquence
                response.sendRedirect("erreur.jsp");
            }
        }
    }
}
