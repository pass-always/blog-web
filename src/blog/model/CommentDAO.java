package blog.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    public static List<Comment> getAllComments (Connection conn) throws SQLException{
        List<Comment> comment = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "select * from project_comment;")) {

            try (ResultSet r = stmt.executeQuery()) {
                while (r.next()) {
                    comment.add(newComment(r));
                }
            }
        }
        return comment;
    }

    public static List<Comment> getParentComments (Connection conn) throws SQLException{
        List<Comment> comment = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "select * from project_comment order by parent_Id;")) {

            try (ResultSet r = stmt.executeQuery()) {
                while (r.next()) {
                    comment.add(newComment(r));
                }
            }
        }
        return comment;
    }

    // TODO: This method may not be required?
    // Get comment by Comment ID
    public static Comment getSingleComment (int commentID, Connection conn) throws SQLException{
        Comment comment;
        try (PreparedStatement stmt = conn.prepareStatement(
                "select * from project_comment where comment_Id =?;")) {

            stmt.setInt(1, commentID);
            try (ResultSet r = stmt.executeQuery()) {
                if (r.next()) {
                    comment = newComment(r);
                } else {
                    return null;
                }
            }
        }
        return comment;
    }

    // Get list of all comments by article ID, include Nested Comments
    public static List<Comment> getArticleComments (int articleId, Connection conn) throws SQLException{
        List<Comment> comment = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "select * from project_comment where article_Id = ? order by comment_Id, parent_Id;")) {

            stmt.setInt(1, articleId);
            try (ResultSet r = stmt.executeQuery()) {
                while (r.next()) {
                    comment.add(newComment(r));
                }
            }
        }
        return comment;
    }

    // Get list of comments by user ID, (Comments and Nested Comments)
    public static List<Comment> getUserComments (User user, Connection conn) throws SQLException{
        List<Comment> comment = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "select * from project_comment where user_Id = ?;")) {

            stmt.setInt(1, user.getUserId());
            try (ResultSet r = stmt.executeQuery()) {
                while (r.next()) {
                    comment.add(newComment(r));
                }
            }
        }
        return comment;
    }

    // Add comment to article
    public static boolean addArticleComment (int articleId, Comment comment, Connection conn) throws SQLException{
        try(PreparedStatement stmt = conn.prepareStatement(
                "insert into project_comment (article_Id, user_Id, parent_Id, comment_title, comment_postTime, comment_content) values (?,?,?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS)){

            stmt.setInt(1, articleId);
            stmt.setInt(2, comment.getUserID());
            if (comment.getParentID() == null){
                stmt.setNull(3, Types.INTEGER);
            }else{
                stmt.setInt(3, comment.getParentID());
            }
            stmt.setString(4,comment.getCommentTitle());
            stmt.setTimestamp(5, comment.getCommentPostTime());
            stmt.setString(6, comment.getCommentContent());

            return insertCheck(comment, stmt);
        }
    }

    // Add NestComment to Parent Comment,
    // get articleID and commentID from the Parent Comment
    public static boolean addNestedComment (Comment baseComment, Comment nestComment, Connection conn) throws SQLException{
        try(PreparedStatement stmt = conn.prepareStatement(
                "insert into project_comment (article_Id, parent_Id, user_Id, comment_title, comment_postTime, comment_content) values (?,?,?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS)){

            stmt.setInt(1, baseComment.getArticleID());

            // Set the CommentID to the NestComment parentID
            stmt.setInt(2, baseComment.getCommentID());

            stmt.setInt(3, nestComment.getUserID());
            stmt.setString(4,nestComment.getCommentTitle());
            stmt.setTimestamp(5, nestComment.getCommentPostTime());
            stmt.setString(6, nestComment.getCommentContent());

            return insertCheck(nestComment, stmt);
        }
    }

    // TODO: What about when the Nested Comment user want to view all the comments he made?
    //  Maybe need a backlog for all the article and comments that has been added to the Server?

    // Delete comment from Article (and all the nested comment)
    // This could be a feature for the Article author to remove all the comments
    public static boolean deleteArticleComment(int articleId, Connection conn) throws SQLException{
        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM project_comment WHERE article_Id = ?")) {

            stmt.setInt(1, articleId);
            int rowsAffected = stmt.executeUpdate();
            return (rowsAffected == 1);
        }
    }

    // Delete single comment, we don't care if the comment is nest or base
    // Get commentID from Comment object
    // if parentID == null, it is a base comment
    // if parentID != null, it is a nested comment
    public static boolean deleteSingleComment(Comment comment, Connection conn) throws SQLException{
        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM project_comment WHERE comment_Id = ?")) {

            stmt.setInt(1, comment.getCommentID());
            int rowsAffected = stmt.executeUpdate();
            return (rowsAffected == 1);
        }
    }

    // Edit comment
    public static boolean editComment (Comment comment, Connection conn) throws SQLException{
        try(PreparedStatement stmt = conn.prepareStatement(
                "update project_comment SET comment_title = ?, comment_content = ?, comment_postTime = ? WHERE comment_Id = ?")){

            stmt.setString(1,  comment.getCommentTitle());
            stmt.setString(2,comment.getCommentContent());
            stmt.setTimestamp(3,comment.getCommentPostTime());

            stmt.setInt(4,comment.getCommentID());

            // Check if the update statement has been executed correctly
            int updateCount = stmt.executeUpdate();

            return updateCount != 0;
        }

    }


    // Method
    private static Comment newComment (ResultSet r) throws SQLException {
        return new Comment(r.getInt(1),
                r.getInt(2),
                r.getInt(3),
                r.getInt(4),
                r.getString(5),
                r.getTimestamp(6),
                r.getString(7));
    }

    private static boolean insertCheck (Comment comment, PreparedStatement stmt) throws SQLException {
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected == 0) {
            return false;
        }
        try (ResultSet r = stmt.getGeneratedKeys()) {
            // Move the cursor to next row
            r.next();
            // Set the generated ID to the comment object
            int newCommentId = r.getInt(1);
            comment.setCommentID(newCommentId);
            return true;
        }
    }

}
