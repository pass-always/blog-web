<%--@Ethan--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>Post Article</title>

    <jsp:include page="/common/styles.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <script src="//cdn.ckeditor.com/4.14.0/basic/ckeditor.js"></script>
</head>
<body>
<jsp:include page="/common/nav.jsp" />


<div class="container">

    <div class="profile-header">
        <div class="profile-img">
            <c:choose>
                <c:when test="${user.getAvatarUrl() == null}">
                    <img src="${pageContext.request.contextPath}/images/default-avatar-1.png" alt="user-avatar">
                </c:when>
                <c:when test="${user.getAvatarUrl() != null}">
                    <img src="${pageContext.request.contextPath}${user.getAvatarUrl()}" width="200" alt="user-avatar">
                </c:when>
            </c:choose>
        </div>

        <div class="profile-nav-info">
            <h3 class="user-name">${user.getUserName()}</h3>
            <div class="address">
                <p class="state">${user.getUserCity()},</p>
                <span class="country">${user.getUserCountry()}</span>
            </div>
        </div>
    </div>

    <div class="main-bd">

        <div class="left-side">

            <div class="profile-side">
                <p class="mobile-no">
                    <i class="fa fa-phone"></i>
                    ${user.getUserContact()}
                </p>
                <p class="user-mail">
                    <i class="fa fa-envelope"></i>
                    ${user.getUserEmail()}
                </p>
                <div class="user-bio">
                    <h3>Bio</h3>
                    <p class="bio">${user.getDescription()}</p>
                </div>
            </div>

            <div class="profile-btn">
                <button class="createbtn" form="edit-area" type="submit">
                    <i class="fa fa-plus"></i>
                    Post
                </button>
            </div>

        </div>

        <div class="right-side">

            <div class="tab">
                <form method="post" action="${pageContext.request.contextPath}/post" id="edit-area">

                    <div id="set-article">
                        <div class="textbox">
                            <input placeholder="title" type="text" name="title" id="title" class="textbox" value="${article.getArticleTitle()}">
                        </div>

                        <div class="textbox">
                            <input placeholder="keywords" type="text" name="keywords" id="keywords" class="textbox" value="${article.getArticleKeyword()}">
                        </div>
                        <div class="textbox">
                            <input placeholder="abstract" type="text" name="abstract" id="abstract" class="textbox" value="${article.getArticleAbstract()}">
                        </div>
                    </div>
                    <textarea id="editor" name="content">${article.getArticleContent()}</textarea>
                </form>
            </div>

        </div>
    </div>
</div>






<!--ckeditor-->
<script src="//cdn.ckeditor.com/4.14.0/basic/ckeditor.js"></script>
<script>
    window.addEventListener("load",function () {
        CKEDITOR.replace('editor', {
            width: '100%',
            height: 500
        })
        CKEDITOR.instances.editor;
    });
</script>

<jsp:include page="/common/scripts.jsp" />
<script src="${pageContext.request.contextPath}/javascript/profilemain.js"></script>

</body>
</html>
