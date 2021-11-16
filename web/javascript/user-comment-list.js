
let commentDisplayArea = document.querySelector("#commentArea");
let confirmDelete = document.querySelector("#confirm-delete");

let modal;
window.addEventListener("load",function () {

    loadComments();

    let cancelDelete = document.querySelector("#cancel-delete");
    cancelDelete.onclick = function() {
        modal.style.display = "none";
        confirmDelete.removeAttribute("name");
    }
});

function displayComment(comments) {
    for (let i = 0; i < comments.length; i++) {

        let newComment = document.createElement("div");
        newComment.setAttribute("id", `comment_${comments[i].commentID}`);
        newComment.setAttribute("class", "comments");
        setCommentFormat(newComment, comments, i);
        commentDisplayArea.appendChild(newComment);
    }

    modal = document.querySelector("#modal");

    let deleteButtons = document.querySelectorAll(".delete-comment");

    for (let i = 0; i < deleteButtons.length; i++) {
        deleteButtons[i].onclick = function() {
            confirmDelete.setAttribute("name", "userComment " + deleteButtons[i].getAttribute("name"));
            modal.style.display = "block";
        }
    }
}

function setCommentFormat(newComment, comments, i) {
    let date = new Date(comments[i].commentPostTime)
    let seconds = padZeros(date.getSeconds());
    let minutes = padZeros(date.getMinutes());
    let hours = padZeros(date.getHours());
    let days = padZeros(date.getDate());
    let months = padZeros(date.getMonth()+1)

    let postTime = "Posted at: " + hours + ':' + minutes + ':' + seconds
        + ' '+ date.getFullYear() + '-' + months + '-' + days;

    newComment.innerHTML = `<a class="comment-title in-comment" href='./article?articleId=${comments[i].articleID}'>${comments[i].commentTitle}</a>
                            <p class="comment-time in-comment">${postTime}</p>
                            <div class="comment-content in-comment">${comments[i].commentContent}</div>
                            <div class="delete-button in-comment">
                            <input type="button" class="delete-comment in-comment" value="Delete Comment" name="${comments[i].articleID} ${comments[i].commentID}">
                            </div>`;
}

function padZeros(number){
    let result
    if (number < 10){
        result = (number + "").padStart(2,"0");
    }else{
        result = number + "";
    }
    return result;
}

async function loadComments(){
    let responseObject = await fetch("./userComment");
    let comments = await responseObject.json();
    if (comments.length !== 0) {
        commentDisplayArea.innerHTML = "";
        displayComment(comments);
    }else{
        commentDisplayArea.innerHTML = "<p id='no-user-comment'>You don't have any comments.</p>"
    }
}