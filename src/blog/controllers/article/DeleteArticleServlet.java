package blog.controllers.article;

import blog.controllers.AbstractBlogServlet;
import blog.model.Article;
import blog.model.ArticleDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Allows for a user to delete an article they have posted
 */
@WebServlet(name = "DeleteArticleServlet", urlPatterns = { "/delete-article" })
public class DeleteArticleServlet extends AbstractBlogServlet {

    /**
     * Handles post requests to delete articles
     * Expects to receive an articleId parameter
     * Requires that there be a currently logged in user and that user be the the user that wrote the article
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession(true);
        if (!isLoggedIn(sess)) {    // should be validated client side too, but just in case
            // TODO should we add a message saying you must be logged in to delete an article?
            req.getRequestDispatcher("/common/login.jsp").forward(req, resp);
            return;
        }

        Article article = doWithConnection(conn -> tryGetExistingArticle(req.getParameter("articleId"), conn), resp);
        if (null == article) {  // should be validated client side too, but just in case
            return;
        }

        if (!getCurrentUser(sess).getUserId().equals(article.getUserID())) {    // should be validated client side too, but just in case
            req.getRequestDispatcher("/home").forward(req, resp);
            return;
        }

        doWithConnection(conn -> ArticleDAO.deleteArticle(article.getUserID(), article.getArticleId(), conn), resp);
        if (resp.isCommitted()) {
            return;
        }

        req.getRequestDispatcher("/home").forward(req, resp); // TODO probably forward to user dashboard instead
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO determine behaviour
    }
}
