package blog.controllers.users;

import blog.controllers.AbstractBlogServlet;
import blog.model.User;
import blog.model.UsersDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Allows the deletion of user accounts
 */
@WebServlet(name = "DeleteUserServlet", urlPatterns = { "/delete-user" })
public class DeleteUserServlet extends AbstractBlogServlet {

    /**
     * Handles requests to delete a user account
     * Does not require any parameters, but does require a user be logged in the account be the same as the currently logged in account
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession(true);
        if (!isLoggedIn(sess)) {    // should be validated client side too, but just in case
            req.setAttribute("message", "You must be logged in to delete your account");
            req.getRequestDispatcher("/common/login.jsp").forward(req, resp);
            return;
        }

        System.out.println(req.getParameter("userId"));

        doWithConnection(conn -> UsersDAO.deleteUser(getCurrentUser(sess).getUserId(), conn), resp);
        if (resp.isCommitted()) {
            return;
        }

        req.getRequestDispatcher("/logout").forward(req, resp); // removes the user from the session
    }

}
