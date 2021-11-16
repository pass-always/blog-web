<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 4/06/2020
  Time: 10:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Display Comment Testing</title>
<%-- When display user's comments, only deleting the comments option is given --%>
    <%--    JQuery  --%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.3/jquery-ui.js"></script>

<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-comment-list.css" media="bogus"/>--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-comment-list.css">

</head>
<body>

<%--TODO: Add User Avatar--%>
<h1>Your Comment History</h1>
<%--<form action="${pageContext.request.contextPath}/removeComment" method="post">--%>
<%--    <input type="submit" class="delete-comment" value="Delete Comment" name="1">--%>
<%--</form>--%>

<div id="commentArea">
Waiting for comments...
</div>

<div id="modal" class="popup">
    <!-- Modal content -->
    <div class="popup-box">
            <p id="delete-alert-message">Do you want to delete the comment?</p>
            <div id="delete-alert-container">
                <form action="${pageContext.request.contextPath}/removeComment" method="post" id="delete-comment-form">
                    <input class="delete-alert-button" type="submit" id="confirm-delete" value="Yes">
                </form>
                <input class="delete-alert-button" type="button" id="cancel-delete" value="No">
            </div>

    </div>
</div>

<script src="${pageContext.request.contextPath}/javascript/user-comment-list.js" type="text/javascript"></script>


</body>
</html>
