//@Ethan
package blog.controllers.users;

import blog.controllers.AbstractBlogServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//servlet that logs out the current user - disconnects user and session and redirects to mainpage page
@WebServlet(name = "Logout", urlPatterns = {"/logout"})
public class LogoutServlet extends AbstractBlogServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sess = request.getSession(true);
        sess.setAttribute(USER_SESSION_ATTRIBUTE_NAME, null);

        request.getRequestDispatcher("/home").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}


