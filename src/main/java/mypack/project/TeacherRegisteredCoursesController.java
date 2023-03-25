package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    private Teacher currentTeacher;
    private ArrayList<Courses> registered_courses;

    /**
     * To initiate the registered course view during scene change
     * @param teacher Teacher object containing their information
     * @param courses List of registered courses of the teacher
     * @param user User object of the teacher
     * @throws SQLException If problems with query
     */
    public void initiateRegisteredCourseView(Teacher teacher, ArrayList<Courses> courses, User user) throws SQLException {
        currentTeacher=teacher;
        registered_courses=courses;
        currentUser=user;
        full_name_label.setText(currentTeacher.getName());

        for(int i=0 ; i<registered_courses.size() ; i++){
//            Pane pane =new Pane();
            VBox pane = new VBox();
            VBox vBox=new VBox();
            vBox.setSpacing(2);
            Label course=new Label(registered_courses.get(i).getCode() + ": "+ registered_courses.get(i).getTitle());
            course.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            Label credit=new Label("Credits: "+registered_courses.get(i).getCredit().toString());
            vBox.getChildren().addAll(course, credit);
//            VBox.getMargin(vBox,new Insets(0, 0, 0, 10));
            pane.setStyle("-fx-padding: 0 0 16px 50px;");
            pane.getChildren().add(vBox);

            course_box.getChildren().add(pane);
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
}
