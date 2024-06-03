<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Se Connecter</title>
<style>
    body, html {
        height: 100%;
        margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: url('images/login.jpg') no-repeat center center fixed; /* Assurez-vous que le chemin est correct */
        background-size: cover;
    }

    .bg-image {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100%;
    }

    .form-container {
        background: rgba(255, 255, 255, 0);
        padding: 20px;
        border-radius: 30px;
        box-shadow: none;
        width: 300px;
        text-align: center;
    }

    .form-container h2 {
        color: #ffffff;
        margin-bottom: 20px;
        font-weight: bold; /* En gras pour le titre */
    }

    input[type="text"],
    input[type="password"] {
        width: 100%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 25px;
        box-sizing: border-box;
        transition: 0.3s;
        background-color: #f8f8f8; /* Light grey background */
    }

    input[type="text"]:focus,
    input[type="password"]:focus {
        background-color: #e8e8e8; /* Slightly darker grey */
    }

    input[type="submit"] {
        width: 100%;
        background-color: #333333; /* Dark grey */
        color: white;
        padding: 14px 20px;
        border: none;
        border-radius: 25px;
        cursor: pointer;
        transition: background-color 0.3s; /* Smooth transition for hover effect */
        font-weight: bold; /* En gras pour le bouton */
    }

    input[type="submit"]:hover {
        background-color: #1a1a1a; /* Very dark grey for hover */
    }

    .signup-link {
        display: block;
        margin-top: 20px;
        color: #ffffff;
        text-decoration: none;
        font-weight: bold; /* En gras pour le lien d'inscription */
    }

    .signup-link:hover {
        text-decoration: underline;
    }
</style>
</head>
<body>

<div class="bg-image">
    <div class="form-container">
        <h2>Se Connecter</h2>
        <form action="UserController" method="post">
            <input type="text" name="nom" placeholder="khadija">
            <input type="password" name="pass" placeholder="******">
            <input type="submit" value="Ce connecter">
            <a href="Inscription.jsp" class="signup-link">S'inscrire</a>
        </form>
    </div>
</div>

</body>
</html>
