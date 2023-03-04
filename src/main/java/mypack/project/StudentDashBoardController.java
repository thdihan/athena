package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import userPack.User;

import java.io.IOException;

public class StudentDashBoardController {


    private User currentUser; //this object will contain the email, pass and type of the user for querying

    @FXML
    private Button course_btn;

    @FXML
    private Label dept_view;

    @FXML
    private Label dob_view;

    @FXML
    private Label email_view;

    @FXML
    private Label fullname_view;

    @FXML
    private Label id_view;

    @FXML
    private Button logout_btn;

    @FXML
    private Button progress_btn;

    @FXML
    private Button result_btn;

    @FXML
    private Label semester_view;

    public User getCurrentUser() {
        return currentUser;
    }

    public void initiateStudentUser(User newUser) {
        currentUser = newUser;
        email_view.setText(currentUser.getEmail());
    }

    @FXML
    void courseBtnClicked(ActionEvent event) {

    }

    @FXML
    void logoutBtnClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("loginPage.fxml"));
        Parent root = loader.load();

        Scene afterLoginScene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(afterLoginScene);
        window.show();
    }

    @FXML
    void progressBtnClicked(ActionEvent event) {

    }

    @FXML
    void resultBtnClicked(ActionEvent event) {

    }

}
