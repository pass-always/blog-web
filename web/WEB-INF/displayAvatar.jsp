<%--
  Created by IntelliJ IDEA.
  User: msx92
  Date: 06/10/20
  Time: 04:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Avatar Testing Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/avatar-upload-preview.css">
    <script src="${pageContext.request.contextPath}/javascript/displayAvatar.js"></script>
</head>
<body>
<h1>Upload New Avatar</h1>
<div id="upload-container">
<form action="${pageContext.request.contextPath}/uploadAvatar" enctype="multipart/form-data" method="post" >
    <label>
        <input type="file" id="avatar-upload" name="avatar" accept="image/png, image/jpeg" />
    </label>

    <input type="submit" value="Submit">
</form>
</div>
<h1>Preview</h1>
<div id="preview-container">
    <img id="preview-image" src="${pageContext.request.contextPath}/images/default-avatar-1.png" alt="user-avatar">
</div>

<c:if test="${userAvatarPath != null}">
    <img src="<c:url value="${userAvatarPath}"/>" title="userAvatar" alt="userAvatar" width="340px">
</c:if>
</body>
</html>
