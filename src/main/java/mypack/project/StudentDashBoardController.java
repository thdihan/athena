package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import userPack.User;

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
    void logoutBtnClicked(ActionEvent event) {

    }

    @FXML
    void progressBtnClicked(ActionEvent event) {

    }

    @FXML
    void resultBtnClicked(ActionEvent event) {

    }

}
