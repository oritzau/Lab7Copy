<!-- provider_details -->
<%@ page import = "edu.skidmore.cs276.lab07.webapps.web.ControllerServlet" %>
<%@ page import = "edu.skidmore.cs276.lab07.beans.medical.Provider" %>
	
<%
	Provider provider = (Provider)request.getAttribute(ControllerServlet.REQUEST_ATTRIB_PROVIDER_DETAILS);
%>
	
<html>

	<head>
		<title>Provider Details: Provider #<%=provider.getId()%></title>
	</head>
	
	<body bgcolor="#a0f0a0">
		<h1>Provider Details for <%=provider.getLastName()%>, <%=provider.getFirstName()%></h1>
		
		<table border="1">
			<tr><th>Id</th><td><%=provider.getId()%></td>
			<tr><th>NPI</th><td><%=provider.getNpi()%></td>
			<tr><th>City</th><td><%=provider.getCity()%></td>
			<tr><th>State</th><td><%=provider.getState()%></td>
			<tr><th>Zip</th><td><%=provider.getZip()%></td>
			<tr><th>Speciality</th><td><%=provider.getSpeciality()%></td>
			<tr><th>Facility</th><td><%=provider.getFacility()%></td>
			
		</table>
		<br/>
		<a href="<%=request.getContextPath()%>/">Search Form</a>
	</body>
</html>
