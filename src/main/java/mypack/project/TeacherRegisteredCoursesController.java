package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import userPack.Courses;
import userPack.Student;
import userPack.Teacher;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
/** Controller class for registered course view of teacher
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

public class TeacherRegisteredCoursesController {
    @FXML
    private VBox course_box;
    @FXML
    private Label full_name_label;
    private User currentUser;

    @FXML
    Pane infoPane;

    private Teacher currentTeacher;
    private ArrayList<Courses> registered_courses;
    /**
     * To create nodes of course Hbox
     * @param course Course code and title
     * @param credits Course credits
     * @return Hbox as node
     * @throws IOException If problems with input/output
     */
    public Node getHbox(String course, String credits) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("courseRegBox.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        CourseRegBoxController courseRegBoxController=loader.getController();

        courseRegBoxController.initiateCourseReg(course, credits);
        Node childNode= courseRegBoxController.getDoneVbox();
        return childNode;
    }

    /**
     * To initiate the registered course view during scene change
     * @param teacher Teacher object containing their information
     * @param courses List of registered courses of the teacher
     * @param user User object of the teacher
     * @throws SQLException If problems with query
     */

    public void initiateRegisteredCourseView(Teacher teacher, ArrayList<Courses> courses, User user) throws SQLException, IOException {
        currentTeacher=teacher;
        registered_courses=courses;
        currentUser=user;

        for(int i=0 ; i<registered_courses.size() ; i++){
            String courseInfo=registered_courses.get(i).getCode() + ": "+ registered_courses.get(i).getTitle();
            String credit="Credits: "+registered_courses.get(i).getCredit().toString();

            course_box.getChildren().add(getHbox(courseInfo, credit));
            Pane separator = new Pane();
            separator.setPrefHeight(20);
            course_box.getChildren().add(separator);
        }

    }

    /**
     * Course button click function
     * @param event Event of course button click
     */
    public void courseBtnClicked(ActionEvent event) {
        System.out.println("Already in course page");
    }
    /**
     * Marks button click function
     * @param event Event of marks button click
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    public void marksBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_courses, currentUser);
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
        teacherDashBoardController.assignDummyController(currentTeacher, registered_courses, currentUser);
        teacherDashBoardController.logoutBtnClicked(event);
    }
    /**
     * Attendance button click function
     * @param event Event of attendance button click
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    public void takeAttendanceBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_courses, currentUser);
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
        teacherDashBoardController.assignDummyController(currentTeacher, registered_courses, currentUser);
        teacherDashBoardController.dashBoardBtnClicked(event);
    }

    public Pane getPane() {
        return infoPane;
    }

    public  String getUiName() {
        return "Course Page";
    }
}
