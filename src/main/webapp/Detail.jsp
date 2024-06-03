<%@page import="model.User"%>
<%@page import="model.Pizza"%>
<%@page import="java.util.List"%>
<%@ include file="layout.jsp" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Détails de la Pizza</title>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
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
               Bienvenue, <%= (loggedInUser).getNom() %>
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
    // Récupération de l'ID de la pizza depuis l'URL
    String idParam = request.getParameter("id");
    Long id = null;
    if (idParam != null && !idParam.isEmpty()) {
        id = Long.parseLong(idParam);
    }

    // Récupération de la pizza correspondante depuis la base de données
    Pizza pizza = (Pizza) request.getAttribute("Pizza");

    // Vérification si la pizza existe
    if (pizza != null) {
        // Affichage de l'image
        String imagePath = request.getContextPath() + "/" + pizza.getImage();
%>
        <div class="row justify-content-center align-items-center">
            <div class="col-md-6">
                <div class="card">
                    <img src="<%= imagePath %>" alt="<%= pizza.getNom() %>" class="card-img-top pizza-image">
                    <div class="card-body">
                        <h2 class="card-title"><%= pizza.getNom() %></h2>
                       
                        <form action="PanierController" method="post" id="pizzaForm">
                            <label for="size">Taille:</label>
                            <select id="size" name="size" class="form-control" onchange="updatePrice()">
                                <option value="0" selected>Petite (8cm)</option>
                                <option value="6">Moyenne (14cm) +6DT</option>
                                <option value="10">Grande (20cm) +10DT</option>
                            </select>
                            <br>

                            <div class="form-group">
                                <label for="quantity">Quantité:</label>
                                <div class="input-group">
                                    <input type="number" id="quantity" name="quantity" value="1" class="form-control quantity-input" min="1" onchange="updatePrice()">
                                </div>
                            </div>
                            <br>

                            <script>
                                function updatePrice() {
                                    var sizeValue = parseFloat(document.getElementById("size").value);
                                    var quantity = parseInt(document.getElementById("quantity").value);
                                    var basePrice = <%= pizza.getPrix() %>;
                                    var totalPrice = (basePrice + sizeValue) * quantity;
                                    document.getElementById("prixTotal").innerHTML = "Prix total : " + totalPrice.toFixed() + " DT";
                                }
                            </script>

                            <input type="hidden" name="pizzaId" value="<%= pizza.getId() %>">
                            <p id="prixTotal" class="prix-total-container">Prix total : <%= pizza.getPrix() %> DT</p>
                            <input type="hidden" name="pizzaId" value="<%= pizza.getId() %>">
                            <input type="hidden" name="create" value="true">

                            <div class="button-group">
                                <button type="submit" class="btn-custom">Ajouter au panier</button>
                                <button type="button" onclick="window.location.href='PizzaController'" class="btn-custom">Annuler</button>
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
