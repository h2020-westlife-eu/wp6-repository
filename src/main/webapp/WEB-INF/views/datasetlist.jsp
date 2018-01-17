<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Users List</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Dataset </span></div>
        <div class="tablecontainer">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Project Id</th>
                    <th>Summary</th>
                    <th>UserId</th>
                    <th>Dataset Name</th>
                    <th>Dataset Summary</th>
                    <th width="100"></th>
                    <th width="100"></th>
                </tr>
                </thead>
                <tbody>
                 <c:forEach items="${dataset}" var="dataset">
                    <tr>
                        <td>${proid}</td>
                        <td>${dataset.dataName}</td>
                        <tf>${dataset.summary}</tf>
                        <td><a href="<c:url value='/add-file-${dataset.id}' />" class="btn btn-success custom-width">Add File</a></td>
                        <td><a href="<c:url value='/edit-dataset-${dataset.id}' />" class="btn btn-success custom-width">edit</a></td>
                        <td><a href="<c:url value='/delete-dataset-${dataset.id}' />" class="btn btn-danger custom-width">delete</a></td>

                     </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="well">
        <a href="<c:url value='//add-dataset-${proid}' />">Add New Dataset to project number ${proid}</a>
    </div>
</div>
</body>
</html>