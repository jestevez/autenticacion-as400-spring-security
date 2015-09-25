<%@page session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <title>Logged Out</title>
        <script>
            if (top != self)
                top.location.href = '<%= request.getContextPath()%>/app/auth/login';
        </script>
    </head>
    <body>
        <div id="content">
            <h2>Logged Out</h2>
            <p>
                <a href="${pageContext.servletContext.contextPath }/app/auth/login">Autenticar</a>	
            </p>
        </div>
    </body>
</html>