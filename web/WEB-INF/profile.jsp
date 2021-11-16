<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>

    <jsp:include page="/common/styles.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-comment-list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/avatar-upload-preview.css">

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
<%--                <img src="${pageContext.request.contextPath}${user.getAvatarUrl()}" width="200" alt="user-avatar">--%>
            </div>

            <div class="profile-nav-info">
                <h3 class="user-name">${user.getUserName()}</h3>
                <div class="address">
                    <p class="state">${user.getUserCity()},</p>
                    <span class="country">${user.getUserCountry()}</span>
                </div>
            </div>

            <div class="profile-option">
                <form action="${pageContext.request.contextPath}/delete-user" method="post">
                    <input class="notification" type="submit" value="Delete My Account" id="delete-account-button">
                </form>
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
                    <a href="${pageContext.request.contextPath}/post" class="createbtn">
                        <i class="fa fa-plus"></i>
                        Create
                    </a>
                </div>

            </div>

            <div class="right-side">

                <div class="nav">
                    <ul>
                        <li class="user-dashboard active">Dashboard</li>
                        <li class="user-comment">Comments</li>
                        <li class="user-details">Details</li>
                    </ul>
                </div>

                <div class="profile-body">
                        <div class="profile-dashboard tab">
                            <jsp:include page="/common/dashboard.jsp">
                                <jsp:param name="articles" value="${articles}"/>
                            </jsp:include>
                        </div>

                        <div class="profile-comment tab">
                            <jsp:include page="/common/user-comment-list.jsp" />
                        </div>

                        <div class="profile-details tab">
                        <h2>Your Details</h2>

                        <form action="${pageContext.request.contextPath}/update-user" method="post" name="update-details" enctype="multipart/form-data">

                            <jsp:include page="/common/user-details-fields.jsp" />

                            <input class="btn" type="submit" value="Update Details" id="submit-user-details">
                            </form>
                        </div>

                </div>
        </div>
        </div>
    </div>


    <canvas id="bubble"></canvas>

    <jsp:include page="/common/scripts.jsp" />
    <script src="${pageContext.request.contextPath}/javascript/profilemain.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/user-details-validation.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/user-comment-list.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/avatar-upload.js"></script>
</body>

</html>
