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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import userPack.Student;
import userPack.Teacher;
import userPack.User;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
/** Controller class updating profile information
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */
public class UpdateProfileController {

    User currentUser;
    @FXML
    public TextField nameField;
    @FXML
    public TextField contactField;
    @FXML
    public TextField dobField;
    @FXML
    public PasswordField currentField;
    @FXML
    public PasswordField newField;
    @FXML
    public PasswordField confirmField;
    @FXML
    public Label currentLabel;
    @FXML
    public Label newLabel;
    @FXML
    public Label confirmLabel;
    @FXML
    public Pane updatePane;
    @FXML
    public Label checkLabel;
    @FXML
    public Label passCheckLabel;

    /**
     * To initiate the update profile pane while scene change
     * @param user User object of the current user
     * @throws SQLException If problems with query
     */
    public void initiateUpdateProfileController(User user) throws SQLException {
        currentUser = user;
        currentField.setVisible(false);
        newField.setVisible(false);
        confirmField.setVisible(false);
        currentLabel.setVisible(false);
        newLabel.setVisible(false);
        confirmLabel.setVisible(false);

        DbUtilities dbUtilities = new DbUtilities();
        if (user.getType().equals("s")) {
            Student student = dbUtilities.getStudentInfo(user.getEmail());
            nameField.setText(student.getName());
            dobField.setText(student.getDob().toString());
            contactField.setText(student.getContact());
        } else {
            Teacher teacher = dbUtilities.getTeacherInfo(user.getEmail());
            nameField.setText(teacher.getName());
            dobField.setText(teacher.getDob().toString());
            contactField.setText(teacher.getContact());
        }
    }

    /**
     * To enable the change password fields
     * @param event Event of change password button click
     */
    public void onChangePasswordBtnClicked(ActionEvent event) {
        currentField.setVisible(true);
        newField.setVisible(true);
        currentLabel.setVisible(true);
        newLabel.setVisible(true);
        confirmLabel.setVisible(true);
        confirmField.setVisible(true);
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
     * To update the newly added information in the database
     * @param event Event of confirm button click
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     * @throws NoSuchAlgorithmException If invalid algorithm
     */
    public void onConfirmBtnClicked(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException {
        checkLabel.setText("");
        passCheckLabel.setText("");
        if (nameField.getText().equals("") || dobField.getText().equals("") || contactField.getText().equals("")) {
            checkLabel.setText("Please fill up all the fields");
        } else {
            boolean check = false;
            if (currentField.isVisible()) {
                if (currentField.getText().equals("") || newField.getText().equals("") || confirmField.getText().equals("")) {
                    passCheckLabel.setText("Please fill up all the fields");
                    check = true;
                } else {
                    String currentFieldPassword=encryptPassword(currentField.getText());
                    if (!(currentFieldPassword.equals(currentUser.getPassword()))) {
                        passCheckLabel.setText("Current password doesn't match");
                        check = true;
                    } else if (!(newField.getText().equals(confirmField.getText()))) {
                        passCheckLabel.setText("New passwords don't match");
                        check = true;
                    }
                }
            }
            if (check == false) {
                checkLabel.setText("");
                passCheckLabel.setText("");
                System.out.println("IN");
                if (!newField.getText().equals(""))
                    currentUser.setPassword(encryptPassword(newField.getText()));
                DbUtilities dbUtilities = new DbUtilities();
                dbUtilities.updateStudentInfo(currentUser, nameField.getText(), contactField.getText(), dobField.getText());
                System.out.println("Profile Updated successfully");

                FXMLLoader loader = new FXMLLoader();
                if (currentUser.getType().equals("s"))
                    loader.setLocation(getClass().getResource("studentDashBoard.fxml"));
                else
                    loader.setLocation(getClass().getResource("teacherDashBoard.fxml"));

                Parent root = loader.load();

                Scene afterLoginScene = new Scene(root);

                if (currentUser.getType().equals("s")) {
                    StudentDashBoardController studentDashBoardController;
                    studentDashBoardController = loader.getController();
                    studentDashBoardController.initiateStudentUser(currentUser);
                } else {
                    TeacherDashBoardController teacherDashBoardController;
                    teacherDashBoardController = loader.getController();
                    teacherDashBoardController.initiateTeacherUser(currentUser);
                }
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(afterLoginScene);
                window.show();
            }

        }

    }

    /**
     * To get a reference to the pane
     * @return Return the pane element as node
     */
    public Node getChildNode() {
        return updatePane;
    }
    public String getUiname () {
        return "Update Profile";
    }
}
