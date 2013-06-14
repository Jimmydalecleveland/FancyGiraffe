<%--
    Document   : Relocate page
    Created on : Apr 18, 2013, 3:09:47 PM
    Author     : Jimmy Cleveland
--%>

<%@page import="javax.sql.rowset.CachedRowSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Relocate Assets</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="Jimmy Cleveland" content="">

        <!-- Le styles -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/site.css" rel="stylesheet">
        <link href="css/bootstrap-responsive.css" rel="stylesheet">
    </head>

    <body>
        
        <!-- Clear the session variables for the groups page -->
        <% if (session.getAttribute("sessionGroupCrs") != null) {
               session.setAttribute("sessionGroupCrs", null); }
           if (session.getAttribute("sessionDissolveCrs") != null) {
               session.setAttribute("sessionDissolveCrs", null); } %>

        <div class="container">

            <div class="masthead">
                <% String a=session.getAttribute("username").toString();%>
                <h3 class="muted">FancyGiraffe<span class="pull-right" style="font-size: 16px;">
                        <a href="logout.jsp">Logout</a>
                </h3>
                <!-- navbar -->
                <jsp:include page="navbar.jsp" >
                    <jsp:param name="pageName" value="relocate" />
                </jsp:include>
                <!-- /.navbar -->
            </div>

            <div class="container">
                <div style="margin:  0 auto;">
                    <div class="form-inline" style="padding: 30px;">
                        <form action="QueryServlet" method="POST">
                            <% CachedRowSet locations = (CachedRowSet)request.getAttribute("locations"); %>
                            <% String resultText = (String) request.getAttribute("resultText"); %>                            
                                                    
                            <div class="control-group <% if(resultText != null) { %>error<% } %>">
                                <div class="controls">
                                    <div class="input-prepend">                                       
                                       
                                        <!--     Asset Input     -->
                                        <span class="add-on"><i class="icon-barcode"></i></span>
                                        <input type="text" name="assetTag" class="input-medium assetPopover" placeholder="Asset Tag">
                                    </div>
                                    <span class="help-inline"><% if (resultText != null) {%><%=resultText%><% }%></span>
                                </div>
                            </div>
                        </form>
                            <input type="submit" style="display: none;">
                        
                        <form action="ReassignServlet" method="POST" class="pull-right" style="margin-top: -54px;">
                            <% if (locations!=null) { %>
                            <select name="location" style="margin-right: 24px;">
                                <option selected></option>
                                <% while(locations.next()) { %>
                                <option value="<%=locations.getString("id")%>"><%=locations.getString("location_name")%></option>
                                <% } %>
                            </select>
                            
                            <!--    Relocate Submit     -->
                            <button type="submit" class="btn btn-success" style="height: 35px; line-height: 25px; font-size: 20px;">Relocate</button>
                            <% } %>
                        </form>
                                                
                    </div>
                </div>                 
                        
                 <!-- Reassign Success/Fail logic: returns how many asset's location were changed -->
                 <% if (request.getAttribute("count") != null) {
                        Integer count = (Integer)request.getAttribute("count");
                        if (count == 0) { %>
                            <div id="errorMessage" class="alert alert-error">
                                <strong>Error!</strong> An asset had a problem during relocation.
                            </div>
                        <% } else { session.setAttribute("sessionRelocateCrs", null); %>                 
                            <div id="successMessage" class="alert alert-success">
                                <strong>Success!</strong> <%=count%> items were moved.
                            </div>
                        <% } } %>
                
                <hr style="margin-top: -10px;">
                <% 
                    CachedRowSet data = (CachedRowSet)session.getAttribute("sessionRelocateCrs");
                    if (data != null) { 
                %>
                <table class="table table-striped table-bordered" id="assetQueueTable" style="margin-top: -15px; background-color: white;">
                    
                    <!-- Used to count the rows in the table -->
                    <% data.last();
                        int count = data.getRow(); %>
                    <caption style="margin-bottom: 5px;"><h4>Asset Queue</h4>
                        <span class="badge badge-success pull-right">x<%=count%></span></caption>
                    <thead>
                        <tr>
                            <th>Asset ID</th>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Model</th>
                            <th>Condition</th>
                            <th>Location</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        data.afterLast();
                        while (data.previous()) {
                    %>
                    <tr>
                        <td><%= data.getString("tag") %></td>
                        <td><%= data.getString("asset_name") %></td>
                        <td><%= data.getString("asset_type") %></td>
                        <td><%= data.getString("model") %></td>
                        <td><%= data.getString("asset_condition") %></td>
                        <td><%= data.getString("location_name") %></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <form action="ClearSessionServlet" method="POST">
                    <input type="hidden" name="page" value="relocate" />
                    <button type="submit" class="btn">Clear table</button>
                </form>   
                <% } %>
            </div>
            <hr>

        </div> <!-- /container -->
        <!-- javascript -->
        <script src="http://code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script type="text/javascript">
            //function showStuff(id) {
            //    document.getElementById(id).style.display = 'table';
            //}
            //function hideStuff(id) {
            //    document.getElementById(id).style.display = 'none';
            //}
            
            $('.assetPopover').popover({
              title: 'Asset Tag',
              content: 'Type or scan your asset tag here then press Enter.',
              trigger: 'focus',
              placement: 'right'
            });
            
            
            
        </script>

    </body>
</html>
