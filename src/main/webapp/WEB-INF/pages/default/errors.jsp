<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

    <head>
        <title>
            .:: ERROR ::.
        </title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="${pageContext.servletContext.contextPath }/favicon.ico" />
        <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.servletContext.contextPath }/css/main.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.servletContext.contextPath }/css/960.css" />
        <script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/jquery-latest.js"></script>

    </head>
    <body>

        <div id="title">
            Error
        </div>
        <div id="ruta">
            <a href="${pageContext.servletContext.contextPath }/app/default/home">Inicio</a> &gt;  Error
        </div>


        <c:if test="${sessionScope.levelsuccess != null}">
            <div id="levelsuccess" class="success">
                <img alt="" class="icon" border="0" src="<c:out value="${pageContext.servletContext.contextPath }"/>/images/icons/success.gif">
                    <c:out value="${sessionScope.levelsuccess}" />
                    <c:remove var="levelsuccess" scope="session"/>
            </div>    
        </c:if>
        <c:if test="${sessionScope.levelerror != null}">
            <div id="levelerror" class="error">
                <img alt="" src="<c:out value="${pageContext.servletContext.contextPath }"/>/images/icons/error.gif" border="0" class="icon">
                    <c:out value="${sessionScope.levelerror}" />
                    <c:remove var="levelerror" scope="session"/>
            </div>    
        </c:if>
        <c:if test="${sessionScope.levelinfo != null}">
            <div id="levelinfo" class="info">
                <img alt="" src="<c:out value="${pageContext.servletContext.contextPath }"/>/images/icons/info.gif" border="0" class="icon">
                    <c:out value="${sessionScope.levelinfo}" />
                    <c:remove var="levelinfo" scope="session"/>
            </div>    
        </c:if>


    </body>
</html>