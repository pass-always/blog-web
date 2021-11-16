package blog.controllers.users;

import blog.controllers.AbstractBlogServlet;
import blog.model.User;
import blog.model.UsersDAO;
import blog.util.Passwords;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Handles login requests
 * GET method loads the login page; POST method handles login requests
 */
@WebServlet(name = "Login", urlPatterns = { "/login" })
public class LoginServlet extends AbstractBlogServlet {
    /**
     * Handles login requests; expects a username and a password parameter
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("username");

        User user = doWithConnection(new DAOInstruction<User>() {
            @Override
            public User act(Connection conn) throws SQLException {
                return UsersDAO.getUser(userName, conn);
            }
        }, resp);

        if (resp.isCommitted()) {
            return; // the database is inaccessible and doWithConnection has sent back the response
        }

        if (null == user) {
            req.setAttribute("noUser", "That user name was not valid");
            req.getRequestDispatcher("/common/login.jsp").forward(req, resp);
            return;
        }

        if (!Passwords.isExpectedPassword(req.getParameter("password"), user.getSaltByte(), user.getIteration(), user.getHashCode())) {
            req.setAttribute("noPassword", "Your password was incorrect");
            req.getRequestDispatcher("/common/login.jsp").forward(req, resp);
            return;
        }

        HttpSession sess = req.getSession(true);
        sess.setAttribute(USER_SESSION_ATTRIBUTE_NAME, user);

        req.getRequestDispatcher("/home").forward(req, resp);
    }

    /**
     * Loads the login page
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isLoggedIn(req.getSession(true))) {
            req.getRequestDispatcher("/home").forward(req, resp);
        } else {
            req.setAttribute("isLogin", true);
            req.getRequestDispatcher("/common/login.jsp").forward(req, resp);
        }
    }
}

