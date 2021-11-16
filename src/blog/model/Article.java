package blog.model;

import java.sql.Timestamp;

public class Article {
    // Unique, auto generated
    private Integer articleId;
    // Reference
    private Integer userID;

    private String articleTitle = null;
    private String articleKeyword = null;
    private String articleAbstract = null;

    // TODO: Timezone?

    private Timestamp articlePostTime;
    private String articleContent = null;


    public Article(Integer articleId, Integer userID, String articleTitle, String articleKeyword, String articleAbstract, Timestamp articlePostTime, String articleContent) {
        this.articleId = articleId;
        this.userID = userID;
        this.articleTitle = articleTitle;
        this.articleKeyword = articleKeyword;
        this.articleAbstract = articleAbstract;
        this.articlePostTime = articlePostTime;
        this.articleContent = articleContent;
    }

    public Article() {
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public Timestamp getArticlePostTime() {
        return articlePostTime;
    }

    public void setArticlePostTime(Timestamp articlePostTime) {
        this.articlePostTime = articlePostTime;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getArticleKeyword() {
        return articleKeyword;
    }

    public void setArticleKeyword(String articleKeyword) {
        this.articleKeyword = articleKeyword;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
}
