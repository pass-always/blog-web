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
    <script src="//cdn.ckeditor.com/4.14.0/standard/ckeditor.js"></script>
</head>
<body>
<h1>Sample Article</h1>
<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sed adipiscing diam donec adipiscing tristique. Et ultrices neque ornare aenean euismod elementum nisi quis eleifend. A erat nam at lectus. Sed libero enim sed faucibus. Proin sagittis nisl rhoncus mattis rhoncus urna neque viverra. Dui sapien eget mi proin sed libero enim. Blandit volutpat maecenas volutpat blandit. In hac habitasse platea dictumst vestibulum. At lectus urna duis convallis convallis tellus. Mollis aliquam ut porttitor leo a diam sollicitudin. Sodales ut eu sem integer.
    <br><br>
    Cursus mattis molestie a iaculis at erat pellentesque. Accumsan in nisl nisi scelerisque eu. Tortor id aliquet lectus proin nibh nisl condimentum. Elit at imperdiet dui accumsan. Amet risus nullam eget felis eget nunc lobortis mattis. Quis hendrerit dolor magna eget. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Tellus mauris a diam maecenas sed enim ut sem. Lobortis scelerisque fermentum dui faucibus in. Diam quam nulla porttitor massa id. Semper auctor neque vitae tempus quam.</p>

    <c:forEach var="comment" items="${commentList}">
        <p>
<%--            Url here --%>
<%--            Add a way to record comment id--%>
        ${comment.getCommentTitle()}
        ${comment.getCommentPostTime()}
        ${comment.getCommentContent()}
        </p>
            <form action="${pageContext.request.contextPath}/removeComment" method="post">
                <input type="button" value="Reply">
                <input type="submit" name="commentId_${comment.getCommentId()}" value="Delete Comment">
            </form>
        <br>
    </c:forEach>

    <form action="${pageContext.request.contextPath}/displayComment" method="post">

        <div>
            <label for="commentText">
                Add Comment Testing (here can use JS to hide/show textarea) <br><br>
            </label>
            <textarea id="commentText" name="comment" rows="4" cols="50"></textarea>
            <script>
                CKEDITOR.replace( 'commentText' );
            </script>
        </div>
        <input type="submit" value="Add Comment">
    </form>

</body>
</html>
