package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import userPack.Courses;
import userPack.Student;
import userPack.Teacher;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
/** Controller class for course registration of teacher
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

public class TeacherCourseRegPageController {
    Teacher currentTeacher;
    private User currentUser;

    @FXML
    private VBox course_box;

    @FXML
    Pane infoPane;
    ArrayList<Courses> offered_courses;
    ArrayList<Courses> registered_course;
    @FXML
    Label warningLabel;

    @FXML
    Pane warning_box;
    /**
     * To create nodes of course Hbox
     * @param course Course code and title
     * @return Hbox as node
     * @throws IOException If problems with input/output
     */
    public Node getHbox(String course) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("courseRegBox.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        CourseRegBoxController courseRegBoxController=loader.getController();

        courseRegBoxController.initiateCourseReg(course, "-1");
        Node childNode= courseRegBoxController.getCheckHbox();
        return childNode;
    }

    /**
     * To initiate course registration view when scene is changed
     * @param newTeacher Teacher object containing teacher information
     * @param user User object of the teacher
     * @throws SQLException If problems with query
     */
    public void initiateTeacher(Teacher newTeacher, User user) throws SQLException, IOException {
        currentTeacher=newTeacher;
//        System.out.println(currentTeacher.getDob());
        currentUser=user;

        DbUtilities dbUtilities = new DbUtilities();
        offered_courses = dbUtilities.getOfferedCourses(currentTeacher.getDept(), user.getType(), "None");


        for(int i=0 ; i<offered_courses.size() ; i++){
            Courses course=offered_courses.get(i);
            String course_name=course.getCode()+": "+course.getTitle();
//            CheckBox option=new CheckBox(course_name);
            course_box.getChildren().add(getHbox(course_name));
        }
    }

    /**
     * To register courses when button is clicked
     * @param event Event of register course button click
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void registerCourseBtnClicked(ActionEvent event) throws SQLException, IOException {
        DbUtilities dbUtilities=new DbUtilities();
        ArrayList<Courses>registered_course=dbUtilities.registerTeacherCourses(course_box, currentTeacher, offered_courses);
        if(registered_course.isEmpty()){
//            warningLabel.setStyle("-fx-background-color: #faafb6;-fx-border-color: red;-fx-background-radius: 15px; -fx-border-radius: 15px;");

            warning_box.setVisible(true);
            warningLabel.setText("No course selected");

        }
        else {
            warning_box.setVisible(false);
            System.out.println("Registered");

            TeacherRegisteredCoursesController teacherRegisteredCoursesController;


            FXMLLoader loader = new FXMLLoader(getClass().getResource("teacherRegisteredCourse.fxml"));
            loader.load(); //loader.load() must be used otherwise controller isn't created
            Node childNode = null;

            teacherRegisteredCoursesController = loader.getController();
            teacherRegisteredCoursesController.initiateRegisteredCourseView(currentTeacher, registered_course, currentUser);

//            ui_name.setText(studentRegisteredCoursesController.getUiName());
            childNode = teacherRegisteredCoursesController.getPane();
            infoPane.getChildren().clear();
            infoPane.getChildren().add(childNode);
        }
    }

    /**
     * Course button click function
     * @param event
     */
    public void courseBtnClicked(ActionEvent event) {
        System.out.println("Already in course page");
    }
    /**
     * Marks button click function
     * @param event Event for marks button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void marksBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.addMarksBtnClicked(event);
    }
    /**
     * Logout button click function
     * @param event Event for logout button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void logoutBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.logoutBtnClicked(event);
    }
    /**
     * Attendance button click function
     * @param event Event for attendance button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void takeAttendanceBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.takeAttendanceBtnClicked(event);
    }
    /**
     * Dashboard button click function
     * @param event Event of button click
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    public void dashBoardBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.dashBoardBtnClicked(event);
//        System.out.println(currentTeacher.getDob());
    }

    public Pane getPane() {
        return infoPane;
    }

    public  String getUiName() {
        return "Course Page";
    }
}
