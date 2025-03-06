<!-- search_criteria -->
<%@ page import = "edu.skidmore.cs276.lab07.webapps.web.ControllerServlet" %>

	
<html>

	<head>
		<title>Search Form</title>
	</head>
	
	<body bgcolor="#a0a0f0">
		<h1>Search for a Provider</h1>
		<form method="POST" action="<%=request.getContextPath()%>/">
			<table border="1">
				<tr><td>Speciality</td><td>
<select name="<%=ControllerServlet.REQUEST_PROVIDER_SPECIALTY%>">
<% 
	String[] specialities = (String[])request.getAttribute(ControllerServlet.REQUEST_ATTRIB_PROVIDER_SPECIALITIES);
	boolean first = false;
	for (String speciality : specialities) {
%>
		<option<% if (first) { %> selected<% } %>><%=speciality%></option>
<%
	}
%>
</select>
</td></tr>
				<tr><td>Last Name</td><td><input type="text" name="<%=ControllerServlet.REQUEST_PROVIDER_LNAME%>"/></td></tr>
				<tr><td>First Name</td><td><input type="text" name="<%=ControllerServlet.REQUEST_PROVIDER_FNAME%>"/></td></tr>
				<tr><td colspan="2"><input type="submit" value="Search"/></td></tr>
			</table>
		</form>			
	</body>
</html>
