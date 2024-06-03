<%@page import="model.User"%>
<%@page import="model.LingeDeCommande"%>
<%@ page import="model.Commande" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes Commandes</title>
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

        .order-card {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
            padding: 20px;
            transition: transform 0.2s ease-in-out;
        }

        .order-card:hover {
            transform: translateY(-5px);
        }

        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #ddd;
            padding-bottom: 10px;
            margin-bottom: 15px;
        }

        .order-header h5 {
            margin: 0;
            font-size: 1.5rem;
            color: #6c757d;
        }

        .order-body {
            padding: 10px 0;
        }

        .order-details p {
            margin: 0;
            padding: 5px 0;
        }

        .order-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px solid #ddd;
        }

        .order-footer {
            text-align: right;
            font-size: 1.25rem;
            font-weight: bold;
            color: #333;
            padding-top: 10px;
            border-top: 1px solid #ddd;
        }

        .btn-custom, .print-btn {
            background-color: #6c757d;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s ease-in-out;
            cursor: pointer;
        }

        .btn-custom:hover, .print-btn:hover {
            background-color: #4d4d4d;
        }

        .fa-check-circle {
            color: #28a745;
        }

        .fa-times-circle {
            color: #dc3545;
        }

        .order-header i {
            font-size: 1.5rem;
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
                <a class="btn btn-secondary" href="PizzaController" style="background-color: #6c757d;">
                    <span style="color: white;">Liste des Pizzas</span>
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
<div class="container mt-5">
    <h1>Mes Commandes</h1>
    <hr>
    <% if (request.getAttribute("commandes") != null) {
       List<Commande> commandes = (List<Commande>) request.getAttribute("commandes");
       for (int i = commandes.size() - 1; i >= 0; i--) {
           Commande commande = commandes.get(i);
           double prixTotalCommande = 0;
           if (commande.getLigneCommandes() != null && !commande.getLigneCommandes().isEmpty()) {
               for (LingeDeCommande ligneDeCommande : commande.getLigneCommandes()) {
                   prixTotalCommande += ligneDeCommande.getPrix();
               }
           }
    %>
    <div class="order-card" id="order-<%= commande.getId() %>">
        <div class="order-header">
            <h5>Commande n° <%= commande.getId() %></h5>
            <i class="fas <%= commande.getSituation().equals("livrée") ? "fa-check-circle" : "fa-times-circle" %>"></i>
        </div>
        <div class="order-body">
            <div class="order-details">
                <p><strong>Date de confirmation :</strong> <%= commande.getDate() %></p>
                <p><strong>État :</strong> <%= commande.getSituation() %></p>
            </div>
            <h6>Liste des pizzas commandées :</h6>
            <% if (commande.getLigneCommandes() != null && !commande.getLigneCommandes().isEmpty()) {
                   List<LingeDeCommande> lignesDeCommande = commande.getLigneCommandes();
                   for (LingeDeCommande ligneDeCommande : lignesDeCommande) { %>
                       <div class="order-item">
                           <div>
                               <%= ligneDeCommande.getQuantite() %> pièce(s) de : <%= ligneDeCommande.getPizza().getNom() %>
                           </div>
                           <div>
                               <%= ligneDeCommande.getPrix() %> DT
                           </div>
                       </div>
                   <% }
               } else { %>
                   <p>Aucune ligne de commande pour cette commande.</p>
               <% } %>
        </div>
        <div class="order-footer">
            Prix total : <%= prixTotalCommande %> DT
        </div>
        <div class="text-right mt-3">
            <button class="print-btn" onclick="printOrder(<%= commande.getId() %>)">Imprimer</button>
        </div>
    </div>
    <% }
       } else { %>
           <p>Aucune commande trouvée.</p>
    <% } %>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    function printOrder(orderId) {
        var orderElement = document.getElementById('order-' + orderId);
        var printWindow = window.open('', '_blank');
        printWindow.document.write('<html><head><title>Imprimer la Commande</title>');
        printWindow.document.write('<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">');
        printWindow.document.write('<style>.order-card {background-color: #fff; border: 1px solid #ddd; border-radius: 10px; padding: 20px; margin-bottom: 20px;} .order-header, .order-footer {border-bottom: 1px solid #ddd; padding-bottom: 10px; margin-bottom: 10px;} .order-item {display: flex; justify-content: space-between; align-items: center; padding: 10px 0; border-bottom: 1px solid #ddd;}</style>');
        printWindow.document.write('</head><body>');
        printWindow.document.write(orderElement.outerHTML);
        printWindow.document.write('</body></html>');
        printWindow.document.close();
        printWindow.print();
    }
</script>
</body>
</html>
