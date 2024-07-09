<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<div class="table-page-container">
    <h1 id="page-title"></h1>
    <div class="main-container">
        <div class="options-container">
            <button type="button" id="cadastrarTableButton" class="cadastrar-table-button" data-toggle="modal" data-target="#persistModal">
                Cadastrar
            </button>
        </div>
        <div class="table-container">
            <div class="table-container-header">
                <div class="table-container-header-search">
                    <input type="text" id="search" class="table-container-header-search-input" placeholder="Pesquisar...">
                    <button type="button" id="search-button" class="table-container-header-search-button">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
                <jsp:include page="nav-buttons.jsp"/>
            </div>
            <div class="table-container-body">
                <table id="table" class="table-container-body">
                    <thead id="table-header">
                    <!-- Cabeçalho da tabela -->
                    </thead>
                    <tbody id="table-body">
                    <!-- Corpo da tabela -->
                    </tbody>
                </table>
            </div>
            <div class="table-container-footer"></div>
        </div>
    </div>
</div>
