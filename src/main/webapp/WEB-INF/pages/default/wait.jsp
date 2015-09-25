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
            <spring:message code="app.title" />
        </title>
        <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.servletContext.contextPath }/css/main.css" />
        <script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/jquery-latest.js"></script>
        <script>
            $( document ).ready(function() {
                console.log("load body");
                document.forms["waitForm"].submit();
            });
         
        </script>
    </head>
    <body>
        <center>
        <p style="font-size: 23pt"><spring:message code="app.wait" /></p>
        <img style="width:200px" alt="<spring:message code="app.wait" />" src="${pageContext.servletContext.contextPath }/images/loading.gif" />
        <form method="post" action="${param.url}" name="waitForm" id="waitForm">
            <c:forEach var='parameter' items='${paramValues}'>
                <c:forEach var='value' items='${parameter.value}'>
                    <input type="hidden" name="<c:out value='${parameter.key}' escapeXml="true"/>" value="<c:out value='${value}' escapeXml="true"/>" />
                </c:forEach>
            </c:forEach>
        </form>
        </center>
    </body>
</html>  