package blog.controllers.article;

import blog.controllers.AbstractBlogServlet;
import blog.model.Article;
import blog.model.ArticleDAO;
import blog.model.User;
import blog.util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserArticles", urlPatterns = { "/userArticles" })
class UserArticles extends AbstractBlogServlet {

    Article article = new Article(1,1, "Article Title", "keyword", "abstract", Timestamp.valueOf(LocalDateTime.now()), "Article Content");
    User user = new User(1, "userName","000-000-0000","----@----.com","city","country", "userFname", "userLname", "userDOB", "description", "avatarUrl", "hashCode", "saltByte", 8);
    /**
     * Checks for an articleId parameter and returns the relevant Article object
     * Will return JSON object containing 'false' if articleId is not parsable as an int
     *
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Article> articles = new ArrayList<>();
        String articleId = req.getParameter("articleId");

        try (Connection conn = DBConnectionUtils.getConnectionFromWebInf(this, "connection.properties")) {
            articles = ArticleDAO.getUserArticles(user.getUserId(), conn);
//            Article article = tryGetExistingArticle(articleId, conn);
            /*if (null != articles) {
                returnJson(resp, articles);*/
                req.setAttribute("articles", articles);
                req.getRequestDispatcher("/common/dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}