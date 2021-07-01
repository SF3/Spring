<%@page language="java" contentType="text/html" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
    <head>
        <title>Select A Flight</title>
        <style type="text/css">
            th { text-align: left }
            #returningFlights tr:nth-child(2n) { background-color: cyan; }
        </style>
    </head>
    <body>
        <table>
            <tr>
                <th colspan="2">Outward Flight</th>
            </tr>
            <tr>
                <th>Origin</th>
                <td>${sessionScope.chosenOut.origin}</td>
            </tr>
            <tr>
                <th>Destination</th>
                <td>${sessionScope.chosenOut.destination}</td>
            </tr>
            <tr>
                <th>Date</th>
                <td>
                    <fmt:formatDate value="${sessionScope.chosenOut.departure}" type="date" dateStyle="medium"/>
            </td>
            </tr>
            <tr>
                <th>Time</th>
                <td>
                    <fmt:formatDate value="${sessionScope.chosenOut.departure}" type="time" dateStyle="medium"/>
                </td>
            </tr>
        </table>
        <hr/>
        <h2>Available Flights Returning</h2>
        <table id="returningFlights" rules="all">
            <tr>
                <th>Flight Number</th>
                <th>Departure Date</th>
                <th>Departure Time</th>
                <th/>
            </tr>
            <c:forEach var="flight" items="${sessionScope.flightsBack}" varStatus="status">
                <tr>
                    <td>${flight.number}</td>
                    <td>
                        <fmt:formatDate value="${flight.departure}" type="date" dateStyle="medium"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${flight.departure}" type="time" dateStyle="medium"/>
                    </td>
                    <td>
                        <a href="/book/${flight.number}/back">Book</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
