<%@page import="javax.sql.rowset.CachedRowSet" errorPage="error.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Reports</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Le styles -->
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/site.css" rel="stylesheet">
        <link href="css/bootstrap-responsive.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/DT_bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/TableTools.css">
    
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
                <jsp:include page="navbar.jsp" >
                    <jsp:param name="pageName" value="reports" />
                </jsp:include><!-- /.navbar -->
            </div>

            <div class="container" style="margin-top: 50px;">
                <form class="form-inline" action="reports" method="post" style="padding: 15px;">
                    <div class="row">
                        <div class="span2">
                            <div class="controls">
                                <label>Search By Asset Tag</label>
                                <div class="input-prepend">
                                    <span class="add-on"><i class="icon-barcode"></i></span>
                                    <input name="asset_search" type="text" class="input-small" placeholder="Asset Tag">
                                </div>
                            </div>
                        </div>
                        <div class="span2">
                            <div class="controls">
                                <% CachedRowSet locations = (CachedRowSet) request.getAttribute("locations"); %>
                                <label>Search By Location</label>
                                <select name="location" class="input-medium">
                                    <option selected></option>
                                    <% while(locations.next()) { %>
                                    <option value="<%=locations.getString("location_name")%>"><%=locations.getString("location_name")%></option>
                                    <% } %>
                                </select>
                            </div>
                        </div>
                        <div class="span2">
                            <div class="controls">
                                <% CachedRowSet assetTypes = (CachedRowSet) request.getAttribute("assetTypes"); %>
                                <label>Search By Asset Type</label>
                                <select name="type" class="input-medium">
                                    <option selected></option>
                                    <% while(assetTypes.next()) { %>
                                    <% String currentAsset = "" + assetTypes.getString("asset_type"); %>
                                    <option value="<%=currentAsset%>"><%=currentAsset%></option>
                                    <% } %>
                                </select>
                            </div>
                        </div>
                        <div class="span2">
                            <div class="controls">  
                                <% CachedRowSet conditions = (CachedRowSet) request.getAttribute("conditions"); %>
                                <label>Search By Condition</label>
                                <select name="condition" class="input-medium">
                                    <option selected></option>
                                    <% while(conditions.next()) { %>
                                    <% String currentCondition = "" + conditions.getString("asset_condition"); %>
                                    <option value="<%=currentCondition%>"><%=currentCondition%></option>
                                    <% } %>
                                </select>
                            </div>
                        </div>        
                        <div class="span2">
                            <div class="controls">                                
                                <% CachedRowSet districts = (CachedRowSet) request.getAttribute("districts"); %>
                                <label>Search By District</label>
                                <select name="district" class="input-medium">
                                    <option selected></option>
                                    <% while(districts.next()) { %>
                                    <% String currentDistrict = "" + districts.getString("district"); %>
                                    <option value="<%=currentDistrict%>"><%=currentDistrict%></option>
                                    <% } %>
                                </select>
                            </div>
                        </div>
                        <div class="span2">
                            <div class="controls">
                                <label>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label>
                                <button type="submit" class="btn btn-success" style="margin-left: 10px;">Generate Report</button>
<!--                                <a href="/reports" class="btn" style="margin: 10px 0px 0px 10px;">Reset</a>-->
                            </div>
                        </div>
                    </div>
                </form>
                <hr>

                <%  CachedRowSet assets = (CachedRowSet) request.getAttribute("assets"); 
                    if ( assets != null ) {
                %>
                
                <% if (request.getAttribute("table_format").equals("tag")) { %>
                <table class="table table-striped table-bordered" id="assetQueueTable" cellpadding="0" cellspacing="0" border="0" style="background-color: white; max-width: 700px;">
                    <caption style="margin-top: -22px;"><strong>Assets with tag <%= request.getAttribute("title") %></strong></caption>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Tag</th>
                        <th>Name</th>
                        <th>Model</th>
                        <th>Serial</th>
                        <th>Value</th>
                        <th>Condition</th>
                        <th>Type</th>
                        <th>Location</th>
                        <th>Address</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Zip</th>
                        <th>Phone</th>
                        <th>District</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% while(assets.next()) { %>
                    <tr>
                        <td><%= assets.getString("id") %></td>
                        <td><%= assets.getString("tag") %></td>
                        <td><%= assets.getString("asset_name") %></td>
                        <td><%= assets.getString("model") %></td>
                        <td><%= assets.getString("ser_no") %></td>
                        <td><%= assets.getString("value") %></td>
                        <td><%= assets.getString("asset_condition") %></td>
                        <td><%= assets.getString("asset_type") %></td>
                        <td><%= assets.getString("location_name") %></td>
                        <td><%= assets.getString("address") %></td>
                        <td><%= assets.getString("city") %></td>
                        <td><%= assets.getString("state") %></td>
                        <td><%= assets.getString("zip") %></td>
                        <td><%= assets.getString("phone") %></td>
                        <td><%= assets.getString("district") %></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <% } else if (request.getAttribute("table_format").equals("location")) { %>
                 <table class="table table-striped table-bordered" id="assetQueueTable" cellpadding="0" cellspacing="0" border="0" style="background-color: white; max-width: 700px;">                    
                    <caption style="margin-top: -22px;"><strong>Assets with location <%= request.getAttribute("title") %></strong></caption>
                    <thead>
                    <tr>
                        <th>Tag</th>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Serial</th>
                        <th>Value</th>
                        <th>Condition</th>
                        <th>Location</th>
                        <th>Address</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Zip</th>
                        <th>Phone</th>
                        <th>Last edited</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% while(assets.next()) { %>
                    <tr>
                        <td><%= assets.getString("tag") %></td>
                        <td><%= assets.getString("asset_name") %></td>
                        <td><%= assets.getString("asset_type") %></td>
                        <td><%= assets.getString("ser_no") %></td>
                        <td><%= assets.getString("value") %></td>
                        <td><%= assets.getString("asset_condition") %></td>
                        <td><%= assets.getString("location_name") %></td>
                        <td><%= assets.getString("address") %></td>
                        <td><%= assets.getString("city") %></td>
                        <td><%= assets.getString("state") %></td>
                        <td><%= assets.getString("zip") %></td>
                        <td><%= assets.getString("phone") %></td>
                        <td><%= assets.getString("modified") %></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <% } else if (request.getAttribute("table_format").equals("type")) { %> 
                <table class="table table-striped table-bordered" id="assetQueueTable" cellpadding="0" cellspacing="0" border="0" style="background-color: white; max-width: 700px;">                    
                    <caption style="margin-top: -22px;"><strong>Assets with type <%= request.getAttribute("title") %></strong></caption>
                    <thead>
                    <tr>
                        <th>Tag</th>
                        <th>Name</th>
                        <th>Model</th>
                        <th>Serial</th>
                        <th>Value</th>
                        <th>Location</th>
                        <th>Modified</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% while(assets.next()) { %>
                    <tr>
                        <td><%= assets.getString("tag") %></td>
                        <td><%= assets.getString("asset_name") %></td>
                        <td><%= assets.getString("model") %></td>
                        <td><%= assets.getString("ser_no") %></td>
                        <td><%= assets.getString("value") %></td>
                        <td><%= assets.getString("location_name") %></td>
                        <td><%= assets.getString("modified") %></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <% } else if (request.getAttribute("table_format").equals("condition")) { %> 
                <table class="table table-striped table-bordered" id="assetQueueTable" cellpadding="0" cellspacing="0" border="0" style="background-color: white; max-width: 700px;">                    
                    <caption style="margin-top: -22px;"><strong>Assets with condition <%= request.getAttribute("title") %></strong></caption>
                    <thead>
                    <tr>
                        <th>Tag</th>
                        <th>Name</th>
                        <th>Value</th>
                        <th>Location</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% while(assets.next()) { %>
                    <tr>
                        <td><%= assets.getString("tag") %></td>
                        <td><%= assets.getString("asset_name") %></td>
                        <td><%= assets.getString("value") %></td>
                        <td><%= assets.getString("location_name") %></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <% } else if (request.getAttribute("table_format").equals("district")) { %> 
                <table class="table table-striped table-bordered" id="assetQueueTable" cellpadding="0" cellspacing="0" border="0" style="background-color: white; max-width: 700px;">                    
                    <caption style="margin-top: -22px;"><strong>Assets with district <%= request.getAttribute("title") %></strong></caption>
                    <thead>
                    <tr>
                        <th>Tag</th>
                        <th>Name</th>
                        <th>District</th>
                        <th>Location</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% while(assets.next()) { %>
                    <tr>
                        <td><%= assets.getString("tag") %></td>
                        <td><%= assets.getString("asset_name") %></td>
                        <td><%= assets.getString("district") %></td>
                        <td><%= assets.getString("location_name") %></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <% } %>
                <% } %> <!-- end if (assets != null) -->
            </div>
            <hr>
        </div> <!-- /container -->
        <!-- javascript -->
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/bootstrap-dropdown.js"></script>
        <script src="js/jquery.dataTables.min.js"></script>
        <script src="js/TableTools.min.js"></script>
        <script src="js/ZeroClipboard.js"></script>
        <script src="js/DT_bootstrap.js"></script>
        <script type="text/javascript">
            
            $.extend( $.fn.dataTableExt.oStdClasses, {
                "sWrapper": "dataTables_wrapper form-inline"
            } );       

        </script>
    </body>
</html>
