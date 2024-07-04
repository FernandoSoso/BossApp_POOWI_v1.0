<%--
  Created by IntelliJ IDEA.
  User: ferna
  Date: 10/06/2024
  Time: 01:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Motorista</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/root.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/global.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/table-page.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/modal.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/sidebar.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/inputs.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/buttons.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body id="page-body">

<jsp:include page="../components/sidebar.jsp"/>
<jsp:include page="../components/table.jsp"/>

<!-- Modal de cadastro-->
<div class="modal fade bd-example-modal-lg" id="cadastrarModal" tabindex="-1" role="dialog" aria-labelledby="titulo-modal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="header-modal">
                <h5 class="titulo-modal" id="titulo-modal">Cadastrar motorista</h5>
                <button type="button" class="fechar-modal" data-dismiss="modal" aria-label="Close">
                    <svg aria-hidden="true" width="35" height="36" viewBox="0 0 35 36" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M6.67196 7.17184C7.08218 6.76175 7.63848 6.53137 8.21853 6.53137C8.79857 6.53137 9.35487 6.76175 9.76509 7.17184L17.5001 14.9068L25.2351 7.17184C25.6477 6.77337 26.2002 6.55288 26.7738 6.55787C27.3473 6.56285 27.896 6.79291 28.3016 7.19849C28.7071 7.60407 28.9372 8.15272 28.9422 8.72628C28.9472 9.29983 28.7267 9.8524 28.3282 10.265L20.5932 18L28.3282 25.735C28.7267 26.1475 28.9472 26.7001 28.9422 27.2737C28.9372 27.8472 28.7071 28.3959 28.3016 28.8014C27.896 29.207 27.3473 29.4371 26.7738 29.4421C26.2002 29.4471 25.6477 29.2266 25.2351 28.8281L17.5001 21.0931L9.76509 28.8281C9.35252 29.2266 8.79995 29.4471 8.2264 29.4421C7.65284 29.4371 7.10419 29.207 6.69861 28.8014C6.29303 28.3959 6.06297 27.8472 6.05799 27.2737C6.05301 26.7001 6.27349 26.1475 6.67196 25.735L14.407 18L6.67196 10.265C6.26187 9.85475 6.03149 9.29845 6.03149 8.7184C6.03149 8.13836 6.26187 7.58206 6.67196 7.17184Z" fill="#1C1C1C" fill-opacity="0.5"></path>
                    </svg>

                </button>
            </div>
            <div class="body-modal">
                <h6 class="aviso-campos-obrigatorios">
                    Campos marcados com * são obrigatórios!
                </h6>
                <form action="motorista" method="post" class="cadastrar-container">
                    <div class="input-container">
                        <input type="hidden" value="insert" name="operacao" id="operacao">

                        <div class="input-text-container">
                            <label for="nome" class="input-text-label">*nome</label>
                            <input type="text" maxlength="50" name="nome" id="nome" class="input-text" required>
                        </div>

                        <div class="input-text-container">
                            <label for="endereco" class="input-text-label">endereço</label>
                            <input type="text" maxlength="100" name="endereco" id="endereco" class="input-text">
                        </div>

                        <div class="input-text-container">
                            <label for="caminhao" class="input-select-label">caminhão</label>
                            <select id="caminhao" name="caminhao" id="caminhao" class="input-select">
                                <option value="-1" selected>Sem caminhao</option>
                                <c:forEach var="caminhao" items='${listaCaminhao}'>
                                    <option value="${caminhao.cod}">${caminhao.placa}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="input-text-container">
                            <label for="telefonePrincipal" class="input-text-label">*telefone principal</label>
                            <input type="text" maxlength="15" name="telefonePrincipal" id="telefonePrincipal" class="input-text" required>
                        </div>

                        <div class="wrapper-input">
                            <div class="input-text-container">
                                <label for="telefoneAlternativo" class="input-text-label">telefone alternativo</label>
                                <input type="text" maxlength="15" name="telefoneAlternativo" id="telefoneAlternativo" class="input-text">
                            </div>
                            <div class="input-text-container">
                                <label for="telefoneAlternativo2" class="input-text-label">telefone alternativo 2</label>
                                <input type="text" maxlength="15" id="telefoneAlternativo2" name="telefoneAlternativo2" class="input-text">
                            </div>
                        </div>

                    </div>
                    <button type="submit" class="cadastrar-button">Cadastrar</button>
                </form>
            </div>
            <div class="footer-modal"></div>
        </div>
    </div>
</div>
</body>

<script src="<%=request.getContextPath()%>/js/table.js"></script>
<script>
    const entidade = 'motorista';
    const tableTitleId = 'page-title';
    const tableHeadId = 'table-header';
    const tableBodyId = 'table-body';
    const incrementId = 'increment';
    const decrementId = 'decrement';
    const cols = [
        "nome",
        "cpf",
        "telefonePrincipal",
        "telefoneAlternativo",
        "telefoneAlternativo2",
        "endereco"
    ];
    const maxItens = 15;

    inicializarTabela(entidade, tableTitleId, tableHeadId, tableBodyId, cols, maxItens);
</script>
</html>
