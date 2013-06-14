<%-- 
    Document   : home
    Created on : May 13, 2013, 11:58:24 AM
    Author     : Jimmy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FancyGiraffe</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Le styles -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/site.css" rel="stylesheet">
        <link href="css/bootstrap-responsive.css" rel="stylesheet">
    </head>
    <body>
        <!-- Clear the session variables -->
        <% if (session.getAttribute("sessionGroupCrs") != null) {
               session.setAttribute("sessionGroupCrs", null); }
           if (session.getAttribute("sessionDissolveCrs") != null) {
               session.setAttribute("sessionDissolveCrs", null); }
           if (session.getAttribute("sessionRelocateCrs") != null) {
               session.setAttribute("sessionRelocateCrs", null); } %>
               
        <div class="container">            
            <div class="masthead">
                <% String a=session.getAttribute("username").toString();%>
                <h3 class="muted">FancyGiraffe<span class="pull-right" style="font-size: 16px;">
                        <a href="logout.jsp">Logout</a>
                </h3>
                <!-- navbar -->
                <div class="navbar">
                    <div class="navbar-inner">
                        <div class="container">
                            <ul class="nav">
                                <li class="active"><a href="#">Home</a></li>
                                <li><a href="assets">Assets</a></li>
                                <li><a href="groups.jsp">Groups</a></li>
                                <li><a href="locations">Locations</a></li>
                                <li><a href="relocate.jsp">Relocate</a></li>
                                <li><a href="reports">Reports</a></li>
                            </ul>
                        </div>
                    </div>
                </div><!-- /.navbar -->
            </div>

            <!-- Jumbotron -->
            <div class="jumbotron">
                <h1>Welcome to FancyGiraffe:</h1><h2> Asset Management System!</h2>
                <p class="lead">Select an area from the options at the top to get started.</p>
            </div>

            <hr>

        </div> <!-- /container -->
                    <!-- Le javascript
                ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="http://code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/bootstrap-dropdown.js"></script>
    </body>
</html>
