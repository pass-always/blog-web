<%--
  Created by IntelliJ IDEA.
  User: GGPC
  Date: 2020/6/17
  Time: 5:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${article.getArticleTitle()}</title>
    <jsp:include page="/common/styles.jsp"/>
    <script src="//cdn.ckeditor.com/4.14.0/basic/ckeditor.js"></script>
    <%--    JQuery  --%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.3/jquery-ui.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <%--    CSS   --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/article-comment-list.css">

</head>
<body>
<jsp:include page="/common/nav.jsp"/>


<div class="container">
    <div class="profile-header">
        <div class="profile-img">

            <c:choose>
                <c:when test="${userInformation[2] == null}">
                    <img src="${pageContext.request.contextPath}/images/default-avatar-1.png" alt="user-avatar">
                </c:when>
                <c:when test="${userInformation[2] != null}">
                    <img src="${pageContext.request.contextPath}${userInformation[2]}" width="200" alt="user-avatar">
                </c:when>
            </c:choose>
        </div>

        <div class="profile-nav-info">
            <h3 class="user-name" id="article-title">${article.getArticleTitle()}</h3>
            <div class="address" style="margin-top: 4%">
                <p class="state user-information" id=${userInformation[0]}>Author: ${userInformation[1]}</p><br>
                <span class="country">PostTime: ${article.getArticlePostTime()}</span>
            </div>
        </div>
    </div>

    <div class="main-bd">
        <div class="left-side">
            <div class="profile-side">
                <p class="mobile-no" style="font-size: 18px">
                    Keywords:
                    ${article.getArticleKeyword()}
                </p>
                <p class="user-mail" style="font-size: 18px">
                    Abstract:
                    ${article.getArticleAbstract()}
                </p>
            </div>
        </div>
        <div class="right-side" id="${article.getArticleId()}">
            <div class="article-area">${article.getArticleContent()}</div>
            <jsp:include page="/common/article-comment-list.jsp"/>
        </div>
    </div>
</div>

    <jsp:include page="/common/scripts.jsp"/>
    <script src="${pageContext.request.contextPath}/javascript/article-comment-list.js"></script>

</body>
</html>
