package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import userPack.Courses;
import userPack.Student;

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

    public void registerCourseBtnClicked(ActionEvent event) throws SQLException {
        DbUtilities dbUtilities=new DbUtilities();
        dbUtilities.registerCourses(course_box, currentStudent, offered_courses);
        System.out.println("Registered");
    }
}
