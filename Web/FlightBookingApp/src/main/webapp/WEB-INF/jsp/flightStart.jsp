<%@page language="java" contentType="text/html"%>
<%@taglib prefix="spring_form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>Start of Spring Flights Application</title>
    </head>
    <body>
        <h2>Choose Your Flight</h2>
        <spring_form:form modelAttribute="data" action="/search" method="post">
            <c:forEach var="error" items="${requestScope.flightsErrors.allErrors}" varStatus="status">
                <p style="color:red;">${error.defaultMessage}</p>
            </c:forEach>
            <table>
                <tr>
                    <td>From:</td>
                    <td>
                        <select name="origin">
                            <option value="BFS" ${requestScope.data.origin == "BFS" ? "selected" : ""}>Belfast</option>
                            <option value="DUB" ${requestScope.data.origin == "DUB" ? "selected" : ""}>Dublin</option>
                            <option value="LON" ${requestScope.data.origin == "LON" ? "selected" : ""}>London</option>
                            <option value="JFK" ${requestScope.data.origin == "JFK" ? "selected" : ""}>Washington</option>
                            <option value="CHO" ${requestScope.data.origin == "CHO" ? "selected" : ""}>Chicago</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>To:</td>
                    <td>
                        <select name="destination">
                            <option value="BFS" ${requestScope.data.destination == "BFS" ? "selected" : ""}>Belfast</option>
                            <option value="DUB" ${requestScope.data.destination == "DUB" ? "selected" : ""}>Dublin</option>
                            <option value="LON" ${requestScope.data.destination == "LON" ? "selected" : ""}>London</option>
                            <option value="JFK" ${requestScope.data.destination == "JFK" ? "selected" : ""}>Washington</option>
                            <option value="CHO" ${requestScope.data.destination == "CHO" ? "selected" : ""}>Chicago</option>
                        </select>
                    </td>
                </tr>
            </table>
            <p>
                Departing:<br/>
                <input type="date" name="dateOfDeparture" id="dateOfDeparture" value="${requestScope.data.dateOfDeparture}" size="11"/>
            <p>
		        Returning:<br/>
                <input type="date" name="dateOfReturn" id="dateOfReturn"  value="${requestScope.data.dateOfReturn}" size="11"/>
            </p>
            <p>
                <input type="submit" value="View Selected Flights"/>
            </p>
        </spring_form:form>
    </body>
</html>
