package blog.controllers.article;

import blog.controllers.AbstractBlogServlet;
import blog.model.Article;
import blog.model.ArticleDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static blog.util.DBConnectionUtils.getConnectionFromWebInf;

/**
 * Loads the main articles page
 */
@WebServlet(name = "AllArticlesServlet", urlPatterns = { "/home", "/main", "/articles" })
public class HomeServlet extends AbstractBlogServlet {

    /**
     * Retrives all articles from the ArticlesDAO and passes it to the main articles page JSP
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Article> articles = doWithConnection(ArticleDAO::getAllArticles, resp);
        if (resp.isCommitted()) {
            return;
        }

        req.setAttribute("articles", articles);
        req.getRequestDispatcher("WEB-INF/posts.jsp").forward(req, resp);

    }

    /**
     * To allow doPost methods in other servlets to forward to this servlet
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
