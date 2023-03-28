package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import userPack.Courses;
import userPack.Student;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/** Controller class for course registration page
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

public class StudentCourseRegPageController {
    Student currentStudent;
    private User currentUser;

    @FXML
    private VBox course_box;
    @FXML
    private Label full_name_label;
    ArrayList<Courses> offered_courses;
    ArrayList<Courses> registered_course;

    public Node getHbox(String course) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("courseRegBox.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        CourseRegBoxController courseRegBoxController=loader.getController();

        courseRegBoxController.initiateCourseReg(course, "-1");
        Node childNode= courseRegBoxController.getCheckHbox();
        return childNode;
    }

    /**
     * For initiating the data and course registration page view
     * @param newStudent Student currently logged in
     * @param user User currently logged in
     * @throws SQLException If problems with query
     */
    public void initiateStudent(Student newStudent, User user) throws SQLException, IOException {
        currentStudent = newStudent;
        currentUser=user;
        full_name_label.setText(currentStudent.getName());

        DbUtilities dbUtilities = new DbUtilities();
        offered_courses = dbUtilities.getOfferedCourses(currentStudent.getDept(), user.getType(), currentStudent.getSemester());


        for(int i=0 ; i<offered_courses.size() ; i++){
            Courses course=offered_courses.get(i);
            String course_name=course.getCode()+": "+course.getTitle();
//            CheckBox option=new CheckBox(course_name);
            course_box.getChildren().add(getHbox(course_name));
        }
    }

    /**
     * Function which registers the selected courses on button click
     * @param event Event of register button click
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    @FXML
    public void registerCourseBtnClicked(ActionEvent event) throws SQLException, IOException {
        DbUtilities dbUtilities=new DbUtilities();
        ArrayList<Courses>registered_course=dbUtilities.registerCourses(course_box, currentStudent, offered_courses);
        System.out.println("Registered");

        StudentRegisteredCoursesController studentRegisteredCoursesController;


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("registeredCourses.fxml"));
        Parent root = loader.load();

        Scene registeredCourseScene = new Scene(root);
        studentRegisteredCoursesController =loader.getController();
        studentRegisteredCoursesController.initiateRegisteredCourseView(currentStudent, registered_course, currentUser);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(registeredCourseScene);
        window.show();
    }
    /**
     * Dashboard button click function
     * @param event Event of button click
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    public void dashboardBtnClicked(ActionEvent event) throws IOException,SQLException{
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_course, currentUser);
        studentDashBoardController.dashboardBtnClicked(event);

    }
    /**
     * Progress button click function
     * @param event Event for progress button click
     */
    public void progressBtnClicked (ActionEvent event) throws SQLException, IOException {
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_course, currentUser);
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
        studentDashBoardController.assignDummyController(currentStudent, registered_course, currentUser);
        studentDashBoardController.logoutBtnClicked(event);
    }

}
