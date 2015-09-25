<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>
            <spring:message code="app.title" />
        </title>
        <script>
            if (top != self)
                top.location.href = '<%= request.getContextPath()%>/app/auth/login';
        </script>

        <link rel="shortcut icon" href="${pageContext.servletContext.contextPath }/favicon.ico" />
        <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.servletContext.contextPath }/css/main.css" />

        <script src="${pageContext.servletContext.contextPath }/js/jquery-latest.js"></script>
        <script src="${pageContext.servletContext.contextPath }/js/login.js"></script>

    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class=''>

                    <p><spring:message code="app.name" /></p>

                    <span class="hora">
                        <c:set var="now" value="<%=new java.util.Date()%>" />
                        <b>
                            <fmt:formatDate pattern="EEEE dd MMMM yyyy" value="${now}" />&nbsp;<span id="clock"><fmt:formatDate pattern="HH:mm:ss" value="${now}" /></span>
                            <input type="hidden" value="<fmt:formatDate pattern="HH:mm:ss" value="${now}" />" id="horaserver" />
                    </span>
                    </b>
                </div>
            </div>

            <div class="row">

                <form method="POST" id="form1" name="form1" 
                      action="${pageContext.servletContext.contextPath }/j_spring_security_check"
                      autocomplete="off">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="timeout" id="timeout" value="${timeout}" />
                    <c:out value="${message}"/>
                    <c:if test="${sessionScope.levelerror != null}">
                        <div id="levelerror " class="error">
                            <c:out value="${sessionScope.levelerror}" />
                            <c:remove var="levelerror" scope="session"/>
                        </div>    
                    </c:if>

                    <table cellspacing="1">
                        <tr>
                            <td>
                                <label for="signin_username">Usuario</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input id="j_username" name="j_username" type="text" value="" maxlength="10"  type="text">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="signin_password">Contraseña</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input id="j_password" name="j_password" type="password" value="" maxlength="10">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="submit" value="Autenticar">
                            </td>
                        </tr>

                    </table>

                </form>




            </div>





        </div>
        <footer>
            <b><spring:message code="app.copyright" /></b>
        </footer>
    </body>
</html>
