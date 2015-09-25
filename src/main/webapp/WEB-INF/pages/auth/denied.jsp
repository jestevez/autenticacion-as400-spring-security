<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title></title>

    </head>

    <body>

        <div align="center" class="sistema">

            <br><br>
            <div>
                <div class="error">
                    Disculpe usted no se encuentra autorizado para ver el recurso solicitado
                </div>
                <br>
                <div>
                    <a href="${pageContext.servletContext.contextPath }/app/auth/login">Volver</a>	
                </div>
            </div>
        </div>
        <br>

    </body>
</html>