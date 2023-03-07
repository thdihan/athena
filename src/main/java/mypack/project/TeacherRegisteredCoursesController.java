package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import userPack.Courses;
import userPack.Student;
import userPack.Teacher;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherRegisteredCoursesController {
    @FXML
    private VBox course_box;
    @FXML
    private Label full_name_label;
    private User currentUser;

    private Teacher currentTeacher;
    private ArrayList<Courses> registered_courses;

    public void initiateRegisteredCourseView(Teacher teacher, ArrayList<Courses> courses, User user) throws SQLException {
        currentTeacher=teacher;
        registered_courses=courses;
        currentUser=user;
        full_name_label.setText(currentTeacher.getName());

        for(int i=0 ; i<registered_courses.size() ; i++){
//            Pane pane =new Pane();
            VBox pane = new VBox();
            VBox vBox=new VBox();
            vBox.setSpacing(2);
            Label course=new Label(registered_courses.get(i).getCode() + ": "+ registered_courses.get(i).getTitle());
            course.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            Label credit=new Label("Credits: "+registered_courses.get(i).getCredit().toString());
            vBox.getChildren().addAll(course, credit);
//            VBox.getMargin(vBox,new Insets(0, 0, 0, 10));
            pane.setStyle("-fx-padding: 0 0 16px 50px;");
            pane.getChildren().add(vBox);

            course_box.getChildren().add(pane);
        }

    }


    public void courseBtnClicked(ActionEvent event) {
        System.out.println("Already in course page");
    }

    public void marksBtnClicked(ActionEvent event) {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_courses, currentUser);
        teacherDashBoardController.addMarksBtnClicked(event);
    }

    public void logoutBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_courses, currentUser);
        teacherDashBoardController.logoutBtnClicked(event);
    }

    public void takeAttendanceBtnClicked(ActionEvent event){
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_courses, currentUser);
        teacherDashBoardController.takeAttendanceBtnClicked(event);
    }

    public void dashBoardBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_courses, currentUser);
        teacherDashBoardController.dashBoardBtnClicked(event);
    }
}
