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
import userPack.Teacher;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherCourseRegPageController {
    Teacher currentTeacher;
    private User currentUser;

    @FXML
    private VBox course_box;
    @FXML
    private Label full_name_label;
    ArrayList<Courses> offered_courses;
    ArrayList<Courses> registered_course;

    public void initiateTeacher(Teacher newTeacher, User user) throws SQLException {
        currentTeacher=newTeacher;
//        System.out.println(currentTeacher.getDob());
        currentUser=user;
        full_name_label.setText(currentTeacher.getName());

        DbUtilities dbUtilities = new DbUtilities();
        offered_courses = dbUtilities.getOfferedCourses(currentTeacher.getDept());


        for(int i=0 ; i<offered_courses.size() ; i++){
            Courses course=offered_courses.get(i);
            String course_name=course.getCode()+": "+course.getTitle();
            CheckBox option=new CheckBox(course_name);
            course_box.getChildren().add(option);
        }
    }


    public void registerCourseBtnClicked(ActionEvent event) throws SQLException, IOException {
        DbUtilities dbUtilities=new DbUtilities();
        ArrayList<Courses>registered_course=dbUtilities.registerTeacherCourses(course_box, currentTeacher, offered_courses);
        System.out.println("Registered");

        TeacherRegisteredCoursesController teacherRegisteredCoursesController;


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("teacherRegisteredCourse.fxml"));
        Parent root = loader.load();

        Scene registeredCourseScene = new Scene(root);
        teacherRegisteredCoursesController =loader.getController();
        teacherRegisteredCoursesController.initiateRegisteredCourseView(currentTeacher, registered_course, currentUser);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(registeredCourseScene);
        window.show();
    }


    public void courseBtnClicked(ActionEvent event) {
        System.out.println("Already in course page");
    }
    public void marksBtnClicked(ActionEvent event) {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.addMarksBtnClicked(event);
    }

    public void logoutBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.logoutBtnClicked(event);
    }

    public void takeAttendanceBtnClicked(ActionEvent event){
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.takeAttendanceBtnClicked(event);
    }

    public void dashBoardBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.dashBoardBtnClicked(event);
//        System.out.println(currentTeacher.getDob());
    }
}
