package blog.controllers.comments;


import blog.controllers.AbstractBlogServlet;
import blog.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "DisplayArticleComment", urlPatterns = { "/displayArticleComment" })
public class DisplayArticleCommentServlet extends AbstractBlogServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        if (!isLoggedIn(session)){
            req.setAttribute("message", "You must be logged in to add comments.");
            req.getRequestDispatcher("/common/login.jsp").forward(req, resp);
        }

        Comment newComment = new Comment();
        String[] parentUser;    // {avatarUrl, username}
        String info = null;

        User loginUser = getCurrentUser(session);

        // The commentID is saved in the parameter's name, which will retrieved by getParameterNames() method
        // The first element would be the object we want
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()){
            info = parameterNames.nextElement();
        }

        String[] parentCommentInfo = info.split("\\s");

//      parentCommentInfo = {commentID, parentID, articleID, userID}

        int commentID = Integer.parseInt(parentCommentInfo[0]);
        int parentID = Integer.parseInt(parentCommentInfo[1]);
        int articleID = Integer.parseInt(parentCommentInfo[2]);
        int parentUserID = Integer.parseInt(parentCommentInfo[3]);

        Article currentArticle = doWithConnection(conn -> ArticleDAO.getArticle(articleID,conn), resp);

/*
        for all the article base comments: {commentID, 0, articleID, userID}
        for the very first base comment, no previous comments: {0, 0, articleID, 0}
*/
        // Add the current userID
        newComment.setUserID(loginUser.getUserId());
        newComment.setArticleID(articleID);

        if (parentUserID != 0){
            newComment.setParentID(commentID);
            parentUser = doWithConnection(conn -> UsersDAO.getAvatarAndUsername(parentUserID,conn), resp);
            newComment.setCommentTitle("Reply To: " + parentUser[1] + " (" + currentArticle.getArticleTitle() + ")");
        }else{
            newComment.setCommentTitle("Re: " + currentArticle.getArticleTitle());
        }


        // Set current time stamp
        Date date = new Date();
        newComment.setCommentPostTime(new Timestamp(date.getTime()));
        // The content of textarea will be stored with html tags (e.g. <p>Hello World!<p>)
        newComment.setCommentContent(req.getParameter("comment-textarea"));

        boolean newRow = doWithConnection(conn -> CommentDAO.addArticleComment(articleID, newComment, conn), resp);

        resp.sendRedirect(req.getContextPath() + "/article?articleId=" + articleID);
    }
}
