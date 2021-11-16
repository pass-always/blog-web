<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav role='navigation' id="mainnav">

    <br><br><br>

    <ul class="gap">

        <li><a href="${pageContext.request.contextPath}/home">HOME</a></li>
        <c:choose>

            <c:when test="${not empty user}">
                <li><a href="${pageContext.request.contextPath}/profile">PROFILE</a></li>
                <li><a href="${pageContext.request.contextPath}/post">NEW POST</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">LOGOUT</a></li>
            </c:when>

            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/login">LOGIN</a></li>
                <li><a href="${pageContext.request.contextPath}/register">REGISTER</a></li>
            </c:otherwise>

        </c:choose>

    </ul>

</nav>

<div class="hamb">
    <a href="#"><i class="fa fa-bars"></i></a>
</div>

<canvas id="bubble"></canvas>
