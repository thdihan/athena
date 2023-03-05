package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import userPack.Courses;
import userPack.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseRegPageController {
    Student currentStudent;

    @FXML
    private VBox course_box;
    ArrayList<Courses> offered_courses;


    public void initiateStudent(Student newStudent) throws SQLException {
        currentStudent = newStudent;
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
//    public void registerCourseBtnClicked(ActionEvent event, VBox vBox, Student currentStudent, ArrayList<Courses> offered_courses) throws SQLException {
//
//    }

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
        registeredCoursesController.initiateRegisteredCourseView(currentStudent, registered_course);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(registeredCourseScene);
        window.show();
    }

}
