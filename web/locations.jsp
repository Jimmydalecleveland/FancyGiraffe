<%-- 
    Document            : Locations page
    Created on          : Apr 18, 2013, 3:09:47 PM
    Java/JSON Author    : Erik Hall
    HTML/CSS/Javascript : Jimmy Cleveland
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="javax.sql.rowset.CachedRowSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Locations</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
	
        <!-- Le styles -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-responsive.css" rel="stylesheet">
        <link href="css/site.css" rel="stylesheet">
        
		
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
				<jsp:include page="navbar.jsp" >
					<jsp:param name="pageName" value="locations" />
				</jsp:include>
				<!-- /.navbar -->
            </div>
                     
            <div class="container" style="padding: 20px;">
                <h3>Manage Locations</h3>
                <div class="tabbable">
                    <!-- Tab Headings-->
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab1" data-toggle="tab">Add</a></li>
                        <li><a href="#tab2" data-toggle="tab">Edit</a></li>
                        <li><a href="#tab3" data-toggle="tab">Delete</a></li>
                    </ul> <!-- /Tab Headings-->
                    <!-- Tab Contents-->
                    <div class="tab-content">

                        <!-- Tab1 -->
                        <div class="tab-pane active" id="tab1">
                            <form class="form-horizontal" id="addForm" action="locations" method="POST">
                                <input type="hidden" name="action" value="add">
                                <div class="control-group">
                                    <label class="control-label" for="location">Location</label>
                                    <div class="controls">
                                        <input type="text" name="location" id="location" required>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="address">Address</label>
                                    <div class="controls">
                                        <input type="text" name="address" id="address">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="city">City</label>
                                    <div class="controls">
                                        <input type="text" name="city" id="city">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="state">State</label>
                                    <div class="controls">
                                        <input class="input-mini" type="text" name="state" id="state" placeholder="UT">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="zip">Zip</label>
                                    <div class="controls">
                                        <input class="input-mini" type="text" name="zip" id="zip">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="phone">Phone</label>
                                    <div class="controls">
                                        <input class="input-medium" type="text" name="phone" id="phone">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="district">District</label>
                                    <div class="controls">
                                        <input type="text" name="district" id="district">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" class="btn btn-success">Add Location</button>                                        
                                    </div><br />
									<% Object newLocationId = request.getAttribute("newLocationId");
										if (newLocationId != null && !newLocationId.toString().equals("-1")) { %>
									<div id="locationResult" class="alert alert-info">
										<strong>Success!</strong> Location added.
                                    </div> <% } else if (newLocationId != null) { %>
									<div id="locationResult" class="alert alert-info">
										<strong>Error!</strong> Couldn't add location.
                                    </div>
									<% } %>
                                </div>
                            </form>
                        </div>

                        <!-- Tab 2-->
                        <div class="tab-pane" id="tab2">
                            <form class="form-horizontal">
                                <div class="control-group">
                                    <label class="control-label" for="location">Location</label>
                                    <div class="controls">
                                        <select id="editSelect">
					<% 
						CachedRowSet locations = (CachedRowSet) request.getAttribute("locations");
						//locations.beforeFirst();
						while(locations.next()) { %>
						<option><%= locations.getString("location_name") %></option>
					<% } %>
                                        </select>
                                    </div>
                                </div>
                            </form>
                            <br />
                            <form class="form-horizontal" action="locations" id="editF" method="post"> 
								<input type="hidden" name="action" value="edit">
								<input type="hidden" name="location" id="editlocation">
                                <div class="control-group">
                                    <label class="control-label" for="address">Address</label>
                                    <div class="controls" id="editForm" >
                                        <input type="text" name="address" id="editaddress">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="city">City</label>
                                    <div class="controls">
                                        <input type="text" name="city" id="editcity">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="state">State</label>
                                    <div class="controls">
                                        <input class="input-mini" type="text" name="state" id="editstate" placeholder="UT">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="zip">Zip</label>
                                    <div class="controls">
                                        <input class="input-mini" type="text" name="zip" id="editzip">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="phone">Phone</label>
                                    <div class="controls">
                                        <input class="input-medium" type="text" name="phone" id="editphone">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="district">District</label>
                                    <div class="controls">
                                        <input type="text" name="district" id="editdistrict">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" class="btn btn-success">Submit Changes</button>
                                    </div>
                                </div>
								<% Object editRowsAffected = request.getAttribute("editRowsAffected");
										if (editRowsAffected != null && !editRowsAffected.toString().equals("-1")) { %>
									<div id="locationResult" class="alert alert-info">
										<strong>Success!</strong> Updated location.
                                    </div> <% } else if (editRowsAffected != null) { %>
									<div id="locationResult" class="alert alert-info">
										<strong>Error!</strong> Couldn't update location.
                                    </div>
									<% } %>
                            </form>
                        </div>
						<!-- Tab 3 -->
                        <div class="tab-pane" id="tab3">
                            <form class="form-horizontal">
                                <div class="control-group">
                                    <label class="control-label" for="location">Location</label>
                                    <div class="controls">
                                        <select id="delSelect">
                                            <% 
						locations.beforeFirst();//go back to first row
						while(locations.next()) { %>
						<option ><%= locations.getString("location_name") %></option>
                                            <% } %>
                                        </select>
                                    </div>
                                </div>
                            </form>
                            <br />
                            <form class="form-horizontal" action="locations" method="post">
								<input type="hidden" name="action" value="delete">
								<input type="hidden" name="location" id="dellocation">
                                <div class="control-group">
                                    <label class="control-label" for="address">Address</label>
                                    <div class="controls">
                                        <span class="input uneditable-input" id="deladdress">3927 S. 200 W.</span>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="city">City</label>
                                    <div class="controls">
                                        <span class="input uneditable-input" id="delcity">Ogden</span>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="state">State</label>
                                    <div class="controls">
                                        <span class="input-mini uneditable-input" id="delstate">UT</span>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="zip">Zip</label>
                                    <div class="controls">
                                        <span class="input-mini uneditable-input" id="delzip">81234</span>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="phone">Phone</label>
                                    <div class="controls">
                                        <span class="input-medium uneditable-input" id="delphone">8011234567</span>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="district">District</label>
                                    <div class="controls">
                                        <span class="input uneditable-input" id="deldistrict">Precinct 12 Ogden</span>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" class="btn btn-danger">Delete Location</button>
                                    </div>
                                </div>
								<% Object deleteRowsAffected = request.getAttribute("deleteRowsAffected");
										if (deleteRowsAffected != null && !deleteRowsAffected.toString().equals("-1")) { %>
									<div id="locationResult" class="alert alert-info">
										<strong>Success!</strong> Deleted location.
                                    </div> <% } else if (deleteRowsAffected != null) { %>
									<div id="locationResult" class="alert alert-info">
										<strong>Error!</strong> Couldn't delete location.
                                    </div>
									<% } %>
                            </form>
                        </div>
                    </div> <!-- /Tab Contents-->
                </div>
            </div>

            <hr>

        </div> <!-- /container -->
        <!-- Le javascript -->
        <!-- Placed at the end of the document so the pages load faster -->
        
        <!-- JQuery -->
        <script src="http://code.jquery.com/jquery.js"></script>
            <!-- JQuery Validate Plugin -->
            <script src="js/jquery.validate.min.js"></script>
            <!-- JQuery Additional Validation Plugin (Used for PhoneUS and others) -->
            <script src="js/additional-methods.js"></script>
            
        <!-- Bootstrap -->
        <script src="js/bootstrap.min.js"></script>
		
            <script type="text/javascript">
             // Validation
                $(document).ready(function() {
                    $('#addForm').validate(
                    {
                        rules: {
                            location: {
                                minlength: 4,
                                required: true
                            },
                            district: {
                                minlength: 2,
                                required: false
                            },
                            phone: {
                                phoneUS: true,
                                required: false
                            }
                        },
                        highlight: function(element) {
                            $(element).closest('.control-group').removeClass('success').addClass('error');
                        },
                        success: function(element) {
                            element
                            .closest('.control-group').removeClass('error').addClass('success');
                        }        
                        
                    });
                }); // /document.ready
			
            </script>
		<script type="text/javascript">
		//<![CDATA[
		
				
			$(function() {

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
	

				//Fill form in delete tab
				function fill_del_form() {

					var selected = $('#delSelect').find(":selected").text();

					$.ajax({
						data: "", 
						dataType: 'json',
						contentType: 'application/json',
						type: "POST", 
						url: "locationJSONServlet?name=" + selected,
						success:function (data, textStatus, jqXHR) 
						{
								$('#dellocation').val(checkNull(data.location_name));
								$('#deladdress').html(checkNull(data.address));
								$('#delcity').html(checkNull(data.city));
								$('#delstate').html(checkNull(data.state));
								$('#delzip').html(checkNull(data.zip));
								$('#delphone').html(checkNull(data.phone));
								$('#deldistrict').html(checkNull(data.district));
										
						},
						error:function (xhr, ajaxOptions, thrownError){
							//alert(xhr.statusText);
							//alert(thrownError);
						} 
					});
			}
			
			//Fill form in edit tab
			function fill_edit_form() {

					var selected = $('#editSelect').find(":selected").text();
					
					$.ajax({
						data: "", 
						dataType: 'json',
						contentType: 'application/json',
						type: "POST", 
						url: "locationJSONServlet?name=" + selected,
						success:function (data, textStatus, jqXHR) 
						{

							$('#editlocation').val(checkNull(data.location_name));
							$('#editaddress').val(checkNull(data.address));
							$('#editcity').val(checkNull(data.city));
							$('#editstate').val(checkNull(data.state));
							$('#editzip').val(checkNull(data.zip));
							$('#editphone').val(checkNull(data.phone));
							$('#editdistrict').val(checkNull(data.district));								
												
						},
						error:function (xhr, ajaxOptions, thrownError){
							//alert(xhr.statusText);
							//alert(thrownError);
						} 
					});
			}
			
			function checkNull(s) {
				return (s === "null") ? "" : s;
			}
		
			//run when page loads
			fill_del_form();
			fill_edit_form();
		
			//run on change
			$("#delSelect").change(fill_del_form);
			$("#editSelect").change(fill_edit_form);
		
		});
		//]]>
		</script>		
    </body>
</html>

