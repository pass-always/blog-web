<%--@Ethan--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>articles list page</title>
</head>
<body>

<table border = "1">
    <h1>Articles List</h1>
    <tr>
        <th>Article ID </th>
        <th>User ID</th>
        <th>Article Title</th>
        <th>Article Keyword</th>
        <th>Article Abstract</th>
        <th>Article PostTime</th>
    </tr>
    <c:forEach var="articles" items="${articles}">
    <tr>
        <td>${items.articleId}</td>
        <td>${items.userID}</td>
        <td>${items.articleTitle}</td>
        <td>${items.articleKeyword}</td>
        <td>${items.articleAbstract}</td>
        <td>${items.articlePostTime}</td>
    </tr>
    </c:forEach>
</body>
</html>
