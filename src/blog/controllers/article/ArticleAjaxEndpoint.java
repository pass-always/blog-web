package blog.controllers.article;

import blog.controllers.AbstractBlogServlet;
import blog.model.Article;
import blog.model.ArticleDAO;
import blog.util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Intended for use with AJAX
 * Returns the Article object for a given articleId
 */
@WebServlet(name = "AjaxEndpointServlet", urlPatterns = { "/article-info" })
public class ArticleAjaxEndpoint extends AbstractBlogServlet {

    /**
     * Checks for an articleId parameter and returns the relevant Article object
     * Will return JSON object containing 'false' if articleId is not parsable as an int
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String articleId = req.getParameter("articleId");

        if (articleId != null) {
            Article article = doWithConnection(conn -> tryGetExistingArticle(articleId, conn), resp);
            if (null != article) {
                returnJson(resp, article);
            }
            return;
        }

        // if no parameters, return all articles
        List<Article> articles = new ArrayList<>();
        try (Connection conn = DBConnectionUtils.getConnectionFromWebInf(this, "connection.properties")) {
            articles = ArticleDAO.getAllArticles(conn);
            returnJson(resp, articles);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
