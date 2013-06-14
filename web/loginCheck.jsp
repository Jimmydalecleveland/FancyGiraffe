<%-- 
    Document   : loginCheck
    Created on : May 13, 2013, 11:48:05 AM
    Author     : Jimmy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Check</title>
    </head>
    <body>
        <%
            String username=request.getParameter("username");
            String password=request.getParameter("password");
            
            if (username.equals("admin") && password.equals("12345"))
            {
                session.setAttribute("username",username);
                response.sendRedirect("home.jsp");
            }
            else
                response.sendRedirect("loginError.jsp");
         %>
    </body>
</html>
