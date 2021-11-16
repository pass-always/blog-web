<%--
  Created by IntelliJ IDEA.
  User: Keziah
  Date: 17-06-2020
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Blog</title>

    <!--Font--->
    <script src="https://kit.fontawesome.com/c5aba5c557.js" crossorigin="anonymous"></script>

    <!--Google Fonts-->
    <link href="https://fonts.googleapis.com/css2?family=Candal&family=Lora:ital@1&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Roboto+Condensed:300,300i,400,400i,700i" rel="stylesheet">

    <!--CSS Stylesheet-->
    <!-- <link rel="stylesheet" href="../css/style.css">-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Article.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


</head>
<body>

<jsp:include page="/common/nav.jsp"/>


<div class="blog">
    <div class="date">April 6, 2020</div>
    <h2>Viverra justo nec ultrices dui
    </h2>

    <p class="paragraph">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Consectetur adipiscing elit duis tristique sollicitudin nibh sit. Et netus et malesuada fames ac turpis egestas integer eget. Consequat ac felis donec et odio. Vitae suscipit tellus mauris a diam maecenas. Ante in nibh mauris cursus mattis. Turpis egestas pretium aenean pharetra. Pretium viverra suspendisse potenti nullam ac tortor vitae. Est ante in nibh mauris cursus mattis. Arcu cursus vitae congue mauris. Turpis egestas sed tempus urna et pharetra. Pulvinar elementum integer enim neque volutpat ac tincidunt vitae semper. Mi proin sed libero enim sed. Purus sit amet volutpat consequat mauris nunc congue nisi. Ut sem nulla pharetra diam. Adipiscing diam donec adipiscing tristique risus nec. Neque vitae tempus quam pellentesque nec nam. Mi ipsum faucibus vitae aliquet nec ullamcorper. Consequat semper viverra nam libero justo laoreet sit.</p>

    <p class="paragraph">Pharetra massa massa ultricies mi quis hendrerit dolor. Pulvinar elementum integer enim neque volutpat ac tincidunt. Adipiscing at in tellus integer. Fermentum iaculis eu non diam phasellus. Eget nulla facilisi etiam dignissim. Habitant morbi tristique senectus et. Convallis a cras semper auctor neque vitae tempus quam pellentesque. Libero enim sed faucibus turpis. Tincidunt dui ut ornare lectus sit amet. Aenean sed adipiscing diam donec adipiscing.</p>

    <p class="paragraph">Tortor at risus viverra adipiscing at in tellus. Varius quam quisque id diam vel quam elementum. Id cursus metus aliquam eleifend. Diam in arcu cursus euismod quis. Eu facilisis sed odio morbi quis. Convallis a cras semper auctor neque vitae tempus. Tristique senectus et netus et. Lobortis mattis aliquam faucibus purus in massa. Vitae turpis massa sed elementum tempus egestas sed. Odio ut enim blandit volutpat maecenas volutpat blandit aliquam etiam. Adipiscing commodo elit at imperdiet dui. Tristique senectus et netus et malesuada fames ac. Libero volutpat sed cras ornare arcu dui. Diam donec adipiscing tristique risus. Lacus sed turpis tincidunt id aliquet risus. Convallis posuere morbi leo urna molestie. Suspendisse potenti nullam ac tortor vitae purus.</p>

</div>
<canvas id="bubble"></canvas>

<!--JQuery-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.3/jquery-ui.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>

<!--Custom Script-->
<script src="${pageContext.request.contextPath}/javascript/mainpage.js"></script>
<script src="${pageContext.request.contextPath}/javascript/home.js"></script>
<script src="${pageContext.request.contextPath}/javascript/login.js"></script>


</body>
</html>