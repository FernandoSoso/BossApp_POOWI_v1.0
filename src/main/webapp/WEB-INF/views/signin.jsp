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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/root.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/global.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/inputs.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/buttons.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/user-page.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body id="user-body">
<a href="login" class="tradeUser" >
    Acessar
</a>
<div class="user-container">
    <div class="user-image">
        <%--            <img src="img/user.png" alt="Imagem de login">--%>
    </div>

    <form action="signin" method="post" class="user-signin">
        <div class="user-titulo">
            <h1>Cadastrar</h1>
        </div>
        <div class="container-inputs">
            <div class="input-text-container">
                <label for="nome" class="input-text-label">Nome</label>
                <input type="text" name="nome" id="nome" class="input-text" maxlength="100" required>
            </div>

            <div class="input-text-container">
                <label for="email" class="input-text-label">Email</label>
                <input type="text" name="email" id="email" class="input-text" maxlength="50" required>
            </div>

            <div class="input-text-container">
                <label for="senha" class="input-text-label">Senha</label>
                <input type="password" name="senha" id="senha" class="input-text" maxlength="64" required minlength="8"><br>
            </div>

            <div class="input-text-container">
                <label for="permissao" class="input-select-label">permissao</label>
                <select id="permissao" name="permissao" class="input-select" required>
                    <option value="ADMIN" selected>Administrador</option>
                    <option value="USER" >Usuário</option>
                </select>
            </div>
        </div>
        <button type="submit" class="cadastrar-button">Cadastrar</button>
    </form>
</div>
</body>

<script src="<%=request.getContextPath()%>/js/input.js"></script>

</html>
