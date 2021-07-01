<%@page language="java" contentType="text/html"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
    <head>
        <title>Booking Confirmation Page</title>
        <style type="text/css">
            th { text-align: left }
            #returningFlights tr:nth-child(2n) { background-color: cyan; }
        </style>
    </head>
    <body>
        <h2>Congratulations - you are booked as follows:</h2>
        <hr/>
        <h3>Outward Flight</h3>
        <table>
            <tr>
                <th>Origin</th>
                <td>${requestScope.chosenOut.origin}</td>
            </tr>
            <tr>
                <th>Destination</th>
                <td>${requestScope.chosenOut.destination}</td>
            </tr>
            <tr>
                <th>Date</th>
                <td>
                    <fmt:formatDate value="${requestScope.chosenOut.departure}" type="date" dateStyle="medium"/>
                </td>
            </tr>
            <tr>
                <th>Time</th>
                <td>
                    <fmt:formatDate value="${requestScope.chosenOut.departure}" type="time" dateStyle="medium"/>
                </td>
            </tr>
        </table>
        <hr/>
        <h3>Returning Flight</h3>
        <table>
            <tr>
                <th>Origin</th>
                <td>${requestScope.chosenBack.origin}</td>
            </tr>
            <tr>
                <th>Destination</th>
                <td>${requestScope.chosenBack.destination}</td>
            </tr>
            <tr>
                <th>Date</th>
                <td>
                    <fmt:formatDate value="${requestScope.chosenBack.departure}" type="date" dateStyle="medium"/>
                </td>
            </tr>
            <tr>
                <th>Time</th>
                <td>
                    <fmt:formatDate value="${requestScope.chosenBack.departure}" type="time" dateStyle="medium"/>
                </td>
            </tr>
          </table>
          <hr/>
          <p>
            <em><a href="/print">Print your booking details</a></em>
          </p>
    </body>
</html>
