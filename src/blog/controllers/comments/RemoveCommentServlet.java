package blog.controllers.comments;

import blog.controllers.AbstractBlogServlet;
import blog.model.Comment;
import blog.model.CommentDAO;
import blog.util.DBConnectionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;

@WebServlet(name = "RemoveComment", urlPatterns = { "/removeComment" })
public class RemoveCommentServlet extends AbstractBlogServlet {
    Comment comment = new Comment();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        if (!isLoggedIn(session)){
            req.setAttribute("message", "You must be logged in to delete comments.");
            req.getRequestDispatcher("/common/login.jsp").forward(req, resp);
        }
        String param = null;
        String[] result;

        // The commentID is saved in the parameter's name, which will retrieved by getParameterNames() method
        // The first element would be the object we want
        Enumeration<String> parameterNames = req.getParameterNames();
        if (parameterNames.hasMoreElements()){
            param = parameterNames.nextElement();
        }
        result = param.split("\\s");
        // {type of comment need to be deleted, article ID, comment ID}
        switch (result[0]) {
            case "singleComment":
                comment.setCommentID(Integer.parseInt(result[2]));
                doWithConnection(conn -> CommentDAO.deleteSingleComment(comment, conn), resp);
                break;
            case "allComments":
                doWithConnection(conn -> CommentDAO.deleteArticleComment(Integer.parseInt(result[1]), conn), resp);
                break;
            case "userComment":
                comment.setCommentID(Integer.parseInt(result[2]));
                doWithConnection(conn -> CommentDAO.deleteSingleComment(comment, conn), resp);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp");
                dispatcher.forward(req,resp);
                break;
        }
        resp.sendRedirect(req.getContextPath() + "/article?articleId=" + result[1]);
    }
}