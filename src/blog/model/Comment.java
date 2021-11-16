package blog.model;

import java.sql.Timestamp;

public class Comment {
    // Unique, auto generated
    private Integer commentID;
    // Reference
    private Integer articleID;
    private Integer userID;
    // Important to set the parentID to be null since it is referenced to the commentID in the database
    private Integer parentID = null;

    // TODO: The title of the comment need more discuss!

    // The title here is to imply which comment (or maybe user) this comment is replying to
    private String commentTitle = null;
    private Timestamp commentPostTime = null;
    private String commentContent = null;

    public Comment(Integer commentID, Integer articleID, Integer userID, Integer parentID,
                   String commentTitle, Timestamp commentPostTime, String commentContent) {
        this.commentID = commentID;
        this.articleID = articleID;
        this.userID = userID;
        this.parentID = parentID;
        this.commentTitle = commentTitle;
        this.commentPostTime = commentPostTime;
        this.commentContent = commentContent;
    }

    public Comment() {
    }

    // Getter and Setter
    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public Integer getArticleID() {
        return articleID;
    }

    public void setArticleID(Integer articleID) {
        this.articleID = articleID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public Timestamp getCommentPostTime() {
        return commentPostTime;
    }

    public void setCommentPostTime(Timestamp commentPostTime) {
        this.commentPostTime = commentPostTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
