package blog.controllers.comments;

import blog.controllers.AbstractBlogServlet;
import blog.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UserLoginCheck", urlPatterns = { "/userLoginCheck" })
public class UserLoginCheckServlet extends AbstractBlogServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        if (!isLoggedIn(session)){
            returnJson(resp,null);
        }else{
            User user = getCurrentUser(session);
            returnJson(resp,user.getUserId());
        }
    }
}
