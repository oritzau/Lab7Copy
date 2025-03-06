<!-- badrequest -->
<% response.setStatus(400); %>
<html>

<head>
<title>Bad Request</title>
</head>

<body bgcolor="#f0a0a0">

<h1>Bad JSP Code Requested</h1>

<p>The jsp id supplied was not legal: <%= request.getParameter("jsprequested") %></p>

<h2>Method: <%=request.getMethod()%></h2>

</body>

</html>
