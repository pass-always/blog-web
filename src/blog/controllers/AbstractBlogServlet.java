package blog.controllers;

import blog.model.Article;
import blog.model.ArticleDAO;
import blog.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static blog.util.DBConnectionUtils.getConnectionFromWebInf;

/**
 * Abstract servlet object that defines some common methods that will be required across the servlets
 */
@WebServlet(name = "AbstractArticleServlet")
public abstract class AbstractBlogServlet extends HttpServlet {
    public static final String USER_SESSION_ATTRIBUTE_NAME = "user";

    /**
     * Tries to get the Article object corresponding to the specified articleId
     * If no article was found (including if the articleId does not meet the appropriate format), returns null
     * @param articleId the id to search for (null safe)
     * @param conn Connection to database to obtain article from
     * @return Article object corresponding to the passed in articleId, or null if no such article exists
     * @throws SQLException if database connection is unavailable or similar
     */
    protected Article tryGetExistingArticle(String articleId, Connection conn) throws SQLException {
        if (null == articleId) {
            return null;
        }

        if (!articleId.matches("^[1-9]\\d*$")) {
            return null;
        }

        return ArticleDAO.getArticle(Integer.parseInt(articleId), conn);
    }

    /**
     * Checks if there is currently a logged in user
     * Assumes that a user will be associated using the USER_SESSION_ATTRIBUTE_NAME string
     * Requires that the session not be null
     * @param sess the current session
     * @return true if there is a user associated with the session
     */
    protected boolean isLoggedIn(HttpSession sess) {
        return null != sess.getAttribute(USER_SESSION_ATTRIBUTE_NAME);
    }

    /**
     * Returns the user currently associated with the session
     * Returns null if no user associated with the user
     * @param sess the current session
     * @return the User currently logged in or null if no one logged in
     */
    protected User getCurrentUser(HttpSession sess) {
        Object userObj = sess.getAttribute(USER_SESSION_ATTRIBUTE_NAME);

        if (userObj instanceof User) {
            return ((User) userObj);
        }

        return null;
    }

    /**
     * Sets the response object to the provided object
     * @param resp response object
     * @param toJson object to Jsonify
     * @throws IOException
     */
    protected void returnJson(HttpServletResponse resp, Object toJson) throws IOException {
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(toJson);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    /**
     * Generic method for handling DAO methods within a self-closing Connection object
     * If a database error occurs it will pass an error code and the message "Unable to access database" to the client
     * Care should be taken when using this to distinguish between a null result because the server is inaccessible and
     * a null result because the DAO purposefully returned null
     * @param instruction DAOInstruction to run with the provided connection
     * @param resp Response object to return error on if required
     * @param <T> return type expected from the DAO object
     * @return expected return type of the DAO method or Boolean if no appropriate object; null if false
     * @throws IOException
     */
    protected <T> T doWithConnection(DAOInstruction<T> instruction, HttpServletResponse resp) throws IOException {
        try (Connection conn = getConnectionFromWebInf(this, "connection.properties")) {

            return instruction.act(conn);

        } catch (SQLException e) {

            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to access database");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * A functional interface to use with the doWithConnection method     */
    protected interface DAOInstruction<T> {
        /**
         * If it is expected to return an object, it should return null on failure
         * If it is not expected to return anything, it should return a Boolean
         * @return true if action succeeded
         */
        T act(Connection conn) throws SQLException;
    }
}
