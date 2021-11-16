<%--
  Created by IntelliJ IDEA.
  User: msx92
  Date: 06/10/20
  Time: 05:42
  To change this template use File | Settings | File Templates.
--%>

<div id="commentArea">
    <p id="loading-comment">Loading Your Comments...</p>
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