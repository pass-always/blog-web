<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <title>Dashboard</title>

    <!--Font--->
    <script src="https://kit.fontawesome.com/c5aba5c557.js" crossorigin="anonymous"></script>

    <!--Google Fonts-->
    <link href=https://fonts.googleapis.com/css2?family=Candal&family=Lora:ital@1&display=swap" rel="stylesheet">
    <link href=https://fonts.googleapis.com/css?family=Roboto+Condensed:300,300i,400,400i,700i" rel="stylesheet">

    <!--CSS Stylesheet-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">


</head>

<body>

<div class="row">
    <div class="container">
        <table class="table table-bordered">
            <thead>
                <th>Title</th>
                <th colspan="2">Actions</th>
            </thead>
            <tbody>
                <c:forEach var="article" items="${articles}">
                    <tr>
                        <td>
                            <a href="./article?articleId=${article.getArticleId()}" methods="post" class="title">${article.getArticleTitle()}</a></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/edit-post?articleId=${article.getArticleId()}" methods="get"><i class="fas fa-edit edit"></i></a></td>

                            <td>
                        <form action="${pageContext.request.contextPath}/delete-article" method="post">

                            <input type="hidden" name="articleId" value="${article.getArticleId()}">
                            <button type="submit" style="all: unset"><i class="fas fa-trash-alt delete"></i></button>
                        </form>
                            </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
