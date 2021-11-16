package blog.model;

public class User {
//    The userID will be auto_increment in the database, here is a place holder to store the value afterwards
    private Integer userId;
//    The user must enter a valid username to complete the registration process
    private String userName;

    private String userContact;
    private String userEmail;
    private String userCity;
    private String userCountry;


//    Initial value for following string are used so if user choose not to put personal information, it will not be
//    logged into the database.
    private String userFname = null;
    private String userLname = null;
    private String userDob = null;


    private String description = null;

//    Place holder for the avatar image URL
    private String avatarUrl = null;

//    The user must provide a valid password in order to generate individual hash code and salt bytes
    private String hashCode;
    private String saltByte;
    private int iteration;

//    Empty constructor
    public User() {
    }

    // Constructor for all user information
    public User(Integer userId, String userName,String userContact, String userEmail, String userCity, String userCountry, String userFname, String userLname, String userDob, String description, String avatarUrl, String hashCode, String saltByte, int iteration) {
        this.userId = userId;
        this.userName = userName;
        this.userContact = userContact;
        this.userEmail = userEmail;
        this.userCity = userCity;
        this.userCountry = userCountry;
        this.userFname = userFname;
        this.userLname = userLname;
        this.userDob = userDob;
        this.description = description;
        this.avatarUrl = avatarUrl;

        this.hashCode = hashCode;
        this.saltByte = saltByte;
        this.iteration = iteration;
    }

    //    Constructor for user ID
    public User(Integer userId) {
        this.userId = userId;
    }

    //    Constructor for user password information with hash code and salt byte
    public User(String hashCode, String saltByte, int iteration) {
        this.hashCode = hashCode;
        this.saltByte = saltByte;
        this.iteration = iteration;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserContact() {
        return userContact;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserCity() {
        return userCity;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public String getUserFname() {
        return userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public String getUserDob() {
        return userDob;
    }

    public String getDescription() {
        return description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHashCode() {
        return hashCode;
    }

    public String getSaltByte() {
        return saltByte;
    }

    public int getIteration() {
        return iteration;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public void setSaltByte(String saltByte) {
        this.saltByte = saltByte;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
