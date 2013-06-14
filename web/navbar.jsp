<%-- 
    Document		: navbar
    Created on		: Apr 18, 2013, 7:38:19 PM
    HTML Author         : Jimmy Cleveland
    JSTL                : Erik Hall
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="navbar">
	<div class="navbar-inner">
		<div class="container">
			<ul class="nav">
				<li <c:if test="${param.pageName == 'home'}">class="active"</c:if>><a href="home.jsp">Home</a></li>
				<li <c:if test="${param.pageName == 'assets'}">class="active"</c:if>><a href="assets">Assets</a></li>
				<li <c:if test="${param.pageName == 'groups'}">class="active"</c:if>><a href="groups.jsp">Groups</a></li>
				<li <c:if test="${param.pageName == 'locations'}">class="active"</c:if>><a href="locations">Locations</a></li>
				<li <c:if test="${param.pageName == 'relocate'}">class="active"</c:if>><a href="relocate.jsp">Relocate</a></li>
				<li <c:if test="${param.pageName == 'reports'}">class="active"</c:if>><a href="reports">Reports</a></li>
			</ul>
		</div>
	</div>
</div>
