<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<div class="barra-lateral">
    <div class="logo">
        <img src="<%=request.getContextPath()%>/img/logo.png" alt="Logo do aplicativo">
    </div>
    <div class="menu">
        <ul>
            <li>
                <a href="motorista">
                    <div class="barra-lateral-item">
                        <div class="barra-lateral-item-svg">
                            <svg class="barra-lateral-item-svg">

                            </svg>
                        </div>
                        <div class="barra-lateral-item-texto">
                            <p>Motorista</p>
                        </div>
                    </div>
                </a>
            </li>
            <li>
                <a href="caminhao">
                    <div class="barra-lateral-item">
                        <div class="barra-lateral-item-svg">
                            <svg class="barra-lateral-item-svg">

                            </svg>
                        </div>
                        <div class="barra-lateral-item-texto">
                            <p>Caminhao</p>
                        </div>
                    </div>
                </a>
            </li>
        </ul>
        <div class="barra-lateral-sair">
            <a class="sair-button" href="logout">Sair</a>
        </div>
    </div>
</div>
