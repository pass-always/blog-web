package blog.controllers.avatar;

import blog.controllers.AbstractBlogServlet;
import blog.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "DisplayAvatar", urlPatterns = {"/displayAvatar"})
public class DisplayAvatar extends AbstractBlogServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = getCurrentUser(session);

        if (user != null) {
            //TODO: when do we first get the user information?

/*
        File avatarFolder = new File(getServletContext().getRealPath(UploadAvatar.STORAGE_PATH));
        String[] avatarPathList = avatarFolder.list();
        req.setAttribute("avatarPathList",avatarPathList);
*/
            // Since we store the whole path of the user avatar, we don't have to get the path through Servlet Context
            req.setAttribute("userAvatarPath", user.getAvatarUrl());

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/displayAvatar.jsp");
            dispatcher.forward(req, resp);
        }
    }

}

