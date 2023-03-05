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

public class CourseRegPageController {
    Student currentStudent;
    private User currentUser;

    @FXML
    private VBox course_box;
    @FXML
    private Label full_name_label;
    ArrayList<Courses> offered_courses;
    ArrayList<Courses> registered_course;


    public void initiateStudent(Student newStudent, User user) throws SQLException {
        currentStudent = newStudent;
        currentUser=user;
        full_name_label.setText(currentStudent.getName());

        DbUtilities dbUtilities = new DbUtilities();
        offered_courses = dbUtilities.getOfferedCourses(currentStudent.getDept());


        for(int i=0 ; i<offered_courses.size() ; i++){
            Courses course=offered_courses.get(i);
            String course_name=course.getCode()+": "+course.getTitle();
            CheckBox option=new CheckBox(course_name);
            course_box.getChildren().add(option);
        }
    }
    @FXML
    public void registerCourseBtnClicked(ActionEvent event) throws SQLException, IOException {
        DbUtilities dbUtilities=new DbUtilities();
        ArrayList<Courses>registered_course=dbUtilities.registerCourses(course_box, currentStudent, offered_courses);
        System.out.println("Registered");

        RegisteredCoursesController registeredCoursesController;

        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("registeredCourses.fxml"));
        Parent root = loader.load();

        Scene registeredCourseScene = new Scene(root);
        registeredCoursesController=loader.getController();
        registeredCoursesController.initiateRegisteredCourseView(currentStudent, registered_course, currentUser);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(registeredCourseScene);
        window.show();
    }

    public void dashboardBtnClicked(ActionEvent event) throws IOException,SQLException{
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_course, currentUser);
        studentDashBoardController.dashboardBtnClicked(event);

    }
    public void progressBtnClicked (ActionEvent event) throws SQLException, IOException {
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_course, currentUser);
        studentDashBoardController.progressBtnClicked(event);
    }
    public void courseBtnClicked (ActionEvent event) throws SQLException, IOException {
        System.out.println("Currently in course registration page");
    }
    public void resultBtnClicked (ActionEvent event){
        System.out.println("Result btn for progress not done");
    }
    public void logoutBtnClicked (ActionEvent event) throws SQLException, IOException {
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_course, currentUser);
        studentDashBoardController.logoutBtnClicked(event);
    }

}
