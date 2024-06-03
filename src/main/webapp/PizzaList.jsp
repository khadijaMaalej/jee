<%@page import="model.User"%>
<%@page import="model.Pizza"%>
<%@page import="java.util.List"%>
<%@ include file="layout.jsp" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Liste des Pizzas</title>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<style>
    body, html {
        height: 100%;
        margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: #f0f0f0;
        color: #333;
    }

    .navbar {
        background-color: #343a40;
        border-bottom: 3px solid #4d4d4d;
    }

    .navbar .navbar-text, .navbar a {
        color: #fff;
    }

    .navbar a:hover {
        background-color: #495057;
    }

    .container h1 {
        color: #6c757d;
        margin-top: 30px;
        text-align: center;
        font-size: 2rem;
        font-weight: bold;
    }

    .card {
        background-color: #fff;
        border: none;
        border-radius: 10px;
        overflow: hidden;
        margin-bottom: 20px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
    }

    .card:hover {
        transform: translateY(-10px);
        box-shadow: 0 16px 32px rgba(0, 0, 0, 0.2);
    }

    .pizza-image {
        height: 250px;
        object-fit: cover;
    }

    .card-body {
        padding: 20px;
        text-align: center;
    }

    .card-title {
        font-size: 1.5rem;
        font-weight: bold;
        margin-bottom: 10px;
        color: #333;
    }

    .card-text {
        font-size: 1rem;
        margin-bottom: 15px;
        color: #555;
    }

    .card-footer {
        background-color: #6c757d;
        padding: 0; /* Remove padding to ensure button takes full width */
    }

    .btn-custom {
        background-color: #6c757d;
        color: #fff;
        border: none;
        border-radius: 0; /* Remove border radius for full-width button */
        width: 100%; /* Make button take full width */
        padding: 10px 20px;
        transition: background-color 0.3s ease-in-out;
        font-size: 1rem;
    }

    .btn-custom:hover {
        background-color: #4d4d4d;
    }

    .text-center p {
        font-size: 18px;
        margin-top: 20px;
    }

    .ml-auto {
        margin-left: auto !important;
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
<div class="container mt-4">
    <h1>Liste des Pizzas</h1>
    <div class="row">
        <% 
        List<Pizza> pizzas = (List<Pizza>) request.getAttribute("listPizza");
        if (pizzas != null && !pizzas.isEmpty()) {
            for (Pizza pizza : pizzas) { 
        %>
            <div class="col-sm-6 col-md-4 mb-3">
                <div class="card pizza-card h-100">
                    <img src="<%= request.getContextPath() + "/" + pizza.getImage() %>" class="card-img-top pizza-image" alt="<%= pizza.getNom() %>">
                    <div class="card-body">
                        <h5 class="card-title"><%= pizza.getNom() %></h5>
                        <p class="card-text"><%= pizza.getDescription() %></p>
                        <p class="card-text"><strong>Prix:</strong> <%= pizza.getPrix() %> DT</p>
                    </div>
                    <div class="card-footer">
                        <a href="PizzaController?id=<%= pizza.getId() %>" class="btn btn-custom btn-block">Commander</a>
                    </div>
                </div>
            </div>
        <% 
            }
        } else {
        %>
            <div class="col-12 text-center">
                <p>Aucune pizza disponible.</p>
            </div>
        <% 
        }
        %>
    </div>
</div>
</body>
</html>
