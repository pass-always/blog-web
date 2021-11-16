<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<html>
<head>
    <meta charset="UTF-8">
    <title>Blog</title>
    <!--Font--->
    <script src="https://kit.fontawesome.com/c5aba5c557.js" crossorigin="anonymous"></script>

    <!--Google Fonts-->
    <link href="https://fonts.googleapis.com/css2?family=Candal&family=Lora:ital@1&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Roboto+Condensed:300,300i,400,400i,700i" rel="stylesheet">

    <!--CSS Stylesheet-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <script src="${pageContext.request.contextPath}/javascript/home.js"></script>
    <jsp:include page="/common/styles.jsp"/>

</head>

<body>

    <jsp:include page="/common/nav.jsp"/>

    <div class="page-wrapper">

    <!--Post Slider-->
    <div class="post-slider">
        <h1 class="slider-title">Trending Posts</h1>
        <i class="fas fa-angle-left prev"></i>
        <i class="fas fa-angle-right next"></i>

        <div class="post-wrapper">

            <div class="post">
                <img src="${pageContext.request.contextPath}/images/Travel-Blog.jpg" alt="" class="slider-image">
                <div class="post-info">
                    <h4><a href="${pageContext.request.contextPath}/html/Article1.jsp">Placerat orci nulla pellentesque dignissim
                    </a> </h4>
                    <i class="far fa-user">Nicole</i>
                    <br>
                    <br>
                    <i class="far fa-calendar"> June 1, 2020</i>
                </div>
            </div>

            <div class="post">
                <img src="${pageContext.request.contextPath}/images/8uniquegift.jpg" alt="" class="slider-image">
                <div class="post-info">
                    <h4><a href="${pageContext.request.contextPath}/html/Article2.jsp">Viverra justo nec ultrices dui</a> </h4>
                    <i class="far fa-user">Sarah</i>
                    <br>
                    <br>
                    <i class="far fa-calendar">April 6, 2020</i>
                </div>
            </div>

            <div class="post">
                <img src="${pageContext.request.contextPath}/images/photo-1503023345310-bd7c1de61c7d.jpg" alt="" class="slider-image">
                <div class="post-info">
                    <h4><a href="${pageContext.request.contextPath}/html/Article3.jsp">Lacus sed viverra tellus in</a> </h4>
                    <i class="far fa-user">George</i>
                    <br>
                    <br>
                    <i class="far fa-calendar">June 2, 2020</i>
                </div>
            </div>

            <div class="post">
                <img src="${pageContext.request.contextPath}/images/Blogging-1-760x400.jpg" alt="" class="slider-image">
                <div class="post-info">
                    <h4><a href="${pageContext.request.contextPath}/html/Article4.jsp">Eget gravida cum sociis natoque</a> </h4>
                    <i class="far fa-user">Nicole</i>
                    <br>
                    <br>
                    <i class="far fa-calendar">May 31, 2020</i>
                </div>
            </div>

            <div class="post">
                <img src="${pageContext.request.contextPath}/images/unique-2596-ac138318b28a3ba877830d08eb45906d@1x.jpg" alt="" class="slider-image">
                <div class="post-info">
                    <h4><a href="${pageContext.request.contextPath}/html/Article5.jsp">Leo duis ut diam quam</a> </h4>
                    <i class="far fa-user">Sarah</i>
                    <br>
                    <br>
                    <i class="far fa-calendar">June 6, 2020</i>
                </div>
            </div>

            <div class="post">
                <img src="${pageContext.request.contextPath}/images/aerial-background-blog-cafe.jpg" alt="" class="slider-image">
                <div class="post-info">
                    <h4><a href="${pageContext.request.contextPath}/html/Article6.jsp">Eget arcu dictum varius duis</a> </h4>
                    <i class="far fa-user">George</i>
                    <br>
                    <br>
                    <i class="far fa-calendar">June 5, 2020</i>
                </div>
            </div>

        </div>
    </div>
    <!--//Post Slider-->

        <!--Content-->
        <div class="content clearfix">

            <!---Main Content--->
            <div class="posts">
                <h2 class="recent-post-title">Recent Posts</h2>
                <!---Holder-->
                <c:forEach var="allArticles" items="${articles}">

                <div class="holder">
                    <div class="post-preview">
                        <h3><a href="${pageContext.request.contextPath}/article?articleId=${allArticles.getArticleId()}">${allArticles.getArticleTitle()}</a></h3>
                        <i class="fas fa-calendar-day"> ${allArticles.getArticlePostTime()}</i>
                        <p class="preview-text">${allArticles.getArticleAbstract()}</p>
                        <a href="${pageContext.request.contextPath}/article?articleId=${allArticles.getArticleId()}" class="btn-btn read-more">Read more</a>
                    </div>
                </div>
                <!--//Holder-->
                </c:forEach>

                <!-- btn holder -->
            </div>

            <div class="btnHolder">
                <button id="btnMore" class="btn">Load More</button>
            </div>
        </div>
        <!--//Content-->
    </div>
<!--// Page Wrapper-->
<canvas id="bubble"></canvas>

<!--JQuery-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.3/jquery-ui.js"></script>

<!-- Slick Carousel-->
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>

<!--Custom Script-->
<script src="${pageContext.request.contextPath}/javascript/mainpage.js"></script>
<script src="${pageContext.request.contextPath}/javascript/home.js"></script>
<script src="${pageContext.request.contextPath}/javascript/loadmore.js"></script>

</body>
</html>
