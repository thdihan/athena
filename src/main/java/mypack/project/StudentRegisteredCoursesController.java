package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import userPack.Courses;
import userPack.Student;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/** Controller class for registered course page
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

public class StudentRegisteredCoursesController {
    @FXML
    private VBox course_box;
    @FXML
    private Label full_name_label;
    private User currentUser;

    private Student currentStudent;
    private ArrayList<Courses> registered_courses;
    public Node getHbox(String course, String credits) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("courseRegBox.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        CourseRegBoxController courseRegBoxController=loader.getController();

        courseRegBoxController.initiateCourseReg(course, credits);
        Node childNode= courseRegBoxController.getDoneVbox();
        return childNode;
    }

    /**
     * To initiate the data and registered course view page
     * @param student Student currently logged in
     * @param courses List of registered courses
     * @param user User currenly logged in
     * @throws SQLException If problems with query
     */
    public void initiateRegisteredCourseView(Student student, ArrayList<Courses> courses, User user) throws SQLException, IOException {
        currentStudent=student;
        registered_courses=courses;
        currentUser=user;
        full_name_label.setText(currentStudent.getName());

        for(int i=0 ; i<registered_courses.size() ; i++){
            String courseInfo=registered_courses.get(i).getCode() + ": "+ registered_courses.get(i).getTitle();
            String credit="Credits: "+registered_courses.get(i).getCredit().toString();
            course_box.getChildren().add(getHbox(courseInfo, credit));
        }

    }

    /**
     * Dashboard button click function
     * @param event Event of button click
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    public void dashboardBtnClicked(ActionEvent event) throws IOException,SQLException{
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_courses, currentUser);
        studentDashBoardController.dashboardBtnClicked(event);

    }
    /**
     * Progress button click function
     * @param event Event for progress button click
     */
    public void progressBtnClicked (ActionEvent event) throws SQLException, IOException {
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_courses, currentUser);
        studentDashBoardController.progressBtnClicked(event);
    }
    /**
     * Function for course button click
     * @param event Event of course button click
     */
    public void courseBtnClicked (ActionEvent event) {
        System.out.println("Currently in course registration page");
    }
    /**
     * Result button click function
     * @param event Event for result button clicked
     */
    public void resultBtnClicked (ActionEvent event){
        System.out.println("Result btn for progress not done");
    }
    /**
     * Logout button click function
     * @param event Event for logout button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void logoutBtnClicked (ActionEvent event) throws SQLException, IOException {
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_courses, currentUser);
        studentDashBoardController.logoutBtnClicked(event);
    }
}
