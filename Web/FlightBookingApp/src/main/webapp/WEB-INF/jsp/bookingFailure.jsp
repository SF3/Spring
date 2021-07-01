<%@page language="java" contentType="text/html"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Booking Failure Page</title>
	</head>
	<body>
		<h2>Sorry - the following booking did not succeed:</h2>
		<h3>Leaving ${requestScope.chosenOut.origin} for ${requestScope.chosenOut.destination} on ${requestScope.chosenOut.departure}</h3>
		<h3>Returning from ${requestScope.chosenBack.origin} for ${requestScope.chosenBack.destination} on ${requestScope.chosenBack.departure}</h3>
	</body>
</html>