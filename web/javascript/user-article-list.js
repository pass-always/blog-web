// @Ethan

let articleDisplayArea = document.querySelector("#articleArea");


window.addEventListener("load", function () {
    loadArticles();
});

function displayArticle(articles) {
    for (let i = 0; i < articles.length; i++) {

        let newArticle = document.createElement("div");
        newArticle.setAttribute("id", `article_${articles[i].articleID}`);
        newArticle.setAttribute("class", "articles");
        setArticleFormat(newArticle, articles, i);
        articleDisplayArea.appendChild(newArticle);
    }
}

function setArticleFormat(newArticle, articles, i) {
    let postTime = new Date(articles[i].articlePostTime)
    newArticle.innerHTML = `
<p class="article-title in-article">${articles[i].articleTitle}</p>
<p class="article-time in-article">${postTime}</p>
<p class="article-content in-article">${articles[i].articleContent}</p>

</div>`;
}

async function loadArticles() {
    let responseObject = await fetch("./userArticle");
    let articles = await responseObject.json();
    console.log(articles);
    if (articles.length !== 0) {
        articleDisplayArea.innerHTML = "";
        await displayArticle(articles);
    } else {
        articleDisplayArea.innerHTML = "<p id='no-user-article'>You haven't made any articles yet!</p>"
    }
}