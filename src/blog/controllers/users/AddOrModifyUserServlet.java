package blog.controllers.users;

import blog.controllers.AbstractBlogServlet;
import blog.model.Article;
import blog.model.ArticleDAO;
import blog.model.User;
import blog.model.UsersDAO;
import blog.util.DBConnectionUtils;
import blog.util.Passwords;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Endpoint for adding or editing users
 * This servlet does double duty, handling both create-user requests from the login page
 * and update user requests from the user account page
 */
@WebServlet(name = "AddOrModifyUserServlet", urlPatterns = { "/register", "/update-user", "/profile" })
public class AddOrModifyUserServlet extends AbstractBlogServlet {

    private File avatarFolder;
    private File tempFolder;
    private String avatarUrl;
    protected static final String STORAGE_PATH = "/avatar";

    private final List<String> acceptableTypes = Arrays.asList("image/png", "image/jpeg");

    @Override
    public void init() throws ServletException {
        // Get the upload folder, ensure it exists.
        this.avatarFolder = new File(getServletContext().getRealPath(STORAGE_PATH));
        if (!avatarFolder.exists()) {
            avatarFolder.mkdirs();
        }

        // Sets the directory used to temporarily store files that are larger than the configured size threshold.
        this.tempFolder = new File(getServletContext().getRealPath("/WEB-INF/temp"));
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
    }

    /**
     * Will create a new user with the corresponding values from the input form
     * Expects client side validation that the username is unique
     * Will throw an SQL error if not
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = getCurrentUser(req.getSession(true));
        if (user == null) { user = new User(); }

        // final user variable to meet lamba final or effectively final requirement
        User finalUser = processUserFromRequest(user, req);

        Boolean result;
        if (finalUser.getUserId() == null) {
            result = doWithConnection(conn -> UsersDAO.addNewUser(finalUser, conn), resp);
            req.getSession(true).setAttribute(USER_SESSION_ATTRIBUTE_NAME, finalUser);
        } else {
            result = doWithConnection(conn -> UsersDAO.changeUserInfo(finalUser, conn), resp);
        }

        if (resp.isCommitted()) {
            return;
        }

        if (result) {
//            req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
            resp.sendRedirect(req.getContextPath() + "/profile");
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to add user to database");
        }
    }

    /**
     * Processes the request and obtains the user details
     * @param req HttpServletRequest object
     * @return User object with details provided in the form, or the current details if unchanged
     * @throws ServletException
     */
    private User processUserFromRequest(User user, HttpServletRequest req) throws ServletException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Sets the size threshold beyond which files are written directly to disk.
        // About 4 KB
        factory.setSizeThreshold(4 * 1024);
        factory.setRepository(tempFolder);

        ServletFileUpload sf = new ServletFileUpload(factory);

        try {
            List<FileItem> fileItems = sf.parseRequest(req);
            for (FileItem item : fileItems) {
                if (!item.isFormField() && acceptableTypes.contains(item.getContentType())) {
                    // Get the extension of the file
                    UUID uuid = UUID.randomUUID();
                    String fullFileName =  uuid + "." + getExtension(item.getName());

                    avatarUrl = STORAGE_PATH + "/" + fullFileName;

                    File avatarFile = new File(avatarFolder, fullFileName);
                    item.write(avatarFile);

                    user.setAvatarUrl(avatarUrl);

                } else if (item.getFieldName() != null) {

                    switch (item.getFieldName()) {
                        case "userName":
                            user.setUserName(item.getString());
                            break;

                        case "firstName":
                            user.setUserFname(item.getString());
                            break;

                        case "lastName":
                            user.setUserLname(item.getString());
                            break;

                        case "dob":
                            user.setUserDob(item.getString());
                            break;

                        case "description":
                            user.setDescription(item.getString());
                            break;

                        case "phone":
                            user.setUserContact(item.getString());
                            break;

                        case "email":
                            user.setUserEmail(item.getString());
                            break;

                        case "city":
                            user.setUserCity(item.getString());
                            break;

                        case "country":
                            user.setUserCountry(item.getString());
                            break;

                        case "password":
                            if (!item.getString().isBlank()) {
                                user.setSaltByte(Passwords.base64Encode(Passwords.getNextSalt()));
                                user.setIteration(Passwords.DEFAULT_ITERATIONS);
                                user.setHashCode(Passwords.hashSaltAndEncode(item.getString(), user.getSaltByte(), user.getIteration()));
                            }
                    }
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

        return user;
    }

    /**
     * Since this does double duty this does two things; if the user is not logged in, it takes them to the login page
     * If they are logged in, it takes them to the profile page where they can change their account details
     * @param req request object
     * @param resp response object
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = getCurrentUser(req.getSession());


        try (Connection conn = DBConnectionUtils.getConnectionFromWebInf(this, "connection.properties")) {
            List<Article> articles = ArticleDAO.getUserArticles(user.getUserId(), conn);
            req.setAttribute("articles", articles);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (isLoggedIn(req.getSession(true))) {
            req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
        } else {
            req.setAttribute("isLogin", false);
            req.getRequestDispatcher("/common/login.jsp").forward(req, resp);
        }

    }

    //Find the extension using a utility class provided by Apache Commons IO library:
    public String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

}
