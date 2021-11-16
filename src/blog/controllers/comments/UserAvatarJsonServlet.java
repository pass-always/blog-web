package blog.controllers.comments;

import blog.controllers.AbstractBlogServlet;
import blog.model.Comment;
import blog.model.CommentDAO;
import blog.model.User;
import blog.model.UsersDAO;
import blog.util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserAvatarUrl", urlPatterns = { "/userAvatarUrl" })
public class UserAvatarJsonServlet extends AbstractBlogServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        int userID = Integer.parseInt(req.getParameter("userID"));

        // {user_Id, user_Name, avatar_Url}
        String[] result = doWithConnection(conn -> UsersDAO.getAvatarAndUsername(userID, conn), resp);
        if (result != null) {
            returnJson(resp, result);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
