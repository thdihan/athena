package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import userPack.Courses;
import userPack.Student;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
/** Controller class for student dashboard
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */
public class StudentDashBoardController {


    private User currentUser; //this object will contain the email, pass and type of the user for querying

    private Student currentStudent;
    private ArrayList<Courses> registered_course;

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

    @FXML
    Label side_fullname_view;

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
    @FXML
    private Pane infoPane;

    public Student getCurrentStudent() {
        return currentStudent;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * To initiate the data and personal information of the logged in student
     * @param newUser
     * @throws SQLException
     */
    public void initiateStudentUser(User newUser) throws SQLException, IOException {
        currentUser = newUser;
        DbUtilities dbUtilities = new DbUtilities();
        currentStudent = dbUtilities.getStudentInfo(currentUser.getEmail());
        System.out.println(currentStudent.getEmail());
//        infoPane.getChildren().clear();

        //initiating student info view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("studentInfo.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        StudentInfoController studentInfoController = loader.getController();
        studentInfoController.initiateStudentPane(currentStudent, newUser);
        Node childNode=studentInfoController.getChildNode();
        infoPane.getChildren().add(childNode);
        side_fullname_view.setText(currentStudent.getName());

        //checking for registered courses( will be empty if not registered )
        registered_course = dbUtilities.getStudentRegisteredCourses(currentStudent.getId(), currentStudent.getSemester());
    }

    /**
     * This function will be used for creating a dummy controller which will be used to call the onBtnClick functions on other student controllers
     * @param student Student currently logged in
     * @param registered_course List of registered courses
     * @param user User currently logged in
     */
    public void assignDummyController(Student student, ArrayList<Courses> registered_course, User user){
        currentStudent=student;
        this.registered_course=registered_course;
        currentUser=user;
    }
    /**
     * Course button click function
     * @param event Event for course button click
     * @throws SQLException If problems with query
     * @throws IOException If problmes with input/output
     */
    @FXML
    void courseBtnClicked(ActionEvent event) throws SQLException, IOException {
        /*-------------Need to check if the student table has courses registered or not---------*/
        /*-------Added null condition for error checking---------*/
        if (registered_course==null || registered_course.isEmpty()) {
            changeSceneInDashboard(event, "courseRegPage.fxml", "regPage");
        } else {
            changeSceneInDashboard(event, "registeredCourses.fxml", "regDonePage");
        }
    }

    /**
     * For changing the scenes in the dashboard
     * @param event Event of button click for scene change
     * @param fxml File name of the UI of a scene
     * @param choice Option containing type of scene (regPage, progressPage, regDonePage, resultPage, logoutPage, studentDashBoard)
     * @throws IOException If problems with input output
     * @throws SQLException If problems with query
     */
    void changeSceneInDashboard(ActionEvent event, String fxml, String choice) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();

        Scene afterLoginScene = new Scene(root);

        //declaring all the controller
        StudentCourseRegPageController studentCourseRegPageController;
        StudentRegisteredCoursesController studentRegisteredCoursesController;
        StudentAcademicProgressController studentAcademicProgressController;
        StudentWorkspacePageController studentWorkspacePageController;
        /*--------Result View Controller Remaining-------------*/

        if (choice.equals("regPage")) {
            studentCourseRegPageController = loader.getController();
            studentCourseRegPageController.initiateStudent(currentStudent, currentUser);

        } else if (choice.equals("progressPage")) {
//            System.out.println("Empty progress controller");
            studentAcademicProgressController =loader.getController();
            studentAcademicProgressController.initiateAcademicProgressView(currentStudent, registered_course, currentUser);

        } else if (choice.equals("regDonePage")) {
            studentRegisteredCoursesController =loader.getController();
            studentRegisteredCoursesController.initiateRegisteredCourseView(currentStudent, registered_course, currentUser);

        } else if (choice.equals("resultPage")) {
            System.out.println("Empty result controller");

        } else if (choice.equals("logoutPage")) {
            System.out.println("Logging out");
        }
        else if (choice.equals("studentDashBoard")) {
            StudentDashBoardController studentDashBoardController;
            studentDashBoardController = loader.getController();
            studentDashBoardController.initiateStudentUser(currentUser);
        }
        else if(choice.equals("workspacePage")){
            studentWorkspacePageController = loader.getController();
            studentWorkspacePageController.initiateRegisteredCourseView(currentStudent, registered_course, currentUser);
        }
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(afterLoginScene);
        window.show();
    }

    /*--------------may need to pass the user object for querying--------------*/
    /**
     * Logout button click function
     * @param event Event for logout button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    @FXML
    void logoutBtnClicked(ActionEvent event) throws IOException, SQLException {

        changeSceneInDashboard(event, "loginPage.fxml", "logoutPage");
    }
    /**
     * Dashboard button click function
     * @param event Event of button click
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    void dashboardBtnClicked(ActionEvent event) throws IOException, SQLException {
        changeSceneInDashboard(event, "studentDashBoard.fxml", "studentDashBoard");
    }
    /**
     * Progress button click function
     * @param event Event for progress button click
     */
    @FXML
    void progressBtnClicked(ActionEvent event) throws IOException, SQLException {
//        System.out.println(currentUser.getEmail()); //works
        changeSceneInDashboard(event, "academicProgress.fxml", "progressPage");
    }
    /**
     * Result button click function
     * @param event Event for result button clicked
     */
    @FXML
    void resultBtnClicked(ActionEvent event) {
        System.out.println("Result button not assigned");
    }

    @FXML
    void workspaceBtnClicked(ActionEvent event) throws SQLException, IOException {
        changeSceneInDashboard(event, "studentWorkspacePage.fxml", "workspacePage");
    }
}
