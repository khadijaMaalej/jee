<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Commandes pour Livrer</title>
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

        .table th, .table td {
            vertical-align: middle;
        }

        .thead-dark th {
            background-color: #343a40;
            color: #fff;
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

        .btn-icon {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            color: #fff;
            font-size: 1.2rem;
            transition: background-color 0.3s ease-in-out;
        }

        .btn-icon-primary {
            background-color: #007bff;
        }

        .btn-icon-primary:hover {
            background-color: #0056b3;
        }

        .btn-icon-success {
            background-color: #28a745;
        }

        .btn-icon-success:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="#">Livraisons</a>
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
        <h1>Liste des Commandes pour Livrer</h1>
        <table class="table table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Nom de l'utilisateur</th>
                    <th>Commande ID</th>
                    <th>Prix</th>
                    <th>Téléphone</th>
                    <th>Situation</th>
                    <th>Adresse</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="livreur" items="${livreurs}">
                    <tr>
                        <td>${livreur.id}</td>
                        <td>${livreur.user.nom}</td>
                        <td>${livreur.commande.id}</td>
                        <td>${livreur.commande.prix}</td>
                        <td>${livreur.user.telephone}</td>
                        <td>${livreur.situation}</td>
                        <td>${livreur.user.adresse}</td>
                        <td class="text-center">
                            <c:if test="${livreur.situation eq 'prête'}">
                                <form action="LivreurController" method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="startDelivery" />
                                    <input type="hidden" name="livreurId" value="${livreur.id}" />
                                    <button type="submit" class="btn-icon btn-icon-primary" title="Lancer la livraison">
                                        <i class="fas fa-play"></i>
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${livreur.situation eq 'en cours de livraison'}">
                                <form action="LivreurController" method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="finalizeDelivery" />
                                    <input type="hidden" name="livreurId" value="${livreur.id}" />
                                    <button type="submit" class="btn-icon btn-icon-success" title="Finaliser la livraison">
                                        <i class="fas fa-check"></i>
                                    </button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
