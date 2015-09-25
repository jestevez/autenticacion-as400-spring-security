<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <link rel="shortcut icon" href="${pageContext.servletContext.contextPath }/favicon.ico" />

        <title></title>
    </head>
    <body>
        <div>
            <spring:message code="app.enterprise" />
        </div>
        <div id="greeting">
            <p>Bienvenido, &nbsp;<b>${username}</b> <a href="${pageContext.servletContext.contextPath}/app/auth/logout" >[Salir]</a></p>
            &nbsp;&nbsp;
        </div>
            <h1>Estas Autenticado!</h1>
            <footer><spring:message code="app.copyright" /></footer>
    </body>
</html>