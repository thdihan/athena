package mypack.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import userPack.Courses;
import userPack.Student;
import userPack.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class RegisteredCoursesController {
    @FXML
    private VBox course_box;
    private User currentUser;

    private Student currentStudent;
    private ArrayList<Courses> registered_courses;

    public void initiateRegisteredCourseView(Student student, ArrayList<Courses> courses, User user) throws SQLException {
        currentStudent=student;
        registered_courses=courses;
        currentUser=user;

        for(int i=0 ; i<registered_courses.size() ; i++){
            Pane pane =new Pane();
            VBox vBox=new VBox();
            vBox.setSpacing(2);
            Label course=new Label(registered_courses.get(i).getCode() + ": "+ registered_courses.get(i).getTitle());
            Label credit=new Label("Credits: "+registered_courses.get(i).getCredit().toString());
            vBox.getChildren().addAll(course, credit);
            pane.getChildren().add(vBox);
            course_box.getChildren().add(pane);
        }
    }
}
