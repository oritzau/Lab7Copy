<!-- provider_list -->
<%@ page import = "java.util.List" %>
<%@ page import = "edu.skidmore.cs276.lab07.webapps.web.ControllerServlet" %>
<%@ page import = "edu.skidmore.cs276.lab07.beans.medical.Provider" %>

<%
	@SuppressWarnings("unchecked")
	List<Provider> providerList = (List<Provider>)request.getAttribute(ControllerServlet.REQUEST_ATTRIB_PROVIDER_LIST);
%>
	
<html>

	<head>
		<title>Providers Found</title>
	</head>
	
	<body bgcolor="#a0f0f0">
		<h1>Providers Found</h1>
								
		<% if (providerList.size() > 0) { %>
			<table border="1">
			<tr><th>NPI</th><th>Name</th><th>State</th></tr>
			<% for (Provider provider : providerList) { %>
				<tr>
					<td><%=provider.getNpi()%></td>
					<td><%=provider.getLastName()%>, <%=provider.getFirstName()%></td>
					<td><%=provider.getState()%></td>
					<td><form method="POST" action="<%=request.getContextPath()%>/">
						<input type="hidden" name="<%=ControllerServlet.REQUEST_PROVIDER_ID%>" value="<%=provider.getId()%>"/>
						<input type="submit" value="View Details"/></form></td>
					
				</tr>
			<% } %>
			</table>
		<% } else { %>
			<font size="+1">No providers match the supplied criteria</font>
		<% } %>
		
		<hr/>
		<strong><%=request.getSession().getId()%></strong>
				
	</body>
</html>
