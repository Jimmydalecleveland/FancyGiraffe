<%-- 
    Document   : logout
    Created on : May 13, 2013, 12:31:40 PM
    Author     : Jimmy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logout</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Le styles -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/site.css" rel="stylesheet">
        <link href="css/bootstrap-responsive.css" rel="stylesheet">
    </head>
    <body>
        <%
            session.removeAttribute("username");
            session.removeAttribute("password");
            session.invalidate();
        %>
        <h2>Logged out successfully</h2>
        <%
        response.sendRedirect("index.html");
        %>
    </body>
</html>
