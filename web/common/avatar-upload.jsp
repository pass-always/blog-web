<%--
  Created by IntelliJ IDEA.
  User: msx92
  Date: 06/14/20
  Time: 18:07
  To change this template use File | Settings | File Templates.
--%>

<div id="upload-container">
    <h3 id="avatar-header">Avatar Upload</h3>
    <form action="${pageContext.request.contextPath}/uploadAvatar" enctype="multipart/form-data" method="post" >
        <label>
            <input type="file" id="avatar-upload" name="avatar" accept="image/png, image/jpeg" />
        </label>

        <input type="submit" value="Submit">
    </form>
    <h1>Preview</h1>
    <div id="preview-container">
        <img id="preview-image" src="${pageContext.request.contextPath}/images/default-avatar-1.png" alt="user-avatar" >
    </div>
</div>
