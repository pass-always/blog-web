<%--@Ethan--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Display Article Testing</title>
    <%-- When display user's articles, only deleting the articles option is given --%>
    <%--    JQuery  --%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.3/jquery-ui.js"></script>



</head>
<body>

<div id="modal" class="popup">
    <!-- Modal content -->
    <div class="popup-box">
        <p id="delete-alert-message">Do you want to delete the article?</p>
        <div id="delete-alert-container">
            <form action="${pageContext.request.contextPath}/delete-article" method="post" id="delete-article-form">
                <input class="delete-alert-button" type="submit" id="confirm-delete" value="Yes">
            </form>
            <input class="delete-alert-button" type="button" id="cancel-delete" value="No">
        </div>

    </div>
</div>

<script src="${pageContext.request.contextPath}/javascript/user-article-list.js" type="text/javascript"></script>


</body>
</html>

