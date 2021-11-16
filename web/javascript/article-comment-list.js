
let commentDisplayArea = document.querySelector("#commentArea");
let boxTitle = document.querySelector("#comment-box-title");
let postButton = document.querySelector("#post-comment");
let loginUserId;

let userID = document.querySelector("p.user-information").getAttribute("id");
let articleID = document.querySelector("div.right-side").getAttribute("id");
let articleTitle = document.querySelector("h3#article-title").textContent;

let NewCommentModal = document.querySelector("#modal");
let deleteModal = document.querySelector("#modal-delete");
let message = document.querySelector("#delete-alert-message");
let confirmDelete = document.querySelector("#confirm-delete");
let addBaseComment = document.querySelector("#article-reply");
let deleteAll = document.querySelector("#delete-all-comments");

window.addEventListener("load",function () {
    // Enable ckEditor for the textarea
    CKEDITOR.replace('commentBox', {
        width: '100%',
        height: 200
    });
    loadComments();

    let textarea = document.querySelector("#commentBox");

    let closeButton = document.querySelector("#cancel-comment");

    deleteAll.addEventListener("click",function () {
        message.innerHTML = "Do you want to delete all comments for this article?";
        confirmDelete.setAttribute("name", "allComments " + articleID);    // articleID
        // the name attribute store the type of form action it wanted to be handled by the remove comment servlet
        deleteModal.style.display = "block";
    });

    let cancelDelete = document.querySelector("#cancel-delete");
    cancelDelete.onclick = function() {
        deleteModal.style.display = "none";
        confirmDelete.removeAttribute("name");
    }

    // When the user clicks Cancel, close the modal and empty the textarea
    closeButton.onclick = function() {
        NewCommentModal.style.display = "none";
        // Clear the textarea of CK-editor
        CKEDITOR.instances.commentBox.setData('');
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target === NewCommentModal) {
            NewCommentModal.style.display = "none";
            deleteModal.style.display = "none";
            confirmDelete.removeAttribute("name");
        }
    }
});

function setFormat(newComment, userInfo, comments, i) {
    // userInfo = {user_Id, user_Name, avatar_Url}
    // Display default image if user didn't have an avatar uploaded
    let imagePath;
    if (userInfo[2] === null){
        imagePath = "./images/default-avatar-1.png";
    }else{
        imagePath = `.${userInfo[2]}`;
    }
    // Convert the timestamp to Date and set the display format
    let date = new Date(comments[i].commentPostTime)
    let seconds = padZeros(date.getSeconds());
    let minutes = padZeros(date.getMinutes());
    let hours = padZeros(date.getHours());
    let days = padZeros(date.getDate());
    let months = padZeros(date.getMonth()+1)

    let postTime = "Posted at: " + hours + ':' + minutes + ':' + seconds
        + ' '+ date.getFullYear() + '-' + months + '-' + days;

    newComment.innerHTML = `<p class="comment-title">${comments[i].commentTitle}</p>
                            <img class="user-image" src="${imagePath}" alt="user-avatar">
                            <p class="username">${userInfo[1]}</p>
                            <p class="comment-time">${postTime}</p>
                            <div class="comment-content">${comments[i].commentContent}</div>`;
    // Store the comment ID, its parent comment ID, its article ID and the writer of the comment (user ID)
    // in the name of the reply button so that it could be passed to the servlet for further processing

    let controls = document.createElement("div");
    controls.setAttribute("class","control-buttons");
    controls.innerHTML = `<input type="button" class="reply reply-and-delete" value="Reply" 
        name="${comments[i].commentID} ${comments[i].parentID} ${comments[i].articleID} ${comments[i].userID}">`;
    newComment.appendChild(controls);
    // Check if a user or a guest is viewing the page
    // if `${loginUserId}` == "null", no login user

    if (userInfo[0] === `${loginUserId}`) {
        console.log("have a user comment");
        // Append delete button to comments that belongs to current user
        let button = document.createElement("input");
        button.type = "button";
        button.value = "Delete Comment";
        button.setAttribute("class", "delete-current-comment reply-and-delete");
        button.setAttribute("name", `${comments[i].articleID} ${comments[i].commentID}`);
        controls.appendChild(button);
    }
}
// Padding zeros to minutes, seconds and hours of time
function padZeros(number){
    let result
    if (number < 10){
        result = (number + "").padStart(2,"0");
    }else{
        result = number + "";
    }
    return result;
}

async function displayComment(comments) {
    for (let i = 0; i < comments.length; i++) {
        if (comments[i].parentID !== 0) {

            let commentContainer = document.createElement("div");
            commentContainer.setAttribute("id", `container_${comments[i].commentID}`);
            commentContainer.setAttribute("class", "nest-container container nest");

            let newComment = document.createElement("div");
            newComment.setAttribute("id", `comment_${comments[i].commentID}`);
            newComment.setAttribute("class", "nest-text textarea nest");

            let userInfoResponse = await fetch(`./userAvatarUrl?userID=${comments[i].userID}`)
            let userInfo = await userInfoResponse.json();

            setFormat(newComment, userInfo, comments, i);
            commentContainer.appendChild(newComment);

            let parentComment = document.querySelector(`#container_${comments[i].parentID}`);
            parentComment.appendChild(commentContainer);

        } else{
            let commentContainer = document.createElement("div");
            commentContainer.setAttribute("id", `container_${comments[i].commentID}`);
            commentContainer.setAttribute("class", "base-container container base");

            let newComment = document.createElement("div");
            newComment.setAttribute("id", `comment_${comments[i].commentID}`);
            newComment.setAttribute("class", "base-text textarea base");

            let userInfoResponse = await fetch(`./userAvatarUrl?userID=${comments[i].userID}`)
            let userInfo = await userInfoResponse.json();

            setFormat(newComment, userInfo, comments, i);

            commentContainer.appendChild(newComment);
            commentDisplayArea.appendChild(commentContainer);
        }
    }

    // Get all the reply buttons
    let replyButtons = document.querySelectorAll(".reply");
    let commentUser = document.querySelectorAll(".username")

    // When the user clicks the button, open the modal
    for (let i = 0; i < replyButtons.length; i++) {
        replyButtons[i].onclick = function() {
            if (`${loginUserId}` === "null") {
                window.location.href = "./login";
            }else {
                boxTitle.innerHTML = "Reply: " + commentUser[i].textContent;
                let info = replyButtons[i].getAttribute("name");
                postButton.setAttribute("name", info);
                NewCommentModal.style.display = "block";
            }
        }
    }

    let deleteButtons = document.querySelectorAll(".delete-current-comment");
    for (let i = 0; i < deleteButtons.length; i++) {
        deleteButtons[i].onclick = function() {
            message.innerHTML = "Do you want to delete this comment?";
            confirmDelete.setAttribute("name", "singleComment " + deleteButtons[i].getAttribute("name"));
            deleteModal.style.display = "block";
        }
    }
}

async function loadComments(){
    if (articleID.length !== 0){
        let userResponse = await fetch("./userLoginCheck");
        loginUserId = await userResponse.json();
        let commentResponse = await fetch(`./articleComment?articleId=${articleID}`);
        let comments = await commentResponse.json();

        // if no login user, `${loginUserId}` == "null"
        if (`${loginUserId}` !== "null"){

            if (comments.length !== 0 && `${loginUserId}` === userID) {
                deleteAll.style.display = "inline-block";
            }


            // When user clicks add comment, the comment will be added to the article
            // for the very first base comment, no previous comments: name of the postButton is {0, 0, article_ID, 0}
            addBaseComment.style.display = "inline-block";
            addBaseComment.onclick = function() {
                modal.style.display = "block";
                postButton.setAttribute("name","0 0 " + articleID + " 0")
                boxTitle.innerHTML = "Reply: " + articleTitle;
            }

        }else{
            addBaseComment.style.display = "block";
            addBaseComment.onclick = function() {
                window.location.href = "./login";
            }
        }

        if (comments.length !== 0) {
            commentDisplayArea.innerHTML = "";
            await displayComment(comments);
        }else{
            deleteAll.style.display = "none";
            commentDisplayArea.innerHTML = "<p id='no-article-comment'>Be the first one to comment.</p>"
        }
    }else{
        commentDisplayArea.innerHTML = "<p id='article-not-found'>Article Not Found</p>"
    }
}


