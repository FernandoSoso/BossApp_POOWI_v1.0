<%--
  Created by IntelliJ IDEA.
  User: ferna
  Date: 10/06/2024
  Time: 01:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Frete</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/root.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/global.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/table-page.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/modal.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/sidebar.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/inputs.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/buttons.css">

    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body id="page-body">

<jsp:include page="../components/warning-popup.jsp"/>
<jsp:include page="../components/sidebar.jsp"/>
<jsp:include page="../components/table.jsp"/>

<%-- Modal de exibição detalhada de informações--%>
<div class="modal fade bd-example-modal-lg" id="showDetailsModal" tabindex="-2" role="dialog" aria-labelledby="showDetails-title-modal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="header-modal">
                <h5 class="header-modal-title" id="showDetails-modal-title"></h5>
                <button type="button" class="header-modal-close" data-dismiss="modal" aria-label="Close">
                    <svg aria-hidden="true" width="35" height="36" viewBox="0 0 35 36" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M6.67196 7.17184C7.08218 6.76175 7.63848 6.53137 8.21853 6.53137C8.79857 6.53137 9.35487 6.76175 9.76509 7.17184L17.5001 14.9068L25.2351 7.17184C25.6477 6.77337 26.2002 6.55288 26.7738 6.55787C27.3473 6.56285 27.896 6.79291 28.3016 7.19849C28.7071 7.60407 28.9372 8.15272 28.9422 8.72628C28.9472 9.29983 28.7267 9.8524 28.3282 10.265L20.5932 18L28.3282 25.735C28.7267 26.1475 28.9472 26.7001 28.9422 27.2737C28.9372 27.8472 28.7071 28.3959 28.3016 28.8014C27.896 29.207 27.3473 29.4371 26.7738 29.4421C26.2002 29.4471 25.6477 29.2266 25.2351 28.8281L17.5001 21.0931L9.76509 28.8281C9.35252 29.2266 8.79995 29.4471 8.2264 29.4421C7.65284 29.4371 7.10419 29.207 6.69861 28.8014C6.29303 28.3959 6.06297 27.8472 6.05799 27.2737C6.05301 26.7001 6.27349 26.1475 6.67196 25.735L14.407 18L6.67196 10.265C6.26187 9.85475 6.03149 9.29845 6.03149 8.7184C6.03149 8.13836 6.26187 7.58206 6.67196 7.17184Z" fill="#1C1C1C" fill-opacity="0.5"></path>
                    </svg>
                </button>
            </div>
            <div class="body-modal">
                <div class="info-container">
                    <div class="info-content">
                        <h6 class="uniqueItemTitle">
                            Caminhão Utilizado
                        </h6>
                        <table class="showDetailsTable">
                            <tr>
                                <th class="last-info-content">Placa</th>
                            </tr>
                            <tr>
                                <td class="last-info-content"><p id="placa-info-table"></p></td>
                            </tr>
                        </table>

                        <h6 class="uniqueItemTitle">
                            Motorista Responsável
                        </h6>
                        <table class="showDetailsTable">
                            <tr>
                                <th class="last-info-content">Nome</th>
                            </tr>
                            <tr>
                                <td class="last-info-content"><p id="nome-info-table"></p></td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="info-container">
                    <div class="info-content">
                        <table class="showDetailsTable">
                            <tr>
                                <th>Origem</th>
                                <th>Destino</th>
                                <th class="last-info-content">Estado</th>
                            </tr>
                            <tr>
                                <td><p id="origem-info-table"></p></td>
                                <td><p id="destino-info-table"></p></td>
                                <td class="last-info-content"><p id="estado-info-table"></p></td>
                            </tr>
                        </table>
                        <table class="showDetailsTable">
                            <tr>
                                <th>Peso</th>
                                <th class="last-info-content">Valor/Tonelada</th>
                            </tr>
                            <tr>
                                <td><p id="peso-info-table" data-type="peso"></p></td>
                                <td class="last-info-content"><p id="valorTonelada-info-table" data-type="valorTonelada"></p></td>
                            </tr>
                        </table>
                        <table class="showDetailsTable">
                            <tr>
                                <th>Valor Bruto</th>
                                <th>Valor Liquido</th>
                                <th class="last-info-content">Parte Motorista</th>
                            </tr>
                            <tr>
                                <td><p id="valorLiquido-info-table" data-type="dinheiro"></p></td>
                                <td><p id="valorBruto-info-table" data-type="dinheiro"></p></td>
                                <td class="last-info-content"><p id="parteMotorista-info-table" data-type="dinheiro"></p></td>
                            </tr>
                        </table>
                        <div style="width: 100%;">
                            <h6 class="uniqueItemTitle">
                                Observação
                            </h6>
                            <p id="observacao-info-table" class="observacao-container"></p>
                        </div>
                    </div>
                </div>
                <div class="info-container">
                    <div id="showDetailsButtons">
                        <a id="showDetailsDeleteButton" data-toggle="modal" data-item-cod="" data-target="#deleteModal">Excluir</a>
                        <a id="showDetailsEditButton" data-toggle="modal" data-item-cod="" data-target="#persistModal">Editar</a>
                    </div>
                </div>
            </div>
            <div class="footer-modal"></div>
        </div>
    </div>
</div>

<!-- Modal de cadastro e edição-->
<div class="modal fade bd-example-modal-lg" id="persistModal" tabindex="-1" role="dialog" aria-labelledby="header-modal-title" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="header-modal">
                <h5 class="header-modal-title" id="header-modal-title"></h5>
                <button type="button" class="header-modal-close" data-dismiss="modal" aria-label="Close">
                    <svg aria-hidden="true" width="35" height="36" viewBox="0 0 35 36" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M6.67196 7.17184C7.08218 6.76175 7.63848 6.53137 8.21853 6.53137C8.79857 6.53137 9.35487 6.76175 9.76509 7.17184L17.5001 14.9068L25.2351 7.17184C25.6477 6.77337 26.2002 6.55288 26.7738 6.55787C27.3473 6.56285 27.896 6.79291 28.3016 7.19849C28.7071 7.60407 28.9372 8.15272 28.9422 8.72628C28.9472 9.29983 28.7267 9.8524 28.3282 10.265L20.5932 18L28.3282 25.735C28.7267 26.1475 28.9472 26.7001 28.9422 27.2737C28.9372 27.8472 28.7071 28.3959 28.3016 28.8014C27.896 29.207 27.3473 29.4371 26.7738 29.4421C26.2002 29.4471 25.6477 29.2266 25.2351 28.8281L17.5001 21.0931L9.76509 28.8281C9.35252 29.2266 8.79995 29.4471 8.2264 29.4421C7.65284 29.4371 7.10419 29.207 6.69861 28.8014C6.29303 28.3959 6.06297 27.8472 6.05799 27.2737C6.05301 26.7001 6.27349 26.1475 6.67196 25.735L14.407 18L6.67196 10.265C6.26187 9.85475 6.03149 9.29845 6.03149 8.7184C6.03149 8.13836 6.26187 7.58206 6.67196 7.17184Z" fill="#1C1C1C" fill-opacity="0.5"></path>
                    </svg>
                </button>
            </div>
            <div class="body-modal">
                <form class="body-modal-form" id="persistForm">
                    <h6 class="required-warning">
                        Campos com<span class="required">&nbsp;*&nbsp;</span>são obrigatórios!
                    </h6>
                    <div class="form-input-area">
                        <div class="input-wrapper">
                            <div class="input-wrapper-title">
                                Localidades
                            </div>
                            <div class="input-wrapper-inputs">
                                <div class="input-container">
                                    <input class="input-text" type="text" id="origem" name="origem" maxlength="35" required>
                                    <label class="input-label" for="origem"><span class="required">*&nbsp;</span>Origem</label>
                                </div>
                                <div class="input-container">
                                    <input class="input-text" type="text" id="destino" name="destino" maxlength="35" required>
                                    <label class="input-label" for="destino"><span class="required">*&nbsp;</span>Destino</label>
                                </div>
                            </div>
                        </div>
                        <div class="input-wrapper">
                            <div class="input-wrapper-title">
                                Transportador
                            </div>
                            <div class="input-wrapper-inputs">
                                <div class="input-container">
                                    <select id="caminhao" class="input-select dinamic"  name="caminhao" required>
                                        <%--  caminhoes disponiveis inseridos por JS--%>
                                    </select>
                                    <label class="input-label" for="caminhao"><span class="required">*&nbsp;</span>Caminhão (Placa)</label>
                                </div>
                                <div class="input-container">
                                    <select id="motorista" class="input-select dinamic"  name="motorista" required>
                                        <%-- motoristas disponiveis inseridos por JS--%>
                                    </select>
                                    <label class="input-label" for="motorista"><span class="required">*&nbsp;</span>Motorista</label>
                                </div>
                            </div>
                        </div>

                        <div class="input-wrapper">
                            <div class="input-wrapper-title">
                                Outros
                            </div>
                            <div class="input-wrapper-inputs">
                                <div class="input-container">
                                    <input class="input-text" type="number" id="peso" name="peso" min="1000" max="99999" step="0.01" maxlength="5" required>
                                    <label class="input-label" for="peso"><span class="required">*&nbsp;</span>Peso (KG)</label>
                                </div>
                                <div class="input-container">
                                    <input class="input-text" type="number" id="valorTonelada" name="valorTonelada" min="100" max="9999999" step="0.01" maxlength="7" required>
                                    <label class="input-label" for="valorTonelada"><span class="required">*&nbsp;</span>Valor/Tonelada (R$)</label>
                                </div>
                                <div class="input-container">
                                    <select id="estado" class="input-select static"  name="estado" required>
                                        <option value="" selected></option>
                                        <option value="pendente">Pendente</option>
                                        <option value="concluído">Concluído</option>
                                    </select>
                                    <label class="input-label" for="estado"><span class="required">*&nbsp;</span>Estado do Frete</label>
                                </div>
                            </div>
                        </div>

                        <div class="input-container">
                            <textarea class="input-textarea" name="observacao" id="observacao" cols="30" rows="10" maxlength="256"></textarea>
                            <label class="input-textarea-label" for="observacao">Observações</label>
                        </div>
                    </div>

                    <button type="submit" class="persistButton" id="persistButton"></button>
                </form>
            </div>
            <div class="footer-modal"></div>
        </div>
    </div>
</div>

<jsp:include page="../components/delete-modal.jsp"/>

</body>

<script src="<%=request.getContextPath()%>/js/table.js"></script>
<script src="<%=request.getContextPath()%>/js/input.js"></script>
<script src="<%=request.getContextPath()%>/js/sidebar.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery-3.7.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.mask.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.maskedinput.js"></script>

<script>
    const entidade = 'frete';
    const incrementId = 'increment';
    const decrementId = 'decrement';
    const cols = [
        "origem",
        "destino",
        "peso",
        "valorBruto",
        "valorLiquido"
    ];
    const colsNames = [
        "origem",
        "destino",
        "peso",
        "valor bruto",
        "valor liquido"
    ];

    const maxItens = 15;

    inicializarPagina(entidade, cols, colsNames, maxItens);
</script>
</html>
