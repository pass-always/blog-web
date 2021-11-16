package blog.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    // Get single Article
    public static Article getArticle(int articleId, Connection conn) throws SQLException{
        Article article;
        try (PreparedStatement stmt = conn.prepareStatement(
                "select * from project_article where article_Id =?;")) {

            stmt.setInt(1, articleId);
            try (ResultSet r = stmt.executeQuery()) {
                if (r.next()) {
                    article = newArticle(r);
                } else {
                    return null;
                }
            }
        }
        return article;
    }

    // Get Article List for a user
    public static List<Article> getUserArticles(int userId, Connection conn) throws SQLException{
        List<Article> articles = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "select * from project_article where user_Id =?;")) {

            stmt.setInt(1, userId);
            try (ResultSet r = stmt.executeQuery()) {
                while (r.next()) {
                    articles.add(newArticle(r));
                }
            }
        }
        return articles;
    }

    // Get ALL articles, for the display
    public static List<Article> getAllArticles(Connection conn) throws SQLException{
        List<Article> articles = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet r = stmt.executeQuery("select * from project_article;")) {
                while (r.next()) {
                    articles.add(newArticle(r));
                }
            }
        }
        return articles;
    }



    // Add article to a user
    public static Integer addArticle (Article article, Connection conn) throws SQLException{
        try(PreparedStatement stmt = conn.prepareStatement(
                "insert into project_article (user_Id, article_title, article_keyword,article_abstract, article_postTime, article_content) values (?,?,?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS)){

            stmt.setInt(1,article.getUserID());
            stmt.setString(2,article.getArticleTitle());
            stmt.setString(3,article.getArticleKeyword());
            stmt.setString(4, article.getArticleAbstract());
            stmt.setTimestamp(5,article.getArticlePostTime());
            stmt.setString(6,article.getArticleContent());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                return 0;
            }
            try (ResultSet r = stmt.getGeneratedKeys()) {
                // Move the cursor to next row
                r.next();
                // Get the id for the new row and set the ID to the article object
                int newArticleId = r.getInt(1);
                article.setArticleId(newArticleId);
                return newArticleId;
            }
        }
    }

    // Edit Article
    public static boolean editArticle (Article article, Connection conn) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
                "update project_article SET article_title = ?,article_keyword = ?, article_abstract = ?, article_postTime = ?, article_content = ? WHERE user_ID = ? and article_Id = ?")){

            stmt.setString(1, article.getArticleTitle());
            stmt.setString(2, article.getArticleKeyword());
            stmt.setString(3, article.getArticleAbstract());
            stmt.setTimestamp(4, article.getArticlePostTime());
            stmt.setString(5, article.getArticleContent());

            stmt.setInt(6,article.getUserID());
            stmt.setInt(7,article.getArticleId());

            // Check if the update statement has been executed correctly
            int updateCount = stmt.executeUpdate();

            return updateCount != 0;
        }
    }

    // Delete Article, can only be done by login user in their dashboard when shown their list of articles
    public static boolean deleteArticle(int userId, int articleId, Connection conn) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM project_article WHERE article_Id = ? and user_Id = ?")) {

            stmt.setInt(1, articleId);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();
            return (rowsAffected == 1);
        }

    }

    // Method
    private static Article newArticle(ResultSet r) throws SQLException {
        return new Article(r.getInt(1),
                r.getInt(2),
                r.getString(3),
                r.getString(4),
                r.getString(5),
                r.getTimestamp(6),
                r.getString(7));
    }
}
