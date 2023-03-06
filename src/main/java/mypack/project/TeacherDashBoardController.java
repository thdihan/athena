package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import userPack.Courses;
import userPack.Teacher;
import userPack.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherDashBoardController {


    private User currentUser; //this object will contain the email, pass and type of the user for querying
    private Teacher currentTeacher;
    private ArrayList<Courses> registered_course;
    @FXML
    private Label fullname_view;
    @FXML
    private Label side_fullname_view;
    @FXML
    private Label email_view;
    @FXML
    private Label dob_view;
    @FXML
    private Label id_view;
    @FXML
    private Label dept_view;
    @FXML
    public User getCurrentUser() {
        return currentUser;
    }

    public Teacher getCurrentTeacher() {
        return currentTeacher;
    }

    public ArrayList<Courses> getRegistered_course() {
        return registered_course;
    }

    public void initiateTeacherUser(User newUser) throws SQLException {
        currentUser = newUser;
        DbUtilities dbUtilities = new DbUtilities();
        currentTeacher = dbUtilities.getTeacherInfo(currentUser.getEmail());

        //putting the values in labels
        email_view.setText(currentUser.getEmail());
        fullname_view.setText(currentTeacher.getName());
        dob_view.setText(currentTeacher.getDob().toString());
        dept_view.setText(currentTeacher.getDept());
        id_view.setText(currentTeacher.getId());
        side_fullname_view.setText(currentTeacher.getName());

        //checking for registered courses( will be empty if not registered )
//        registered_course = dbUtilities.getRegisteredCourses(currentStudent.getId(), currentStudent.getSemester());
    }

    public void logoutBtnClicked(ActionEvent event) {
    }

    public void progressBtnClicked(ActionEvent event) {
    }

    public void courseBtnClicked(ActionEvent event) {
    }
}
