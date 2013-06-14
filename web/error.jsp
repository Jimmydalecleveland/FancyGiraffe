<%-- 
    Document   : error
    Created on : May 13, 2013, 12:33:52 PM
    Author     : Jimmy
--%>

<%@page language="java" contentType="text/html" isErrorPage="true" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Le styles -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/site.css" rel="stylesheet">
        <link href="css/bootstrap-responsive.css" rel="stylesheet">
    </head>
    <body>
        <div class="miniBox">
        <h3>An error has occurred.</h3>
        <p class="alert alert-danger"><b>Error Code:</b> ${pageContext.errorData.statusCode}</p>
        <p class="alert alert-danger"><b>Request URI:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p><br />
        <button class="btn" onclick ="history.back();">Back to Previous Page</button>          
        </div>
    </body>
</html>
