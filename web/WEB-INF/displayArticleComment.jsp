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
<%--    CKEditor    --%>
    <script src="//cdn.ckeditor.com/4.14.0/basic/ckeditor.js"></script>
<%--    JQuery  --%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.3/jquery-ui.js"></script>

<%--    CSS   --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/article-comment-list.css">

</head>

<body>
<div id="articleId">
<h1>Sample Article</h1>
<%--    commentInfo = {commentID, parentID, articleID, userID}
        for all the article base comments: {commentID, 0, articleID, 0}
        for the very first base comment: {0, 0, articleID, 0}
        --%>
</div>
<div id="article-comment-management">
    <input type="button" class="comment-management" id="article-reply" value="Add Comment" style="display: none">
    <input type="button" class="comment-management" id="delete-all-comments" value="Delete All Comments" style="display: none">
</div>
<div id="commentArea">
Waiting for comments...
</div>

<div id="modal" class="popup">
    <!-- Modal content -->
    <div class="popup-box">
        <p id="comment-box-title">Reply</p>
        <form action="${pageContext.request.contextPath}/displayArticleComment" method="post">
            <textarea id="commentBox" name="comment-textarea"></textarea>
            <div class="comment-button">
                <input class="comment-button" type="submit" id="post-comment" value="Post Comment">
                <input class="comment-button" type="button" id="cancel-comment" value="Cancel Comment">
            </div>
        </form>
    </div>
</div>

<div id="modal-delete" class="popup-delete">
    <!-- Modal content -->
    <div class="popup-box-delete">
        <p id="delete-alert-message">Do you want to delete the comment?</p>
        <div id="delete-alert-container">
            <form action="${pageContext.request.contextPath}/removeComment" method="post" id="delete-comment-form">
                <input class="delete-alert-button" type="submit" id="confirm-delete" value="Yes">
            </form>
            <input class="delete-alert-button" type="button" id="cancel-delete" value="No">
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/javascript/article-comment-list.js" type="text/javascript"></script>

</body>
</html>
