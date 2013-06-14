<%-- 
    Document   : Assets page
    Created on : Apr 22, 2013, 6:52:16 PM
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@page import="javax.sql.rowset.CachedRowSet"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Assets</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Le styles -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/site.css" rel="stylesheet">
        <link href="css/bootstrap-responsive.css" rel="stylesheet">
        <style type="text/css">
            .form-horizontal input
            {
                verticle-align: top;
            }
        </style>

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
                    <jsp:param name="pageName" value="assets" />
                </jsp:include>
                <!-- /.navbar -->
            </div>

            <div class="container" style="margin-top: 30px; padding: 20px;">
                <h3>Manage Assets</h3>
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
                            <form class="form-horizontal" id="addForm" action="assets" method="POST">
                                <input type="hidden" name="action" value="add">
                                <div class="span7" style="margin-left: -30px;">
                                    <div class="control-group">
                                        <label class="control-label" for="description">Description</label>
                                        <div class="controls">
                                            <input type="text" name="description" id="description">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="model">Model</label>
                                        <div class="controls">
                                            <input type="text" name="model" id="model">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="sn">Serial Number</label>
                                        <div class="controls">
                                            <input type="text" name="sn" id="sn">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="type">Asset Type</label>
                                        <div class="controls">
                                            <select id="typeSelect" name="typeDD" style="width: 112px;">
                                                <% 
                                                        CachedRowSet types = (CachedRowSet) request.getAttribute("types");
                                                        //locations.beforeFirst();
                                                        while(types.next()) { %>
                                                        <option><%= types.getString("asset_type") %></option>
                                                <% } %>
                                                <option>Other</option>
                                            </select>
                                            <input class="input-small typePopover" name="typeL" type="text" name="type" id="type" placeholder="select other" disabled>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="value">Value</label>
                                        <div class="controls">
                                            <div class="input-prepend">
                                                <span class="add-on">$</span>
                                                <input class="span1" type="text" name="value" id="value" style="vertical-align: top;">                                                
                                            </div>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="location">Location</label>
                                        <div class="controls">
                                            <select name="location" >
                                                <% 
                                                        CachedRowSet locations = (CachedRowSet) request.getAttribute("locations");
                                                        //locations.beforeFirst();
                                                        while(locations.next()) { %>
                                                        <option><%= locations.getString("location_name") %></option>
                                                <% } %>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="condition">Condition</label>
                                        <div class="controls">
                                            <select id="conditionSelect" name="conditionDD" style="width: 112px;">                                                
                                               <% 
                                                        CachedRowSet conditions = (CachedRowSet) request.getAttribute("conditions");
                                                        //conditions.beforeFirst();
                                                        while(conditions.next()) { %>
                                                        <option><%= conditions.getString("asset_condition") %></option>
                                                <% } %>
                                                <option>Other</option>
                                            </select>
                                            <input class="input-small conditionPopover" type="text" name="conditionL" id="condition" placeholder="select other" disabled>
                                        </div>                                        
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="assetTag">Asset Tag</label>
                                        <div class="controls">
                                            <div class="input-prepend">
                                                <span class="add-on"><i class="icon-barcode"></i></span>
                                                <input class="input-medium" type="text" name="assetTag" id="assetTag" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="controls">
                                            <button type="submit" class="btn btn-success">Add Asset</button>
                                        </div>
                                    </div>
									<!--Alert for add asset form-->
									<% Object id = request.getAttribute("id");
									if (id != null && !id.toString().equals("-1")) { %>
									<div id="locationResult" class="alert alert-info" style="margin-left: 30px;">
										<strong>Success!</strong> Asset created.
									</div> <% } else if (id != null) { %>
									<div id="locationResult" class="alert alert-info" style="margin-left: 30px;">
										<strong>Error!</strong> Couldn't add asset.
									</div>
									<!--/Alert for add asset form-->
									<% } %>
                                </div>
                                <div class="span3">
                                    <textarea rows="10" placeholder="Notes go here..." name="notes" style="max-height: 300px; max-width: 300px;"></textarea>
                                </div>
                            </form>
                        </div>

                                                
                        <!-- Tab 2-->
                        <div class="tab-pane" id="tab2">
                            <form class="form-horizontal" id="editInput">
                                <input type="hidden" name="action" value="edit">
                                <div class="span5">
                                    <div class="control-group">
                                        <label class="control-label">Asset Tag</label>
                                        <div class="controls">
                                            <div class="input-prepend">
                                                <span class="add-on"><i class="icon-barcode"></i></span>
                                                <input class="input-medium" type="text" name="assetTag" id="editInputAssetTag">
												<input type="submit" style="display: none;" />
                                            </div>
                                        </div>
                                    </div>
							</form>
							<form class="form-horizontal" id="editForm" action="assets" method="POST">
                                <input type="hidden" name="assetTag" id="editTag" />
								<input type="hidden" name="action" value="edit">
                                    <br />
                                    <div class="control-group">
                                        <label class="control-label" for="description">Description</label>
                                        <div class="controls">
                                            <input type="text" name="description" id="editDescription">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="model">Model</label>
                                        <div class="controls">
                                            <input type="text" name="model" id="editModel">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="sn">Serial Number</label>
                                        <div class="controls">
                                            <input type="text" name="sn" id="editSn">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="type">Asset Type</label>
                                        <div class="controls">
                                            <input type="text" name="type" id="editType">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="value">Value</label>
                                        <div class="controls">
                                            <div class="input-prepend">
                                                <span class="add-on">$</span>
                                                <input class="span1" name="value" type="text" id="editValue">'                                                
                                            </div>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="location">Location</label>
                                        <div class="controls">
                                            <select name="location" id="editLocation" class="input-middle">
                                            <% 
						locations.beforeFirst();//go back to first row
						while(locations.next()) { %>
						<option ><%= locations.getString("location_name") %></option>
                                            <% } %>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="condition">Condition</label>
                                        <div class="controls">
                                            <input type="text" name="condition" id="editCondition">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="controls">
                                            <button type="submit" class="btn btn-success">Submit Changes</button>
                                        </div>
                                    </div>
                                <!--Alert for edit asset form-->
                                <% Object editRowsAffected = request.getAttribute("editRowsAffected");
                                if (editRowsAffected != null && !editRowsAffected.toString().equals("-1")) { %>
                                <div id="locationResult" class="alert alert-info">
                                        <strong>Success!</strong> Asset updated.
                                </div> <% } else if (editRowsAffected != null) { %>
                                <div id="locationResult" class="alert alert-info">
                                        <strong>Error!</strong> Couldn't edit asset.
                                </div>
                                <% } %>
                                <!--/Alert for edit asset form-->
                                </div>
                                <div class="span3">
                                    <textarea rows="10" name="notes" id="editNotes" placeholder="Notes go here..."></textarea>
                                </div>
							</form>
						</div>
								            
                                            
                        <!-- Tab 3-->
                        <div class="tab-pane" id="tab3">
                            <form class="form-horizontal" id="deleteInput">
                                <div class="span5">
                                    <div class="control-group">
                                        <label class="control-label">Asset Tag</label>
                                        <div class="controls">
                                            <div class="input-prepend">
                                            <span class="add-on"><i class="icon-barcode"></i></span>
                                            <input class="input-medium" type="text" name="assetTag" id="deleteInputAssetTag">
											<input type="submit" style="display:none;" />
                                            </div>
                                        </div>
                                    </div>
							</form>
							<form class="form-horizontal" id="deleteForm" action="assets" method="POST">
                                <input type="hidden" name="assetTag" id="deleteTag" />
								<input type="hidden" name="action" value="delete">
                                    <br />
                                    <div class="control-group">
                                        <label class="control-label">Description</label>
                                        <div class="controls">
                                            <span class="input uneditable-input" id="deleteDescription"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Model</label>
                                        <div class="controls">
                                            <span class="input uneditable-input" id="deleteModel"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Serial Number</label>
                                        <div class="controls">
                                            <span class="input uneditable-input" id="deleteSn"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Asset Type</label>
                                        <div class="controls">
                                            <span class="input uneditable-input" id="deleteType"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Value</label>
                                        <div class="controls">
                                            <div class="input-prepend">
                                                <span class="add-on">$</span>
                                                <span class="input span1 uneditable-input" id="deleteValue"></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Location</label>
                                        <div class="controls">
                                            <span class="input uneditable-input" id="deleteLocation"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Condition</label>
                                        <div class="controls">
                                            <span class="input uneditable-input" id="deleteCondition"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="controls">
                                            <button type="submit" class="btn btn-danger">Delete Asset</button>
                                        </div>
                                    </div>
									<!--Alert for delete asset form-->
									<% Object deleteRowsAffected = request.getAttribute("deleteRowsAffected");
									if (deleteRowsAffected != null && !deleteRowsAffected.toString().equals("-1")) { %>
									<div id="locationResult" class="alert alert-info">
										<strong>Success!</strong> Asset removed.
									</div> <% } else if (deleteRowsAffected != null) { %>
									<div id="locationResult" class="alert alert-info">
										<strong>Error!</strong> Couldn't remove asset.
									</div>
									<% } %>
									<!--/Alert for delete asset form-->
                                </div>
                                <div class="span3">
                                    <textarea rows="10" placeholder="Notes go here..." id="deleteNotes"></textarea>
                                </div>
                            </form>							
                        </div>
                    </div> <!-- /Tab Contents-->
                </div>
            </div>

            <hr>

        </div> <!-- /container -->
        
        <!-- Le javascript -->

        <!-- JQuery -->
        <script src="http://code.jquery.com/jquery.js"></script>
            <!-- JQuery Validate Plugin -->
            <script src="js/jquery.validate.min.js"></script>
            
        <!-- Bootstrap -->
        <script src="js/bootstrap.min.js"></script>
        
        <script type="text/javascript">
             // Validation
                $(document).ready(function() {
					
					$('#addForm').validate(
                    {
                        rules: {
                            description: {
                                minlength: 3,
                                required: true
                            },
                            value: {
                                number: true                             
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
                    
                     $('#conditionSelect').on("change", function() {
                        if($(this).val() == 'Other')
                        {
                            $('#condition').attr("disabled", false).focus();                            
                        }
                        else
                        {
                            $('#condition').attr("disabled", true).val("");
                        }
                    });
                    
                    $('#typeSelect').on("change", function() {
                        if($(this).val() == 'Other')
                        {
                            $('#type').attr("disabled", false).focus();                            
                        }
                        else
                        {
                            $('#type').attr("disabled", true).val("");
                        }
                    });
					
					
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
					
					//Assign click events for ajax functions
					$('#editInput').submit(fill_edit_form);
					$('#deleteInput').submit(fill_del_form);
                }); // /document.ready
			
                        $('.typePopover').popover({
                        title: 'New Asset Type',
                        content: 'If the type you desire does not exist please enter it here.',
                        trigger: 'focus',
                        placement: 'right'
                      });
                      $('.conditionPopover').popover({
                        title: 'New Asset Condition',
                        content: 'If the condition you desire does not exist please enter it here.',
                        trigger: 'focus',
                        placement: 'right'
                      });
					  
				
				//if s is "null" returns "". used by ajax form populating functions
				function checkNull(s) {
					return (s === "null") ? "" : s;
				}
					  
				//
				//AJAX functions	  
				//
				//Fill form in edit tab
				function fill_edit_form() {
					var selected = $('#editInputAssetTag').val();
					$.ajax({
						data: "", 
						dataType: 'json',
						contentType: 'application/json',
						type: "POST", 
						url: "assetJSONServlet?name=" + selected,
						success:function (data, textStatus, jqXHR) 
						{	//populate form values
							$('#editTag').val(selected);
							$('#editDescription').val(checkNull(data.asset_name));
							$('#editModel').val(checkNull(data.model));
							$('#editSn').val(checkNull(data.ser_no));
							$('#editType').val(checkNull(data.asset_type));
							$('#editValue').val(checkNull(data.value));
							$('#editLocation').val(checkNull(data.location_name));
							$('#editCondition').val(checkNull(data.asset_condition));
							$('#editNotes').val(checkNull(data.text));
						},
						error:function (xhr, ajaxOptions, thrownError){
							alert(xhr.statusText);
							alert(thrownError);
						} 
					});
					return false;//So the page doesn't reload
				}
					
				//Fill form in delete tab
				function fill_del_form() {

					var selected = $('#deleteInputAssetTag').val();

					$.ajax({
						data: "", 
						dataType: 'json',
						contentType: 'application/json',
						type: "POST", 
						url: "assetJSONServlet?name=" + selected,
						success:function (data, textStatus, jqXHR) 
						{	//populate form values
							$('#deleteTag').val(selected);
							$('#deleteDescription').html(checkNull(data.asset_name));
							$('#deleteModel').html(checkNull(data.model));
							$('#deleteSn').html(checkNull(data.ser_no));
							$('#deleteType').html(checkNull(data.asset_type));
							$('#deleteValue').html(checkNull(data.value));
							$('#deleteLocation').html(checkNull(data.location_name));
							$('#deleteCondition').html(checkNull(data.asset_condition));
							$('#deleteNotes').html(checkNull(data.text));										
						},
						error:function (xhr, ajaxOptions, thrownError){
							alert(xhr.statusText);
							alert(thrownError);
						} 
					});
					return false;//So the page doesn't reload
				}
           </script>

    </body>
</html>
