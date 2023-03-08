package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import userPack.Courses;
import userPack.Student;
import userPack.Teacher;
import userPack.User;

import java.io.IOException;
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
        registered_course = dbUtilities.getTeacherRegisteredCourses(currentTeacher.getId());
    }

    public void assignDummyController(Teacher teacher, ArrayList<Courses> registered_course, User user){
        currentTeacher=teacher;
        this.registered_course=registered_course;
        currentUser=user;

    }

    void changeSceneInDashboard(ActionEvent event, String fxml, String choice) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();

        Scene afterLoginScene = new Scene(root);

        //declaring all the controller
        TeacherCourseRegPageController teacherCourseRegPageController;
        TeacherRegisteredCoursesController teacherRegisteredCoursesController;
        TeacherMarksController teacherMarksController;

        /*--------Marks and attendence Controller Remaining-------------*/

        if (choice.equals("regPage")) {
            teacherCourseRegPageController = loader.getController();
            teacherCourseRegPageController.initiateTeacher(currentTeacher, currentUser);


        } else if (choice.equals("regDonePage")) {
            teacherRegisteredCoursesController =loader.getController();
            teacherRegisteredCoursesController.initiateRegisteredCourseView(currentTeacher, registered_course, currentUser);


        } else if (choice.equals("logoutPage")) {
            System.out.println("Logging out");
        }
        else if (choice.equals("teacherDashBoard")) {
            TeacherDashBoardController teacherDashBoardController;
            teacherDashBoardController = loader.getController();
            teacherDashBoardController.initiateTeacherUser(currentUser);
        }else if (choice.equals("teacherMarks")) {
            teacherMarksController = loader.getController();
            teacherMarksController.initiateTeacherMarksController(currentTeacher,currentUser, registered_course);
        }
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(afterLoginScene);
        window.show();
    }

    @FXML
    void logoutBtnClicked(ActionEvent event) throws IOException, SQLException {

        changeSceneInDashboard(event, "loginPage.fxml", "logoutPage");
    }


    public void courseBtnClicked(ActionEvent event) throws SQLException, IOException {
        if (registered_course==null || registered_course.isEmpty()) {
            changeSceneInDashboard(event, "teacherCourseRegPage.fxml", "regPage");
        }
        else {
            changeSceneInDashboard(event, "teacherRegisteredCourse.fxml", "regDonePage");
        }
    }

    public void addMarksBtnClicked(ActionEvent event) throws SQLException, IOException {
        changeSceneInDashboard(event, "teacherMarks.fxml", "teacherMarks");
    }

    public void takeAttendanceBtnClicked(ActionEvent event){
        System.out.println("Not implemented taking attendance");
    }

    public void dashBoardBtnClicked(ActionEvent event) throws SQLException, IOException {
        changeSceneInDashboard(event, "teacherDashBoard.fxml", "teacherDashBoard");
    }
}
