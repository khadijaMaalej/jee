<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Chefs et leurs Commandes</title>
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

        

        .order-details {
            margin-bottom: 10px;
        }

        .order-details strong {
            color: #6c757d;
        }

        .order-details ul {
            padding-left: 20px;
        }

        .order-details li {
            margin-bottom: 5px;
        }

        .btn-custom {
            background-color: #6c757d;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s ease-in-out;
        }

        .btn-custom:hover {
            background-color: #4d4d4d;
        }

        .filter-select {
            width: 200px;
            margin: 0 auto 30px auto;
        }

        .table th, .table td {
            vertical-align: middle;
        }

        .thead-dark th {
            background-color: #343a40;
            color: #fff;
        }

        .action-buttons a {
            display: inline-block;
            margin-right: 10px;
            color: #fff;
            text-decoration: none;
            transition: transform 0.2s ease-in-out;
        }

        .action-buttons a:hover {
            transform: translateY(-3px);
        }

        .btn-lancer {
            background-color: #6c757d;
            padding: 10px;
            border-radius: 5px;
        }

        .btn-finaliser {
            background-color: #28a745;
            padding: 10px;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="#">Commande Chef</a>
        <div class="ml-auto">
            <form action="LogOutController" method="post">
                <button type="submit" class="btn btn-warning" style="color: white; background-color: #6c757d; border-color: grey;">
                    Déconnexion
                </button>
            </form>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <h1>Liste des Commandes pour cuisinier</h1>
    
    <form action="chefController" method="get" class="filter-select">
        <select name="filter" class="form-control" onchange="this.form.submit()">
            <option value="tout" ${currentFilter == 'tout' ? 'selected' : ''}>Tout</option>
            <option value="en attente" ${currentFilter == 'en attente' ? 'selected' : ''}>En attente</option>
            <option value="en cours" ${currentFilter == 'en cours' ? 'selected' : ''}>En cours</option>
            <option value="prête" ${currentFilter == 'prête' ? 'selected' : ''}>Prête</option>
        </select>
    </form>
    
    <table class="table table-bordered">
        <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Nom de l'utilisateur</th>
                <th>Situation</th>
                
                <th>Détails de la Commande</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="chef" items="${chefs}">
                <tr>
                    <td>${chef.id}</td>
                    <td>${chef.user.getNom()}</td>
                    <td>${chef.situation}</td>
                    
                    <td>
                        <div class="order-details">
                            <strong>Date :</strong> <fmt:formatDate value="${chef.commande.date}" pattern="yyyy-MM-dd HH:mm:ss"/><br/>
                            <strong>Total Prix :</strong> ${chef.commande.prix} DT<br/>
                            <strong>Pizza :</strong>
                            <ul>
                                <c:forEach var="item" items="${chef.commande.ligneCommandes}">
                                    <li>${item.quantite} x ${item.pizza.getNom()} - 
                                        <c:choose>
                                            <c:when test="${item.size == 0}">
                                                Petite
                                            </c:when>
                                            <c:when test="${item.size == 6}">
                                                Moyenne
                                            </c:when>
                                            <c:when test="${item.size == 10}">
                                                Grande
                                            </c:when>
                                        </c:choose>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </td>
                    <td class="action-buttons">
                        <c:choose>
                            <c:when test="${chef.situation eq 'en attente'}">
                                <a href="ChefController?action=start&commandeId=${chef.commande.id}" class="btn-lancer" title="Lancer la commande">
                                    <i class="fas fa-play"></i>
                                </a>
                            </c:when>
                            <c:when test="${chef.situation eq 'en cours'}">
                                <a href="ChefController?action=finalize&commandeId=${chef.commande.id}" class="btn-finaliser" title="Finaliser commande">
                                    <i class="fas fa-check"></i>
                                </a>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
