<%@ page import="model.User"%>
<%@ page import="dao.PizzaDao" %>
<%@ page import="model.Pizza" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Panier" %>
<%@ page import="dao.PanierDao" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Votre Panier</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f0f0f0;
            color: #333;
        }

        .navbar {
            background-color: #333333;
            border-bottom: 3px solid #4d4d4d;
        }

        .navbar .navbar-text, .navbar a {
            color: #fff;
        }

        .navbar a:hover {
            background-color: #4d4d4d;
        }

        .container h1 {
            color: #6c757d;
            margin-top: 30px;
            text-align: center;
            font-size: 2rem;
            font-weight: bold;
        }

        .table-responsive {
            margin-top: 20px;
        }

        .table th, .table td {
            vertical-align: middle;
        }

        .table-img {
            max-width: 100px;
            height: auto;
        }

        .action {
            font-size: 1.5rem;
            color: #6c757d;
            transition: color 0.3s ease;
        }

        .action:hover {
            color: #4d4d4d;
        }

        .total-price-container {
            text-align: right;
            font-size: 1.5rem;
            color: #6c757d;
            font-weight: bold;
            padding: 10px;
        }

        .btn-custom, .checkout-btn {
            font-size: 1.5rem;
            padding: 15px 40px;
            border-radius: 25px;
            transition: background-color 0.3s ease, border-color 0.3s ease;
            color: #fff;
            width: 300px;
            background-color: #6c757d;
            border-color: #6c757d;
        }

        .btn-custom:hover, .checkout-btn:hover {
            background-color: #4d4d4d;
            border-color: #4d4d4d;
        }

        .btn-custom:focus, .btn-custom:active,
        .checkout-btn:focus, .checkout-btn:active {
            background-color: #6c757d;
            border-color: #6c757d;
            color: #fff;
        }

        .button-group {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 20px;
        }

        .signup-link {
            display: block;
            margin-top: 20px;
            color: #ffffff;
            text-decoration: none;
            font-weight: bold;
        }

        .signup-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a href="Pannier.jsp" class="nav-link">
            <i class="fas fa-shopping-cart"></i>
        </a>

        <% 
            User loggedInUser = (User)session.getAttribute("loggedInUser");
            if (loggedInUser != null) {
        %>
            <span class="navbar-text">
              Bienvenue,  <%= (loggedInUser).getNom() %>
            </span>
            <div class="ml-auto">
                <a class="btn btn-secondary" href="CommandeController" style="background-color: #6c757d;">
                    <span style="color: white;">Liste de Commandes</span>
                </a>
            </div>
            <div class="ml-auto">
                <form action="LogOutController" method="post">
                    <button type="submit" class="btn btn-warning" style="color: white; background-color: #6c757d; border-color: grey;">
                        Deconnexion
                    </button>
                </form>
            </div>
        <% } else { %>
            <div class="ml-auto">
                <a class="btn btn-secondary dropdown-toggle" href="Login.jsp" style="background-color: #6c757d;">
                    <span style="color: white;">Se connecter</span>
                </a>
            </div>
        <% } %>
    </div>
</nav>

<%
    List<Panier> paniers = (List<Panier>) session.getAttribute("paniers");
    PizzaDao pizzaDao = new PizzaDao();
    double totalPrice = 0;

    if (paniers != null && !paniers.isEmpty()) {
%>
<div class="container mt-5">
    <h1>Votre Panier</h1>
    <div class="table-responsive">
        <table class="table table-bordered table-hover">
            <thead class="thead-light">
                <tr>
                    <th>Image</th>
                    <th>Nom</th>
                    <th>Quantité</th>
                    <th>Prix</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (Panier panier : paniers) { %>
                <tr>
                    <td>
                        <%
                            Pizza pizza = pizzaDao.findById(panier.getPizzaId());
                            String imagePath = request.getContextPath() + "/" + pizza.getImage();
                        %>
                        <img src="<%= imagePath %>" class="table-img" alt="<%= pizza.getNom() %>">
                    </td>
                    <td><%= pizza.getNom() %></td>
                    <td><%= panier.getQuantite() %></td>
                    <td><%= panier.getprix() %> DT</td>
                    <td>
                        <a href='PanierController?id=<%= panier.getId()%>&quantity=<%= panier.getQuantite() %>' class="action btn-edit" name="editButton"><i class="fas fa-edit"></i></a>
                        <a href='PanierController?id=<%= panier.getId()%>' class="action"><i class="fas fa-trash-alt"></i></a>
                    </td>
                </tr>
                <% 
                    totalPrice += panier.getprix();
                } %>
                <tr>
                    <td colspan="4" class="text-right">
                        <div class="total-price-container">
                            Prix total : <%= totalPrice %> DT
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
<%
    } else {
%>
<div class="container mt-5">
    <h1>Votre Panier</h1>
    <p>Aucun produit dans le panier pour le moment.</p>
</div>
<%
    }
%>

<div class="container text-center mt-3">
    <div class="button-group">
        <a href="PizzaController" class="btn btn-custom">Ajouter Pizza</a>
        <form action="CommandeController" method="post" id="commandform" class="mb-0">
            <input type="hidden" name="totalPrice" value="<%= totalPrice %>">
            <button type="submit" class="btn btn-primary checkout-btn">Passer commande</button>
        </form>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
