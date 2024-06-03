<%@page import="model.User"%>
<%@page import="model.Panier"%>
<%@ page import="dao.PizzaDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Pizza" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.io.File" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.Base64.Encoder" %>
<%@ page import="java.util.Base64.Decoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.util.Objects" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="java.nio.file.StandardCopyOption" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.awt.image.BufferedImage" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Détails de la Pizza</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
<style>
    body, html {
        height: 100%;
        margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: #f8f8f8;
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

    .card {
        background-color: #fff;
        border: none;
        border-radius: 8px;
        overflow: hidden;
        margin-bottom: 20px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    }

    .pizza-image {
        height: 300px;
        object-fit: cover;
    }

    .card-body {
        padding: 15px;
    }

    .card-footer {
        background-color: #6c757d;
        color: white;
        text-align: center;
    }

    .btn-custom {
        background-color: #6c757d;
        color: #fff;
        border: none;
        font-size: 1.5rem;
        padding: 15px 40px;
        border-radius: 5px;
        transition: background-color 0.3s ease, border-color 0.3s ease;
    }

    .btn-custom:hover {
        background-color: #4d4d4d;
        border-color: #4d4d4d;
    }

    .quantity-input {
        width: 60px;
    }

    .prix-total-container {
        text-align: center;
        font-size: 1.5rem;
        color: #6c757d;
        font-weight: bold;
        border: 2px solid #6c757d;
        padding: 10px;
        border-radius: 5px;
        margin-top: 20px;
    }

    .btn-container {
        text-align: right;
        margin-top: 10px;
    }

    .btn-container button {
        width: 100px;
    }

    .button-group {
        display: flex;
        justify-content: center;
        gap: 20px;
        margin-top: 20px;
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
                Bienvenue,<%= (loggedInUser).getNom() %>
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

<div class="container mt-4">
<%
    Panier panier = (Panier) request.getAttribute("Panier"); // Récupérer l'objet Panier depuis les attributs de la requête
    if (panier != null) {
        PizzaDao pizzaDao = new PizzaDao();
        // Récupérer les attributs du panier
        int quantite = panier.getQuantite();
        Pizza pizza = pizzaDao.findById(panier.getPizzaId());
        double prixTotal = panier.getprix();
        double taille = (prixTotal / quantite) - pizza.getPrix(); // Calculer la taille sélectionnée

        // Autres attributs de la pizza
        String nomPizza = pizza.getNom();
        String imagePath = request.getContextPath() + "/" + pizza.getImage();
%>
        <div class="row justify-content-center align-items-center">
            <div class="col-md-6">
                <div class="card">
                    <img src="<%= imagePath %>" alt="<%= pizza.getNom() %>" class="card-img-top pizza-image">
                    <div class="card-body">
                        <h2 class="card-title"><%= pizza.getNom() %></h2>
                       
                        <!-- Formulaire pour choisir la taille de la pizza -->
                        <form action="PanierController" method="post" id="pizzaForm">
                            <input type="hidden" name="Id" value="<%= panier.getId() %>">

                            <div class="form-group">
                                <label for="size">Taille:</label>
                                <select id="size" name="size" class="form-control" onchange="updatePrice()">
                                    <option value="0" <%= taille == 0 ? "selected" : "" %>>Petite (8cm)</option>
                                    <option value="6" <%= taille == 6 ? "selected" : "" %>>Moyenne (14cm) +6DT</option>
                                    <option value="10" <%= taille == 10 ? "selected" : "" %>>Grande (20cm) +10DT</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="Quantité">Quantité:</label>
                                <div class="input-group">
                                    <input type="number" id="quantity" name="quantity" value="<%= quantite %>" class="form-control quantity-input" onchange="updatePrice()" >
                                </div>
                            </div>

                            <script>
                                function updatePrice() {
                                    var sizeValue = parseFloat(document.getElementById("size").value);
                                    var quantity = parseInt(document.getElementById("quantity").value);
                                    var prixPizza = <%= pizza.getPrix() %>;
                                    var prixTotal = (prixPizza + sizeValue) * quantity;
                                    document.getElementById("prixTotal").innerHTML = "Prix total : " + prixTotal.toFixed(2) + " DT";
                                }
                            </script>

                            <input type="hidden" name="pizzaId" value="<%= pizza.getId() %>">
                            <p id="prixTotal" class="prix-total-container">Prix total : <strong><%= panier.getprix() %> DT</strong></p>

                            <div class="button-group">
                                <button type="submit" class="btn-custom">Modifier</button>
                                <button type="button" onclick="window.location.href='Pannier.jsp'" class="btn-custom">Annuler</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
<%
    } else {
%>
        <div class="row">
            <div class="col-12 text-center">
                <p>La pizza demandée n'existe pas.</p>
            </div>
        </div>
<%
    }
%>
</div>
</body>
</html>
