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
    <title>Caminhao</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/global.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/view.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body id="view-body">

<jsp:include page="../components/barra-lateral.jsp"/>

<div class="view-container">
    <div class="view-titulo">
        <h1>Caminhões</h1>
    </div>
    <div class="view-tabela">
        <div class="opcoes-container">
            <button type="button" class="cadastrar-upper-button" data-toggle="modal" data-target="#cadastrarModal">
                Cadastrar
            </button>
        </div>
        <div class="container-tabela">
            <div class="header"></div>
            <div class="body">
                <table class="table-striped">
                    <tr class="row cabecalho">
                        <th class="col"></th>
                        <th class="col">Placa</th>
                        <th class="col">Marca</th>
                        <th class="col">Modelo</th>
                        <th class="col">Ano</th>
                        <th class="col">Capacidade</th>
                        <th class="col">Estado</th>
                    </tr>
                    <c:forEach var="caminhao" items='${listaCaminhao}'>
                        <c:set var="i" value="${i + 1}" scope="page" />
                        <c:choose>
                            <c:when test="${i%2 == 1}">
                                <tr class="row linha-claro linha">
                            </c:when>
                            <c:otherwise>
                                <tr class="row linha-escuro linha">
                            </c:otherwise>
                        </c:choose>
                            <td class="col">
                                <a class="visualizar-button">
                                    <p class="visualizar-texto">visualizar</p>
                                    <svg class="visualizar-svg" width="17" height="21" viewBox="0 0 17 21" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M5.41174 11.4375V3.46875C5.41174 3.09579 5.55119 2.7381 5.7994 2.47438C6.04761 2.21066 6.38425 2.0625 6.73527 2.0625C7.08629 2.0625 7.42294 2.21066 7.67115 2.47438C7.91936 2.7381 8.0588 3.09579 8.0588 3.46875V10.5M8.0588 10.0312V8.15625C8.0588 7.78329 8.19824 7.4256 8.44645 7.16188C8.69466 6.89816 9.03131 6.75 9.38233 6.75C9.73335 6.75 10.07 6.89816 10.3182 7.16188C10.5664 7.4256 10.7059 7.78329 10.7059 8.15625V10.5M10.7059 9.09375C10.7059 8.72079 10.8453 8.3631 11.0935 8.09938C11.3417 7.83566 11.6784 7.6875 12.0294 7.6875C12.3804 7.6875 12.7171 7.83566 12.9653 8.09938C13.2135 8.3631 13.3529 8.72079 13.3529 9.09375V10.5"></path>
                                        <path d="M13.3529 10.0312C13.3529 9.65829 13.4924 9.3006 13.7406 9.03688C13.9888 8.77316 14.3254 8.625 14.6765 8.625C15.0275 8.625 15.3641 8.77316 15.6123 9.03688C15.8606 9.3006 16 9.65829 16 10.0312V14.25C16 15.7418 15.4422 17.1726 14.4494 18.2275C13.4565 19.2824 12.11 19.875 10.7059 19.875H8.94118H9.12471C8.24794 19.8752 7.38487 19.644 6.61299 19.2021C5.8411 18.7603 5.18457 18.1218 4.70235 17.3437L4.52941 17.0625C4.25412 16.6138 3.28765 14.8238 1.63 11.6925C1.46099 11.3733 1.41584 10.9967 1.50416 10.6426C1.59248 10.2886 1.80731 9.9852 2.10294 9.79688C2.41797 9.59661 2.78686 9.51368 3.15123 9.56123C3.5156 9.60877 3.85465 9.78407 4.11471 10.0594L5.41176 11.4375M2.76471 2.0625L1.88235 1.125M1.88235 5.8125H1M10.7059 2.0625L11.5882 1.125M11.5882 4.875H12.4706"></path>
                                    </svg>
                                </a>
                            </td>
                            <td class="col"><p><c:out value="${caminhao.placa}"/></p></td>
                            <td class="col"><p><c:out value="${caminhao.marca}"/></p></td>
                            <td class="col"><p><c:out value="${caminhao.modelo}"/></p></td>
                            <td class="col"><p><c:out value="${caminhao.ano}"/></p></td>
                            <td class="col"><p><c:out value="${caminhao.capacidade}"/></p></td>
                            <td class="col">
                                <div class="status">
                                    <c:out value="${caminhao.status}"/>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="footer"></div>
        </div>
    </div>
</div>

<!-- Modal de cadastro-->
<div class="modal fade" id="cadastrarModal" tabindex="-1" role="dialog" aria-labelledby="titulo-modal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="header-modal">
                <h5 class="titulo-modal" id="titulo-modal">Cadastrar caminhao</h5>
                <button type="button" class="fechar-modal" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="body-modal">
                <h6 class="aviso-campos-obrigatorios">
                    Campos marcados com <p class="asterisco">*</p> são obrigatórios!
                </h6>
                <form action="caminhao" method="post" class="cadastrar-container">
                    <div class="input-container">
                        <div class="input-text-container">
                            <label for="placa" class="input-text-label"><p class="asterisco">*</p>placa</label>
                            <input type="text" name="placa" id="placa" class="input-text" required>
                        </div>
                        <div class="input-text-container">
                            <label for="modelo" class="input-text-label">modelo</label>
                            <input type="text" name="modelo" id="modelo" class="input-text">
                        </div>
                        <div class="input-text-container">
                            <label for="marca" class="input-text-label">marca</label>
                            <input type="text" name="marca" id="marca" class="input-text">
                        </div>
                        <div class="input-text-container">
                            <label for="ano" class="input-text-label">ano</label>
                            <input type="number" min="1900" max="2100" step="1" name="ano" id="ano" class="input-text">
                        </div>
                        <div class="input-text-container">
                            <label for="motorista" class="input-text-label">motorista</label>
                            <input type="number" step="1" name="motorista" id="motorista" class="input-text">
                        </div>
                        <div class="input-text-container">
                            <label for="percentualMotorista" class="input-text-label"><p class="asterisco">*</p>percentual do motorista</label>
                            <input type="number" step="0.1" name="percentualMotorista" id="percentualMotorista" class="input-text" required>
                        </div>
                        <div class="input-text-container">
                            <label for="capacidade" class="input-text-label">capacidade</label>
                            <input type="text" name="capacidade" id="capacidade" class="input-text">
                        </div>
                        <div class="input-text-container">
                            <label for="status" class="input-select-label"><p class="asterisco">*</p>estado</label>
                            <select id="status" name="status" class="input-select">
                                <option value="" selected></option>
                                <option value="a" >Disponivel</option>
                                <option value="b">Indisponivel</option>
                            </select>
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
</html>
