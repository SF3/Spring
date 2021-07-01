<%@page language="java" contentType="text/html" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
    <head>
        <title>Select A Flight</title>
        <style type="text/css">
            th { text-align: left }
            tr:nth-child(2n) { background-color: cyan; }
        </style>
    </head>
    <body class="tundra">
        <h2>Available Flights Outwards</h2>
        <table rules="all">
            <tr>
                <th>Flight Number</th>
                <th>Departure Date</th>
                <th>Departure Time</th>
                <th/>
            </tr>
            <c:forEach var="flight" items="${requestScope.flightsOut}" varStatus="status">
                <tr>
                    <td>${flight.number}</td>
                    <td>
                        <fmt:formatDate value="${flight.departure}" type="date" dateStyle="medium"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${flight.departure}" type="time" dateStyle="medium"/>
                    </td>
                    <td>
                        <a href="/book/${flight.number}/out">Book</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>