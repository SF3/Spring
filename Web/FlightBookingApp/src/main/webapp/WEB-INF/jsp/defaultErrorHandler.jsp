<%@ page language="java" isErrorPage="true" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
	<head>
		<title>Default Error Handling Page</title>
	</head>
	<body>
		<h1>Default Error Handling Page</h1>
		<h2>An unexpected exception occurred - sorry!</h2>
        <h3>Stack Trace</h3>
		<ol>
                <%
                        StackTraceElement[] frames = exception.getStackTrace();
                        for(int i=0;i<frames.length;i++) {
                %>
                                <li><%= frames[i] %></li>
                <%	}  %>
		</ol>
	</body>
</html>