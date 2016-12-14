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
            <c:forEach var="task" items="${list.getTasks()}">
                <form:form modelAttribute="updateTask" method="POST" action="/update-task">
                    <tr>
                        <td></td>
                        <td>${task.getName()}</td>
                        <td>${task.isDone() ? "Done" : "Not done"}</td>
                        <td><input type="submit" name="action" value="Change Status"></td>
                        <td><input type="submit" name="action" value="Remove Task"></td>
                        <td><form:hidden path="id" value="${task.getId()}"/></td>
                        <td><form:hidden path="listId" value="${list.getId()}"/></td>
                    </tr>
                </form:form>
            </c:forEach>
            <tr style="height: 15px"></tr>
            <form:form modelAttribute="task" method="POST" action="/add-task">
                <tr>
                    <td></td>
                    <td><form:label path="name">New Task Name:</form:label></td>
                    <td><form:input path="name"/></td>
                    <td><input type="submit" value="add"></td>
                    <td><form:hidden path="listId" value="${list.getId()}"/></td>
                </tr>
            </form:form>
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
