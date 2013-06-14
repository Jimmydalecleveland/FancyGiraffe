
<%@page import="javax.sql.rowset.CachedRowSet" errorPage="error.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Groups</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Le styles -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/site.css" rel="stylesheet">
        <link href="css/bootstrap-responsive.css" rel="stylesheet">

    </head>

    <body>

        <!-- Clear the session variable from the relocate page -->
        <% if (session.getAttribute("sessionRelocateCrs") != null) {
               session.setAttribute("sessionRelocateCrs", null); } %>
        
        <div class="container">

            <div class="masthead">
                <% String a=session.getAttribute("username").toString();%>
                <h3 class="muted">FancyGiraffe<span class="pull-right" style="font-size: 16px;">
                        <a href="logout.jsp">Logout</a>
                </h3>
                <!-- navbar -->
                <jsp:include page="navbar.jsp" >
                    <jsp:param name="pageName" value="groups" />
                </jsp:include>
                <!-- /.navbar -->
            </div>

            <div class="container" style="margin-top: 30px; padding: 20px;">
                <h3>Manage Asset Grouping</h3>
                <div class="tabbable" >
                    <!-- Tab Headings-->
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab1" data-toggle="tab">Create Group</a></li>
                        <li><a href="#tab2" data-toggle="tab">Edit Group</a></li>
                        <li><a href="#tab3" data-toggle="tab">Dissolve Group</a></li>
                    </ul> <!-- /Tab Headings-->
                    <!-- Tab Contents-->
                    <div class="tab-content">

                        <!-- Tab1: Create Group -->
                        <div class="tab-pane active" id="tab1">
                            <div style="padding: 30px;">
                                <form class="form-inline" method="POST" action="GroupQueryServlet">
                                    <% String createResultText = (String) request.getAttribute("createResultText"); %>                                    
                                    
                                    <input type="hidden" name="action" value="create">
                                    <!-- if an error result is passed back add "error" to the class -->
                                    <div class="control-group <% if(createResultText != null) { %>error<% } %>">
                                        <div class="controls">
                                            <div class="input-prepend">
                                                
                                            <!--     Asset Input     -->
                                            <span class="add-on"><i class="icon-barcode"></i></span>
                                            <input type="text" name="assetTag" class="input-medium" placeholder="asset tag">
                                            </div>
                                            <span class="help-inline"><% if (createResultText != null) {%><%=createResultText%><% }%></span>
                                        </div>
                                    </div>
                                    <input type="submit" style="display: none;">
                                </form>
                                <form action="GroupServlet" method="POST" class="pull-right" style="margin-top: -50px;">
                                    <input type="hidden" name="action" value="create">
                                    <button type="submit" class="btn btn-success">Submit Group</button>
                                </form>                            
                                    <% if (request.getAttribute("ccount") != null) { 
                                        Integer count = (Integer)request.getAttribute("ccount");
                                        if (count == 0) { %>
                                            <div id="errorMessage" class="alert alert-error">
                                            <strong>Error!</strong> Error occurred creating group.
                                            </div>
                                    <% } else { session.setAttribute("sessionGroupCrs", null); %>            
                                            <div id="successMessage" class="alert alert-success">
                                            <strong>Success!</strong> <%=count%> items were grouped.
                                            </div>
                                    <% } } %>

                                    <% 
                                        CachedRowSet data = (CachedRowSet)session.getAttribute("sessionGroupCrs");
                                        if (data != null) { 
                                    %>
                                    <!-- Table Start -->
                                    <table class="table table-striped table-bordered" id="assetQueueTable" style="background-color: white;">
                                        
                                        <caption style="margin-bottom: 5px;"><h4>Assets to be grouped</h4></caption>
                                        <thead>
                                        <tr>
                                            <th>Asset Tag</th>
                                            <th>Name</th>                                         
                                            <th>Type</th>
                                            <th>Condition</th>
                                            <th>Location</th>
                                            <th>Group</th>
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
                                            <td><%= data.getString("asset_condition") %></td>
                                            <td><%= data.getString("location_name") %></td>
                                            <td><%= data.getString("group_id") %></td>
                                        </tr>
                                        <% } %>
                                   </tbody>
                                   </table>
                                   <form action="ClearSessionServlet" method="POST">
                                       <input type="hidden" name="page" value="groups" />
                                       <button type="submit" class="btn">Clear table</button>
                                   </form>
                                   <% } %>
                                   </div>
                        </div>

                        <!-- Tab 2-->
                        <!--    To Add asset to group:  Scan or enter an assetID in the input box and press enter.
                                To Remove asset:        Check the box under the red X for the appropriate row you wish to remove.
                                To Complete changes:    Click "Submit Group" button to finalize changes. 
                        -->
                        <div class="tab-pane" id="tab2">
                            <div style="padding: 30px;">
                                <div class="alert alert-danger"><strong>Demo Model - Not Currently Working</strong></div>
                                <form class="form-inline">
                                    <div class="input-prepend">
                                        <span class="add-on"><i class="icon-barcode"></i></span>
                                    <input type="text" class="input-medium" placeholder="asset tag" >
                                    </div>
                                    <button type="submit" class="btn btn-success pull-right" >Submit Changes</button>
                                    <!-- Table Start -->
                                    <table class="table table-striped table-bordered" id="assetQueueTable" style="background-color: white;">
                                        <caption style="margin-bottom: 5px;"><h4>Asset Group</h4></caption>
                                        <thead>
                                        <tr>
                                            <th>AssetID</th>
                                            <th>Location</th>
                                            <th>Description</th>
                                            <th>MFR</th>
                                            <th>AssetType</th>
                                            <th style="width: 10px;">
                                                <a href="#" class="tooltips" style="color: red; text-decoration: none;" data-toggle="tooltip" data-placement="right" data-original-title="Check the box next to each asset you wish to remove from the group.">X
                                                </a>
                                            </th>
                                        </tr>
                                    </thead>
                                        <tbody>
                                        <tr>
                                            <td>10010</td>
                                            <td>Office</td>
                                            <td>HD Black Printer</td>
                                            <td>HP</td>
                                            <td>Printer</td>
                                            <td><input type="checkbox"></td>
                                        </tr>
                                        <tr>
                                            <td>10011</td>
                                            <td>Office</td>
                                            <td>HD Color Printer</td>
                                            <td>HP</td>
                                            <td>Printer</td>
                                            <td><input type="checkbox"></td>
                                        </tr>
                                        <tr>
                                            <td>10012</td>
                                            <td>Warehouse</td>
                                            <td>HD Printer Cartridge</td>
                                            <td>HP</td>
                                            <td>Printer Set</td>
                                            <td><input type="checkbox"></td>
                                        </tr>
                                        <tr>
                                            <td>10013</td>
                                            <td>Office</td>
                                            <td>HD Printer Tray</td>
                                            <td>HP</td>
                                            <td>Printer Set</td>
                                            <td><input type="checkbox"></td>
                                        </tr>
                                   </tbody>
                                    </table>
                                </form>
                            </div>
                        </div>

                        <!-- Tab3: Dissolve Group -->
                        <div class="tab-pane" id="tab3">
                            <div style="padding: 30px;">
                                <form action="GroupQueryServlet" method="POST" class="form-inline">
                                    <% String dissolveResultText = (String) request.getAttribute("dissolveResultText"); %>
                                    
                                    <input type="hidden" name="action" value="dissolve">
                                    
                                    <div class="control-group <% if(dissolveResultText != null) { %>error<% } %>">
                                        <div class="controls">
                                            <div class="input-prepend">
                                        
                                            <!--    Asset Input   -->
                                            <span class="add-on"><i class="icon-barcode"></i></span>
                                            <input type="text" class="input-medium" name="assetTag" placeholder="asset tag">
                                             </div>
                                            <span class="help-inline"><% if (dissolveResultText != null) {%><%=dissolveResultText%><% }%></span>
                                        </div>
                                    </div>                                    
                                        <input type="submit" style="display: none;">
                                </form>
                                <form action="GroupServlet" method="POST" class="pull-right" style="margin-top: -50px;">
                                    <input type="hidden" name="action" value="dissolve">
                                    <button type="submit" class="btn btn-danger pull-right">Dissolve Group</button>
                                </form>
                                    <% if (request.getAttribute("dcount") != null) { 
                                        Integer count = (Integer)request.getAttribute("dcount");
                                        if (count == 0) { %>
                                            <div id="errorMessage" class="alert alert-error">
                                            <strong>Error!</strong> Error occurred dissolving group.
                                            </div>
                                    <% } else { session.setAttribute("sessionDissolveCrs", null); %>            
                                            <div id="successMessage" class="alert alert-success">
                                            <strong>Success!</strong> <%=count%> items were ungrouped.
                                            </div>
                                    <% } } %>
                                    <% 
                                        data = (CachedRowSet)session.getAttribute("sessionDissolveCrs");
                                        if (data != null) { 
                                    %>
                                    
                                    <!-- Table Start -->
                                    <table class="table table-striped table-bordered" id="assetQueueTable" style="background-color: white;">
                                        
                                        <caption style="margin-bottom: 5px;"><h4>Assets to be grouped</h4></caption>
                                        <thead>
                                        <tr>
                                            <th>Asset Tag</th>
                                            <th>Name</th>
                                            <th>Type</th>
                                            <th>Condition</th>
                                            <th>Location</th>
                                            <th>Group</th>
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
                                            <td><%= data.getString("asset_condition") %></td>
                                            <td><%= data.getString("location_name") %></td>
                                            <td><%= data.getString("group_id") %></td>
                                        </tr>
                                        <% } %>
                                   </tbody>
                                   </table>
                                   <form action="ClearSessionServlet" method="POST">
                                       <input type="hidden" name="page" value="groups" />
                                       <button type="submit" class="btn">Clear table</button>
                                   </form>
                                   <% } %>
                            </div>
                        </div>
                    </div> <!-- /Tab Contents-->
                </div>
            </div>

            <hr>
        </div> <!-- /container -->
        <!-- Le javascript -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="http://code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/bootstrap-dropdown.js"></script>

        <script>
            $(function () {
				
				//Set active tab
				var tab = <%= request.getAttribute("activeTab") %>;
				tab = (tab === null) ? 1 : tab;				
				switch(tab) {
					case 2:
						$('a[href=#tab2]').tab('show');
						break;
					case 3:
						$('a[href=#tab3]').tab('show');
						break;
				}
				
				
                $('.tooltips').tooltip();
            });
        </script>

    </body>
</html>
