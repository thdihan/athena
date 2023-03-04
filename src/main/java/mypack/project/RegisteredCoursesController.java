package mypack.project;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import userPack.Courses;
import userPack.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public class RegisteredCoursesController {
    @FXML
    private VBox course_box;

    private Student currentStudent;
    private ArrayList<Courses> registered_courses;

    public void initiateRegisteredCourseView(Student student, ArrayList<Courses> courses) throws SQLException {
        currentStudent=student;
        registered_courses=courses;

    }
}
