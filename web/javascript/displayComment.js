
window.addEventListener("load",function () {
    let display = document.querySelector("#displayComment");
    let commentArea = document.querySelector("#commentArea");


    display.addEventListener("click",function () {

        let firstContainer = document.createElement("ul");
        firstContainer.setAttribute("id","commentContainer");
        commentArea.appendChild(firstContainer);

        function displayComment(comments) {

            let firstComment = document.createElement("li");
            firstComment.setAttribute("id",`comment_${comments[0].commentID}`);
            firstComment.innerHTML = `${comments[0].commentTitle}<br>${comments[0].commentPostTime}<br>${comments[0].commentContent}<br>`;
            firstContainer.appendChild(firstComment);

            for (let i = 1; i < comments.length; i++) {
                if (comments[i].parentID !== 0) {

                    let nestCommentContainer = document.createElement("ul");
                    nestCommentContainer.setAttribute("id", `container_${comments[i].commentID}`);
                    nestCommentContainer.setAttribute("class", "commentContainer");

                    let nestComment = newComment(comments, i);
                    nestCommentContainer.appendChild(nestComment);

                    let parentComment = document.querySelector(`#comment_${comments[i].parentID}`);
                    parentComment.appendChild(nestCommentContainer);

                } else if (comments[i].parentID === 0) {
                    let nestComment = newComment(comments, i);
                    firstContainer.appendChild(nestComment);
                }
            }
        }

        function newComment(comments, i) {
            let newComment = document.createElement("li");
            newComment.setAttribute("id", `comment_${comments[i].commentID}`);
            newComment.setAttribute("class", "commentTextarea");
            newComment.innerHTML = `${comments[i].commentTitle}<br>${comments[i].commentPostTime}<br>${comments[i].commentContent}<br>`;
            return newComment;
        }

        async function loadComments(){
            let responseObject = await fetch("./commentJson");
            let comments = await responseObject.json();
            console.log(comments);
            // console.log(comments.length);
            displayComment(comments);
        }
        loadComments();
    });

});