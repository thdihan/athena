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
import java.sql.SQLException;

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

    public void onChangePasswordBtnClicked(ActionEvent event) {
        currentField.setVisible(true);
        newField.setVisible(true);
        currentLabel.setVisible(true);
        newLabel.setVisible(true);
        confirmLabel.setVisible(true);
        confirmField.setVisible(true);
    }

    public void onConfirmBtnClicked(ActionEvent event) throws IOException, SQLException {
        checkLabel.setText("");
        passCheckLabel.setText("");
        System.out.println("1: " + currentField.getText() + "   " + currentUser.getPassword());
        if (nameField.getText().equals("") || dobField.getText().equals("") || contactField.getText().equals("")) {
            checkLabel.setText("Please fill up all the fields");
//            if (currentField.isVisible()) {
//
//            }
            System.out.println("2: " + currentField.getText() + "   " + currentUser.getPassword());
        } else {
            boolean check = false;
            if (currentField.isVisible()) {
                if (currentField.getText().equals("") || newField.getText().equals("") || confirmField.getText().equals("")) {
                    passCheckLabel.setText("Please fill up all the fields");
                    check = true;
                } else {
                    if (!(currentField.getText().equals(currentUser.getPassword()))) {
                        System.out.println("3: " + currentField.getText() + "   " + currentUser.getPassword());
                        passCheckLabel.setText("Current password doesn't match");
                        check = true;
                    } else if (!(newField.getText().equals(confirmField.getText()))) {
                        passCheckLabel.setText("New passwords don't match");
                        check = true;
                    }
                }
                System.out.println("STILL IN");
            }
            if(check==false) {
                System.out.println("4: " + currentField.getText() + "   " + currentUser.getPassword());
                checkLabel.setText("");
                passCheckLabel.setText("");
                System.out.println("IN");
                if (!newField.getText().equals(""))
                    currentUser.setPassword(newField.getText());
                DbUtilities dbUtilities = new DbUtilities();
                dbUtilities.updateStudentInfo(currentUser, nameField.getText(), contactField.getText(), dobField.getText());
                System.out.println("Profile Updated successfully");

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("studentDashBoard.fxml"));
                Parent root = loader.load();

                Scene afterLoginScene = new Scene(root);

                StudentDashBoardController studentDashBoardController;
                studentDashBoardController = loader.getController();
                studentDashBoardController.initiateStudentUser(currentUser);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(afterLoginScene);
                window.show();
            }

        }

    }

    public Node getChildNode() {
        return updatePane;
    }
}
