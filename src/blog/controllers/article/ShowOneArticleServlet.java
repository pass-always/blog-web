package blog.controllers.article;

import blog.controllers.AbstractBlogServlet;
import blog.model.Article;
import blog.model.UsersDAO;
import blog.util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "OneArticle", urlPatterns = { "/article" })
public class ShowOneArticleServlet extends AbstractBlogServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String articleId = req.getParameter("articleId");

        try (Connection conn = DBConnectionUtils.getConnectionFromWebInf(this, "connection.properties")) {
            Article article = tryGetExistingArticle(articleId, conn);
            String[] user = UsersDAO.getAvatarAndUsername(article.getUserID(),conn);

            req.setAttribute("article", article);
            req.setAttribute("userInformation",user);
            req.getRequestDispatcher("WEB-INF/showOneArticle.jsp").forward(req, resp);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
