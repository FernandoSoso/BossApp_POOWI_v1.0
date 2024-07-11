<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<c:if test="${sessionScope.usuario == null}">
    <meta http-equiv="refresh" content="0; url=login" />
</c:if>