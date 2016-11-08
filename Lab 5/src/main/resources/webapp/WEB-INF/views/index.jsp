<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>Task List</title>
</head>
<body>

<table>
    <c:forEach var="list" items="${lists}">
        <tr>
            <td>${list.getName()}</td>
        </tr>
    </c:forEach>
</table>

<h3>Add new List</h3>
<form:form modelAttribute="taskList" method="POST" action="/add-task-list">
    <table>
        <tr>
            <td><form:label path="name">Name:</form:label></td>
            <td><form:input path="name"/></td>
        </tr>
    </table>

    <input type="submit" value="add">
</form:form>
</body>
</html>
