package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import userPack.User;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/** Controller class for login page
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */
public class LoginController {
    //    private User newUser; //this object will store the email, pass and type of the user which will login
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passField;
    @FXML
    private Label wrongField;

    /**
     * Function for going from login page to user dashboard
     * @param fxml Name of the fxml file
     * @param event Event of login button click
     * @param loggingUser User object currently logging in
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    public void goToDashBoard(String fxml, ActionEvent event, User loggingUser) throws IOException, SQLException {
//        this.newUser = loggingUser;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();


        Scene afterLoginScene = new Scene(root);

        //getting the user type
        String userType = loggingUser.getType();
        StudentDashBoardController studentDashBoardController; //do same for other user controllers also for inside if statements
        TeacherDashBoardController teacherDashBoardController;

        if (userType.equals("s")) {
            studentDashBoardController = loader.getController();
            studentDashBoardController.initiateStudentUser(loggingUser);

            User us = new User(studentDashBoardController.getCurrentUser());


        } else if (userType.equals("t")) {
            teacherDashBoardController=loader.getController();
            teacherDashBoardController.initiateTeacherUser(loggingUser);

            User us= new User(teacherDashBoardController.getCurrentUser());

        } else {
            System.out.println("Empty admin controller");
        }
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(afterLoginScene);
        window.show();


    }

    /**
     * Function to encrypt password based on SHA-256 algorithm
     * @param password Passsword as string
     * @return Encrypted password
     * @throws NoSuchAlgorithmException If invalid algorithm
     */
    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest mD=MessageDigest.getInstance("SHA-256");
        byte[] messageDigest= mD.digest(password.getBytes());
        BigInteger bigInteger=new BigInteger(1,messageDigest);
        return bigInteger.toString(16);
    }

    /**
     * For login authentication of users
     * @param event Event of login button click
     * @throws NoSuchAlgorithmException If incorrect algorithm
     */
    public void loginCode(ActionEvent event) {
        try {
            String password=encryptPassword(passField.getText());
            DbUtilities dbUtilities = new DbUtilities();
            if (emailField.getText().isEmpty() || passField.getText().isEmpty())
                wrongField.setText("Email and password field can't be left empty !!");
            else if (dbUtilities.loginNow(emailField.getText(), password)) {

                wrongField.setText("Valid username and password !!");

                User loggingUser = new User(emailField.getText(), password);
                String userType = dbUtilities.getUserType(loggingUser.getEmail());
                loggingUser.setType(userType);

                //need to use nested if for other users similarly
                if (userType.equals("s"))
                    goToDashBoard("studentDashBoard.fxml", event, loggingUser);
                else if (userType.equals("t")) {
                    goToDashBoard("teacherDashBoard.fxml", event, loggingUser);
                }
            } else {
                wrongField.setText("Invalid username or password !!");
            }
        } catch (SQLException e) {
            System.out.println(e);
            wrongField.setText("Login check error");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
