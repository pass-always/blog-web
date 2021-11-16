<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <title>Login</title>

    <jsp:include page="styles.jsp"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/avatar-upload-preview.css">

</head>

<body>

    <jsp:include page="nav.jsp"/>

    <div class="login-box" <c:if test="${!isLogin}">style="transform: translate(-50%, -15%); width: 33%"</c:if>>

        <img src="${pageContext.request.contextPath}/images/default-avatar-1.png" class="avatar">
        <div id="login-form-container" <c:if test="${!isLogin}">hidden</c:if>>

            <h2>Login</h2>

        <c:if test="${not empty message}">
            <div id="error_message" class="error-message">${message}</div>
        </c:if>

            <form class="login-form" action="${pageContext.request.contextPath}/login" method="post" name="form"> <%-- onsubmit="return validated()">--%>

                <div class="textbox">
                    <i class="fas fa-user" aria-hidden="true"></i>
                    <input autocomplete="off" type="text" placeholder="Username" name="username">
                </div>

                <c:if test="${noUser.length() > 0}">
                    <div class="error-message" id ="username_error">${noUser}</div>
                </c:if>

                <div class="textbox">
                    <i class="fas fa-lock" aria-hidden="true"></i>
                    <input type="password" placeholder="Password" name="password">
                </div>

                <c:if test="${noPassword.length() > 0}">
                    <div class="error-message" id ="password_error">${noPassword}</div>
                </c:if>

                <input class="btn" type="submit" value="Sign In">

                <p>Or <a id="register-link" href="${pageContext.request.contextPath}/register">Register</a> </p>

            </form>
        </div>

        <div id="registration-form-container" <c:if test="${isLogin}">hidden</c:if>>

            <h2>Your Details</h2>

            <form action="${pageContext.request.contextPath}/update-user" method="post" name="update-details" enctype="multipart/form-data">

                <jsp:include page="/common/user-details-fields.jsp" />

                <input class="btn" type="submit" value="Register" id="submit-user-details">
                <p>Or <a id="login-link" href="${pageContext.request.contextPath}/login">Sign In</a> </p>

            </form>

        </div>

    </div>



    <jsp:include page="/common/scripts.jsp"/>
    <script src="${pageContext.request.contextPath}/javascript/home.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/login.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/user-details-validation.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/avatar-upload.js"></script>

</body>
</html>