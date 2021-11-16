package blog.controllers.article;

import blog.controllers.AbstractBlogServlet;
import blog.model.Article;
import blog.model.ArticleDAO;
import blog.model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Endpoint for posting new articles
 * Get method returns the page to add an article
 * Post method submits the article to the db and reloads the home page
 */
@WebServlet(name = "AddOrModifyArticleServlet", urlPatterns = { "/post", "/edit-post" })
public class AddOrModifyArticleServlet extends AbstractBlogServlet {

    public static final int MIN_BLURB_LENGTH = 1000;
    public static final int MAX_BLURB_LENGTH = 1500;

    /**
     * Receives requests to update or add users and updates the database accordingly
     * Expects parameters named:
     *      content
     *      title
     *      blurb (Article.articleAbstract)
     * Optionally, an articleId may be provided if an existing article is being updated
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!isLoggedIn(req.getSession())) {
            req.getRequestDispatcher("/common/login.jsp").forward(req, resp); // should never happen, but safety first
            return;
        }

        Article article = doWithConnection(conn -> tryGetExistingArticle(req.getParameter("articleId"), conn), resp);
        if (resp.isCommitted()) {
            return;
        }

        Article finalArticle = populateArticleObjectFromRequest(article == null ? new Article() : article, req);

        Boolean result;
        if (finalArticle.getArticleId() == null) {
            int newId = doWithConnection(conn -> ArticleDAO.addArticle(finalArticle, conn), resp);
            System.out.println(newId);
            if (newId == 0) {
                result = false;
            } else {
                finalArticle.setArticleId(newId);
                result = true;
            }
        } else {
            result = doWithConnection(conn -> ArticleDAO.editArticle(finalArticle, conn), resp);
        }

        //noinspection StatementWithEmptyBody
        if (result == null) {
            // prevent null pointer errors as addArticle may return null, in which case an error has occurred and an appropriate error response has already been sent;
        } else if (result) {
            resp.sendRedirect(req.getContextPath() + "/article?articleId=" + finalArticle.getArticleId());
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    /**
     * Gets the /post page
     * If a non-logged in user tries to access it, they are redirected to the login page
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!isLoggedIn(req.getSession(true))) {
            req.setAttribute("message", "You must be logged in to post");
            req.getRequestDispatcher("/common/login.jsp").forward(req, resp);
            return;
        }

        Article article = doWithConnection(conn -> tryGetExistingArticle(req.getParameter("articleId"), conn), resp);
        if (resp.isCommitted()) {   // a database error occurred
            return;
        }

        if (article != null) {
            req.setAttribute("article", article);
        }

        req.getRequestDispatcher("WEB-INF/WYSIWYGediting.jsp").forward(req, resp);

    }

    /**
     * Creates the blurb for the article; this should only be used if the user has not provided one
     * This tries to get a cohesive introduction to the article by looking for:
     *      the first paragraph
     *      the first few sentencees
     * If these searches fail, or either of these is more than 1000 characters, or less than 500, it will return the first 1000 characters instead
     * @param content the content of the article
     * @return a short segment from the beginning of the article
     */
    private String createBlurb(String content) {

        String[] paras = content.split("\\R");
        String result = tryBuildBlurb(paras);
        if (result != null) {
            return result;
        }

        String[] sentences = content.split("\\.");
        result = tryBuildBlurb(sentences);
        if (result != null) {
            return result;
        }

        return content.substring(0, MAX_BLURB_LENGTH);
    }

    /**
     * Takes the article broken into an array of strings and attempts to build a blurb from them by append them together
     * If they can be appended to form a string between MIN_BLURB_LENGTH and MAX_BLURB_LENGTH then returns that string
     * Otherwise, returns null
     * @param content the article content arranged into an array
     * @return a string of between 1500 and 1000 characters OR null
     */
    private String tryBuildBlurb(String[] content) {

        StringBuilder result = new StringBuilder();
        for (String element : content) {
            result.append(element);

            if (result.length() > MAX_BLURB_LENGTH) {
                return null;
            } else if (result.length() > MIN_BLURB_LENGTH) {
                return result.toString();
            }
        }

        return result.toString();
    }

    /**
     * Updates the provided Article object with the information provided from the request object's parameters
     * Expects parameters named:
     *  content
     *  title
     *  abstract
     *  keywords
     * Also handles file uploads from the CKEditors WYSIWYG pane
     * @param req request object containing parameters
     * @param article Article object to update
     */
    private Article populateArticleObjectFromRequest(Article article, HttpServletRequest req) {
        User user = getCurrentUser(req.getSession());
        article.setUserID(user.getUserId());

        article.setArticlePostTime(Timestamp.valueOf(LocalDateTime.now()));

        article.setArticleTitle(req.getParameter("title"));

        article.setArticleContent(req.getParameter("content"));

        String blurb = req.getParameter("abstract");
        if (!blurb.isBlank()) {
            article.setArticleAbstract(createBlurb(blurb));
        }

        article.setArticleKeyword(req.getParameter("keywords"));

        return article;

    }
}
