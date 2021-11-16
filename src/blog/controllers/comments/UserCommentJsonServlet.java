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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserComment", urlPatterns = { "/userComment" })
public class UserCommentJsonServlet extends AbstractBlogServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (!isLoggedIn(session)){
            req.getRequestDispatcher("/common/login.jsp").forward(req, resp);
        }
        User user = getCurrentUser(session);
        List<Comment> comments = new ArrayList<>();
        comments = doWithConnection(conn -> CommentDAO.getUserComments(user,conn), resp);
        returnJson(resp,comments);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
