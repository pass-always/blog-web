package blog.controllers.avatar;

import blog.controllers.AbstractBlogServlet;
import blog.model.User;
import blog.model.UsersDAO;
import blog.util.DBConnectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@WebServlet (name = "UploadAvatar", urlPatterns = {"/uploadAvatar"})
public class UploadAvatar extends AbstractBlogServlet {

    private File avatarFolder;
    private File tempFolder;

    protected static final String STORAGE_PATH = "/avatar";

    private final List<String> acceptableTypes = Arrays.asList("image/png", "image/jpeg");

    @Override
    public void init() throws ServletException {
        super.init();

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = getCurrentUser(session);

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
                    String fullFileName = user.getUserId() + "." + getExtension(item.getName());

                    user.setAvatarUrl(STORAGE_PATH + "/" + fullFileName);
                    // Update the database
                    updateUser(user);
//                    System.out.println(test); It will return the name of the input form, which is "avatar"
                    File avatarFile = new File(avatarFolder, fullFileName);
                    item.write(avatarFile);
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
//        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/displayAvatar.jsp");
//        dispatcher.forward(req,resp);
        resp.sendRedirect("./displayAvatar");
    }

    //Find the extension using a utility class provided by Apache Commons IO library:
    public String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public void updateUser(User user) throws ServletException, IOException{
        try(Connection conn = DBConnectionUtils.getConnectionFromWebInf(this,"connection.properties")) {
            UsersDAO.changeUserInfo(user, conn);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}

