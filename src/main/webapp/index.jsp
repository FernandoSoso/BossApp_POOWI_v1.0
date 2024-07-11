<%--
  Created by IntelliJ IDEA.
  User: ferna
  Date: 10/06/2024
  Time: 00:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Pagina de Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="./css/root.css">
    <link rel="stylesheet" href="./css/global.css">
    <link rel="stylesheet" href="./css/inputs.css">
    <link rel="stylesheet" href="./css/buttons.css">
    <link rel="stylesheet" href="./css/user-page.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body id="user-body">

<div class="user-container">
    <div class="user-image">
        <%--            <img src="img/user.png" alt="Imagem de login">--%>
    </div>

    <form action="login" method="post" class="user-login">
        <div class="user-titulo">
            <h1>Acessar</h1>
        </div>
        <div class="container-inputs">
            <div class="input-container">
                <input type="text" name="email" id="email" class="input-text" value="" required>
                <label for="email" class="input-label">Email</label>
            </div>

            <div class="input-container">
                <input type="password" name="senha" id="senha" class="input-text" minlength="8" value="" required>
                <label for="senha" class="input-label">Senha</label>
            </div>
        </div>
        <button type="submit" class="persistButton">Entrar</button>
    </form>
</div>
</body>

<script src="./js/input.js"></script>

<script>

    document.addEventListener('DOMContentLoaded', function() {
        const erro = new URLSearchParams(window.location.search).get('erro');
        if (erro) {
            alert('Email ou senha inválidos');
        }
    });
</script>

</html>
