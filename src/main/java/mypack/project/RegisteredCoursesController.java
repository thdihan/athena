package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import userPack.Courses;
import userPack.Student;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisteredCoursesController {
    @FXML
    private VBox course_box;
    @FXML
    private Label full_name_label;
    private User currentUser;

    private Student currentStudent;
    private ArrayList<Courses> registered_courses;

    public void initiateRegisteredCourseView(Student student, ArrayList<Courses> courses, User user) throws SQLException {
        currentStudent=student;
        registered_courses=courses;
        currentUser=user;
        full_name_label.setText(currentStudent.getName());

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

    public void progressBtnClicked (ActionEvent event) throws SQLException, IOException {
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_courses, currentUser);
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
        studentDashBoardController.assignDummyController(currentStudent, registered_courses, currentUser);
        studentDashBoardController.logoutBtnClicked(event);
    }
}
