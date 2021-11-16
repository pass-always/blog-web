<%--
  Created by IntelliJ IDEA.
  User: Keziah
  Date: 05-06-2020
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Blog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="${pageContext.request.contextPath}/https://fonts.googleapis.com/css?family=Roboto+Condensed:300,300i,400,400i,700i" rel="stylesheet">

</head>
<body>
<nav role='navigation' id="mainnav">
    <br><br><br>
    <ul class="gap">
        <li><a href="${pageContext.request.contextPath}/html/posts.html">HOME</a></li>
        <li><a href="${pageContext.request.contextPath}/html/login.html">LOGIN</a></li>
        <li><a href="${pageContext.request.contextPath}/html/register.html">REGISTER</a></li>
    </ul>
</nav>

<div class="hamb">
    <a href="#"><i class="fa fa-bars"></i></a>
</div>

<div class="hero">
    <h1>JAVASquad<br><span>BLOG</span></h1>
</div>

<canvas id="bubble"></canvas>

<!--JQuery-->

<script src="${pageContext.request.contextPath}/https://code.jquery.com/jquery-3.3.1.js"></script>
<script src="${pageContext.request.contextPath}/https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.3/jquery-ui.js"></script>

<!--Custom Script-->
<script src="${pageContext.request.contextPath}/javascript/mainpage.js"></script>

</body>
</html>
