package blog.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO{

    // Get user information
    public static User getUser(String userName, Connection conn) throws SQLException{
        User user;
        try (PreparedStatement stmt = conn.prepareStatement(
                "select * from project_user where user_Name =?;")) {

            stmt.setString(1, userName);
            try (ResultSet r = stmt.executeQuery()) {
                if (r.next()) {
                    user = newUser(r);
                } else {
                    return null;
                }
            }
        }
        return user;
    }

    //    List of ALL user information management
    public static List<User> getAllUser(Connection conn) throws SQLException{
        List<User> userList = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "select * from project_user;")) {

            try (ResultSet r = stmt.executeQuery()) {
                while (r.next()) {
                    userList.add( newUser(r));
                }
            }
        }
        return userList;
    }

    // Get user information String[] = (user_Id, user_Name, avatar_Url)
    public static String[] getAvatarAndUsername(int userID, Connection conn) throws SQLException{
        String[] result = new String[3];
        try (PreparedStatement stmt = conn.prepareStatement(
                "select user_Id, user_Name, avatar_Url  from project_user where user_Id = ?;")) {
            stmt.setInt(1,userID);
            try (ResultSet r = stmt.executeQuery()) {
                if (r.next()) {
                    result[0] = r.getString(1);
                    result[1] = r.getString(2);
                    result[2] = r.getString(3);

                }
            }
        }
        return result;
    }

    // Add new user account information, Return false when fail
    public static boolean addNewUser (User user, Connection conn) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
        "insert into project_user " +
                "(user_Name, user_Contact, user_Email, " +
                "user_City, user_Country, " +
                "user_fname, user_lname, " +
                "user_DOB, user_description, " +
                "avatar_Url, " +
                "user_hashCode, user_salt, user_iteration) " +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS)){

            setUser(user, stmt);

            return insertCheck(user, stmt);
        }
    }

    // Change user information
    public static boolean changeUserInfo (User user, Connection conn) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
                "update project_user SET " +
                        "user_Name = ?, user_Contact = ?, user_Email = ?, " +
                        "user_City = ?, user_Country = ?, " +
                        "user_fname = ?, user_lname = ?, " +
                        "user_DOB = ?, user_description = ?, " +
                        "avatar_Url = ?, " +
                        "user_hashCode = ?, user_salt = ?, user_iteration = ? " +
                        "WHERE user_ID = ?;")){

            stmt.setInt(14, user.getUserId());

            setUser(user, stmt);

            // Check if the update statement has been executed correctly
            return updateCheck(stmt);
        }
    }

    // Delete user account information
    public static boolean deleteUser(int userId, Connection conn) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM project_user WHERE user_Id = ?")) {

            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            return (rowsAffected == 1);
        }

    }

    // Methods
    // Check if the update statement has been executed correctly
    private static boolean updateCheck(PreparedStatement stmt) throws SQLException {
        int updateCount = stmt.executeUpdate();

        return updateCount != 0;
    }

    // Check if information as been added to the database correctly
    private static boolean insertCheck(User user, PreparedStatement stmt) throws SQLException {
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected == 0) {
            return false;
        }
        try (ResultSet r = stmt.getGeneratedKeys()) {
            // Move the cursor to next row
            r.next();
            // Get the id for the new row and set the ID to the user object
            int newUserId = r.getInt(1);
            user.setUserId(newUserId);
            return true;
        }
    }

    private static User newUser(ResultSet r) throws SQLException {
        return new User(r.getInt(1),    // UserID
                r.getString(2),     // UserName
                r.getString(3),     // UserContact
                r.getString(4),     // UserEmail
                r.getString(5),     // UserCity
                r.getString(6),     // UserCountry
                r.getString(7),     // UserFname
                r.getString(8),     // UserLname
                r.getString(9),     // UserDob
                r.getString(10),    // description
                r.getString(11),    // avatarUrl
                r.getString(12),    // hashCode
                r.getString(13),    // saltByte
                r.getInt(14));      // iteration
    }

    private static void setUser(User user, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, user.getUserName());
        stmt.setString(2, user.getUserContact());
        stmt.setString(3, user.getUserEmail());
        stmt.setString(4, user.getUserCity());
        stmt.setString(5, user.getUserCountry());
        stmt.setString(6, user.getUserFname());
        stmt.setString(7, user.getUserLname());

        if (user.getUserDob().equals("")) {
            stmt.setNull(8, Types.DATE);
        } else {
            stmt.setString(8, user.getUserDob());
        }

        stmt.setString(9, user.getDescription());
        stmt.setString(10, user.getAvatarUrl());

        // Set hashing details
        stmt.setString(11, user.getHashCode());
        stmt.setString(12, user.getSaltByte());
        stmt.setInt(13, user.getIteration());
    }


}
