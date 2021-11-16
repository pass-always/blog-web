package blog.controllers.users;

import blog.controllers.AbstractBlogServlet;
import blog.model.User;
import blog.model.UsersDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Intended for use by AJAX queries; identifies whether a username is valid (i.e. not currently in use)
 * Returns a JSON object containing the value true if user name is taken
 */
@WebServlet(name = "UserNameValidation", urlPatterns = { "/username-used" })
public class UserNameValidationServlet extends AbstractBlogServlet {

    /**
     * When queried with the username parameter returns a JSON object containing the value true if user name is taken
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String testedUserName = req.getParameter("username");

        User user = doWithConnection(conn -> UsersDAO.getUser(testedUserName, conn), resp);
        if (!resp.isCommitted()) {
            returnJson(resp, null != user);
        }
    }

}
