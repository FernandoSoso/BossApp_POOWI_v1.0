<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<div id="warning-popup">
    <%
        if(request.getAttribute("erro") != null){
    %>
    <p id="warning-popup-message" style='opacity: 1; visibility: visible;'>
        <%= request.getAttribute("erro") %>
    </p>
    <%
    } else {
    %>
    <p id="warning-popup-message">
    </p>
    <%
        }
    %>
</div>