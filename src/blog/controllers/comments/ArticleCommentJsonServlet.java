package blog.controllers.comments;

import blog.controllers.AbstractBlogServlet;
import blog.model.Article;
import blog.model.Comment;
import blog.model.CommentDAO;
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

@WebServlet(name = "ArticleComment", urlPatterns = { "/articleComment" })
public class ArticleCommentJsonServlet extends AbstractBlogServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Comment> comments = new ArrayList<>();

        int articleId;
        String temp = req.getParameter("articleId");
        if (temp != null) {
            articleId = Integer.parseInt(temp);
            int finalArticleId = articleId;

            comments = doWithConnection(conn -> CommentDAO.getArticleComments(finalArticleId, conn), resp);
            returnJson(resp, comments);
        }else{
            returnJson(resp,null);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
