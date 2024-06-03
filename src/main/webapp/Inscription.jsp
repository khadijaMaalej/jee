<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Inscription</title>
<style>
    body, html {
        height: 100%;
        margin: 0;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: url('images/login.jpg') no-repeat center center fixed;
        background-size: cover;
    }

    .bg-image {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100%;
    }

    .form-container {
        padding: 20px;
        width: 300px;
        border-radius: 8px;
        background: rgba(0, 0, 0, 0);
        text-align: center;
        color: #fff;
    }

    .form-container h2 {
        margin-bottom: 20px;
        font-weight: bold;
    }

    input[type="text"], input[type="password"], input[type="number"], input[type="submit"] {
        width: 100%;
        padding: 12px;
        margin-bottom: 8px;
        border: 1px solid #ccc;
        border-radius: 20px;
        box-sizing: border-box;
        background-color: #f8f8f8;
        color: #000;
        font-weight: bold;
    }

    input[type="submit"] {
        background-color: #333;
        color: white;
        cursor: pointer;
    }

    input[type="submit"]:hover {
        background-color: #555;
    }

    .link, .link a {
        font-weight: bold;
        color: #ccc;
        text-decoration: none;
    }

    .link a:hover {
        text-decoration: underline;
        color: #fff;
    }

    .error-message {
        color: #ff6a6a;
        font-size: 14px;
        margin-top: 10px;
        font-weight: bold;
    }
</style>
</head>
<body>

<div class="bg-image">
    <div class="form-container">
        <h2>Créer Un Compte</h2>
        <form action="UserController" method="post">
            <input type="hidden" name="create" value="true">
            <input type="text" name="nom" placeholder="Nom" required>
            <input type="password" name="pass" placeholder="Mot de passe" required>
            <input type="text" name="adresse" placeholder="Adresse" required>
            <input type="number" name="telephone" placeholder="Téléphone" required>
            <input type="submit" value="S'inscrire">
        </form>
        <div class="link">
            <a href="Login.jsp">J'ai un compte existant</a>
        </div>
        <div class="error-message">${requestScope.message}</div>
    </div>
</div>

</body>
</html>
