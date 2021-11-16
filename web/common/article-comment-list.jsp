<%--
  Created by IntelliJ IDEA.
  User: msx92
  Date: 06/17/20
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>

<div id="article-comment-management">
    <input type="button" class="comment-management" id="article-reply" value="Add Comment" style="display: none">
    <input type="button" class="comment-management" id="delete-all-comments" value="Delete All Comments" style="display: none">
</div>
<div id="commentArea">
    <p>Loading Article Comments...</p>
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
