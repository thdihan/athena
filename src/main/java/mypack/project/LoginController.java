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

        if (userType.equals("s")) {
            studentDashBoardController = loader.getController();
            studentDashBoardController.initiateStudentUser(loggingUser);

            User us = new User(studentDashBoardController.getCurrentUser());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(afterLoginScene);
            window.show();
        } else if (userType.equals("t")) {
            System.out.println("Empty teacher controller");

        } else {
            System.out.println("Empty admin controller");
        }


    }

    /**
     * For login authentication of users
     * @param event Event of login button click
     */
    public void loginCode(ActionEvent event) {
        try {
            DbUtilities dbUtilities = new DbUtilities();
            if (emailField.getText().isEmpty() || passField.getText().isEmpty())
                wrongField.setText("Email and password field can't be left empty !!");
            else if (dbUtilities.loginNow(emailField.getText(), passField.getText())) {

                wrongField.setText("Valid username and password !!");

                User loggingUser = new User(emailField.getText(), passField.getText());
                String userType = dbUtilities.getUserType(loggingUser.getEmail());
                loggingUser.setType(userType);

                //need to use nested if for other users similarly
                if (userType.equals("s"))
                    goToDashBoard("studentDashBoard.fxml", event, loggingUser);
            } else {
                wrongField.setText("Invalid username or password !!");
            }
        } catch (SQLException e) {
            System.out.println(e);
            wrongField.setText("Login check error");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
