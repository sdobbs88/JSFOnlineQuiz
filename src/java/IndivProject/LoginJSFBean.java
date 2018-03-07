package IndivProject;

import java.io.Serializable;
import javax.inject.Named;
import java.sql.*;
import javax.enterprise.context.SessionScoped;

@Named(value = "loginBean")
@SessionScoped
//@ApplicationScoped
public class LoginJSFBean implements Serializable {

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    private PreparedStatement pstmt;
    private String username;
    private String password;
    private String firstname;
    private String mi;
    private String lastname;
    private String response;
    private final String timestamp = "CURRENT_TIMESTAMP";
    private String currentPassword;

    private String newPassword;
    private String newPassword2;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String register() {
        firstname = "";
        lastname = "";
        username = "";
        password = "";
        mi = "";
        response = "";
        return "RegisterPage";
    }

    public String back() {
        username = "";
        password = "";
        response = "";
        newPassword = "";
        newPassword2 = "";
        return "login";
    }
    
    

    public String changePassword() {
        return "updatePassword";
    }

    public String changePasswordDB() {
        initializeJdbc();

        try {
            String query = "select * from user where username='" + username + "';";
            ResultSet rs = pstmt.executeQuery(query);

            if (!rs.next()) {
                response = "Error: Username does not exist.";
            } else {
                initializeJdbc();
//                    String query1 = "select password from user where username ='" + username + "';";
//                    ResultSet rs1 = pstmt.executeQuery(query1);

                currentPassword = rs.getString("password");

                if (currentPassword.equals(password)) {
                    if (newPassword.equals(newPassword2)) {
                        try {
                            //Loading the driver
                            Class.forName("com.mysql.jdbc.Driver");
                            System.out.println("Driver loaded");
                            //Start connection
                            
                            //Local DB
                            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/selftest", "scott", "tiger");
                            
                            //My DB
                            Connection conn = DriverManager.getConnection("jdbc:mysql://csci-3720-p2.cehwxsgs7qjg.us-east-1.rds.amazonaws.com:3306/selftest", "wilhsy86", "310998007");
                            
                            
                            
                            System.out.println("Database Connected");
                            //Statement to delete the tuple before it is updated
                            pstmt = conn.prepareStatement("update user set password='" + newPassword + "', whencreated =CURRENT_TIMESTAMP where username ='" + username + "';");
                            pstmt.executeUpdate();
                            response = "Password successfully changed. Press back to login.";
                        } catch (ClassNotFoundException | SQLException ex) {
                            response = ("Error: " + ex);
                        }
                    } else {
                        response = "Error: New password does not match.";
                    }
                } else {
                    response = "Error: Current password does not match.";
                }
            }
        } catch (SQLException ex) {
            response = "Database error: " + ex;
        }
        return "";
    }

    public String addUser() {
        initializeJdbc();

        try {
            String query = "select * from user where username = '" + username + "';";
            ResultSet rs = pstmt.executeQuery(query);

            if (!rs.next()) {
                addUser(firstname, mi, lastname, username, password);
                response = "User created. Click back to return to login page.";
            } else {
                response = "Error: Username already exists. Please choose a different username.";
            }
        } catch (SQLException ex) {
            response = "Database error: " + ex;
        }

        return "";
    }

    public String login() {
        initializeJdbc();
        if (username.isEmpty() && password.isEmpty()) {
            response = "Error: Username and password required.";
            return "login";
        } else if (password.isEmpty()) {
            response = "Error: Password field required";
            return "login";
        } else if (username.isEmpty()) {
            response = "Error: Username field required";
        } else {
            try {
                String query = "select * from user where username='" + username + "' and password= '" + password + "';";
                ResultSet rs = pstmt.executeQuery(query);

                if (!rs.next()) {
                    response = "Error: Improper username or password.";
                } else {
                    response = "";
                    return "HomePage";
                }
            } catch (SQLException ex) {
                response = "Database error: " + ex;
            }
        }
        return "login";
    }

    private void initializeJdbc() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
            // Establish a connection

            
            Connection conn = DriverManager.getConnection("jdbc:mysql://csci-3720-p2.cehwxsgs7qjg.us-east-1.rds.amazonaws.com:3306/selftest", "wilhsy86", "310998007");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/selftest", "scott", "tiger");
            
            
            System.out.println("Database connected");
            // Create a Statement
            pstmt = conn.prepareStatement("insert into user "
                    + "(username, firstname, mi, lastname, password, whencreated) "
                    + "values (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)");
        } catch (ClassNotFoundException | SQLException ex) {
            response = "Error" + ex;
        }
    }

    private void addUser(String firstname, String mi, String lastname, String username, String password) throws SQLException {
        pstmt.setString(1, username);
        pstmt.setString(2, firstname);
        pstmt.setString(3, mi);
        pstmt.setString(4, lastname);
        pstmt.setString(5, password);
        pstmt.executeUpdate();
    }

}
