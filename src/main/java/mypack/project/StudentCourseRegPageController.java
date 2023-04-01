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
    Pane infoPane;
    ArrayList<Courses> offered_courses;
    ArrayList<Courses> registered_course;

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
     * For initiating the data and course registration page view
     * @param newStudent Student currently logged in
     * @param user User currently logged in
     * @throws SQLException If problems with query
     */
    public void initiateStudent(Student newStudent, User user) throws SQLException, IOException {
        currentStudent = newStudent;
        currentUser=user;

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


        FXMLLoader loader = new FXMLLoader(getClass().getResource("registeredCourses.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        Node childNode = null;

        StudentRegisteredCoursesController studentRegisteredCoursesController;
        studentRegisteredCoursesController =loader.getController();
        studentRegisteredCoursesController.initiateRegisteredCourseView(currentStudent, registered_course, currentUser);

//            ui_name.setText(studentRegisteredCoursesController.getUiName());
        childNode=studentRegisteredCoursesController.getPane();
        infoPane.getChildren().clear();
        infoPane.getChildren().add(childNode);
    }





    public Pane getPane() {
        return infoPane;
    }

    public  String getUiName() {
        return "Course Registration Page";
    }

}
