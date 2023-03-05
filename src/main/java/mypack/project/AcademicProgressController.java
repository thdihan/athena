package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import userPack.Courses;
import userPack.Student;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SplittableRandom;

public class AcademicProgressController {
    @FXML
    private Label full_name_label;
    private Student currentStudent;
    private ArrayList<Courses> registered_course;
    private User currentUser;

    void initiateAcademicProgressView(Student currentStudent, ArrayList<Courses> registered_courses, User user){
        this.currentStudent=currentStudent;
        this.registered_course=registered_courses;
        currentUser=user;

        full_name_label.setText(currentStudent.getName());
        //REMAINING: Functionalities of the academic progress
    }


    public void dashboardBtnClicked(ActionEvent event) throws IOException,SQLException{
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_course, currentUser);
        studentDashBoardController.dashboardBtnClicked(event);

    }
    public void progressBtnClicked (ActionEvent event){
        System.out.println("Currently in progress page");
    }
    public void courseBtnClicked (ActionEvent event) throws SQLException, IOException {
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_course, currentUser);
        studentDashBoardController.courseBtnClicked(event);
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
