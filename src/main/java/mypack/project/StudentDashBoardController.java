package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import userPack.Courses;
import userPack.Student;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDashBoardController {


    private User currentUser; //this object will contain the email, pass and type of the user for querying

    private Student currentStudent;

    @FXML
    private Button course_btn;

    @FXML
    private Label dept_view;

    @FXML
    private Label dob_view;

    @FXML
    private Label email_view;

    @FXML
    private Label fullname_view;

    @FXML Label side_fullname_view;

    @FXML
    private Label id_view;

    @FXML
    private Button logout_btn;

    @FXML
    private Button progress_btn;

    @FXML
    private Button result_btn;

    @FXML
    private Label semester_view;
    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void initiateCurrentStudent(){

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void initiateStudentUser(User newUser) throws SQLException {
        currentUser = newUser;
        DbUtilities dbUtilities=new DbUtilities();
        currentStudent=dbUtilities.getStudentInfo(currentUser.getEmail());

        //putting the values in labels
        email_view.setText(currentUser.getEmail());
        fullname_view.setText(currentStudent.getName());
        dob_view.setText(currentStudent.getDob().toString());
        dept_view.setText(currentStudent.getDept());
        id_view.setText(currentStudent.getId());
        semester_view.setText(currentStudent.getSemester());
        side_fullname_view.setText(currentStudent.getName());
    }

    @FXML
    void courseBtnClicked(ActionEvent event) throws SQLException, IOException {
        /*-------------Need to check if the student table has courses registered or not---------*/
        DbUtilities dbUtilities =new DbUtilities();
        ArrayList<Courses> registered_course=dbUtilities.getRegisteredCourses(currentStudent.getId(), currentStudent.getSemester());
        if(registered_course==null) {
            changeSceneInDashboard(event, "courseRegPage.fxml", "regpage");
        }
        else {
//            changeSceneInDashboard(event, "registeredCourses.fxml", "regdone");
            RegisteredCoursesController registeredCoursesController;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("registeredCourses.fxml"));
            Parent root = loader.load();

            Scene afterLoginScene = new Scene(root);

            registeredCoursesController=loader.getController();
            registeredCoursesController.initiateRegisteredCourseView(currentStudent, registered_course);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(afterLoginScene);
            window.show();
        }
    }

    void changeSceneInDashboard(ActionEvent event, String fxml, String choice) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();

        Scene afterLoginScene = new Scene(root);

        //declaring all the controller
        CourseRegPageController courseRegPageController;
        RegisteredCoursesController registeredCoursesController;

        if(choice.equals("regpage")){
            courseRegPageController=loader.getController();
            courseRegPageController.initiateStudent(currentStudent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(afterLoginScene);
            window.show();

        } else if (choice.equals("progress")) {
            System.out.println("Empty progress controller");
//            courseRegPageController=loader.getController();
//            courseRegPageController.initiateStudent(currentStudent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(afterLoginScene);
            window.show();

        } else if (choice.equals("result")) {
            System.out.println("Empty result controller");

        } else if (choice.equals("logout")) {
            System.out.println("Logging out");
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(afterLoginScene);
            window.show();
        }


    }

    /*--------------may need to pass the user object for querying--------------*/
    @FXML
    void logoutBtnClicked(ActionEvent event) throws IOException, SQLException {

        changeSceneInDashboard(event, "loginPage.fxml", "logout");
    }

    @FXML
    void progressBtnClicked(ActionEvent event) throws IOException, SQLException {
//        System.out.println(currentUser.getEmail()); //works
        changeSceneInDashboard(event, "academicProgress.fxml", "progress");
    }

    @FXML
    void resultBtnClicked(ActionEvent event) {

    }

}
