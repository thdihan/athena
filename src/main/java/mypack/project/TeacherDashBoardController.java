package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import userPack.Courses;
import userPack.Student;
import userPack.Teacher;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
/** Controller class for teacher dashboard
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

public class TeacherDashBoardController {


    private User currentUser; //this object will contain the email, pass and type of the user for querying
    private Teacher currentTeacher;
    private ArrayList<Courses> registered_course;
    @FXML
    private Label fullname_view;
    @FXML
    private Label side_fullname_view;
    @FXML
    private Label email_view;
    @FXML
    private Label dob_view;
    @FXML
    private Label id_view;
    @FXML
    private Label dept_view;
    @FXML
    private Pane infoPane;
    @FXML
    Label ui_name;
    @FXML
    public User getCurrentUser() {
        return currentUser;
    }

    public Teacher getCurrentTeacher() {
        return currentTeacher;
    }

    public ArrayList<Courses> getRegistered_course() {
        return registered_course;
    }

    /**
     * To initiate the teacher dashboard during scene change
     * @param newUser User object of the teacher
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void initiateTeacherUser(User newUser) throws SQLException, IOException {
        currentUser = newUser;
        DbUtilities dbUtilities = new DbUtilities();
        currentTeacher = dbUtilities.getTeacherInfo(currentUser.getEmail());
        side_fullname_view.setText(currentTeacher.getName());

        //loading the teacherInfo fxml as child to the pane
        FXMLLoader loader = new FXMLLoader(getClass().getResource("teacherInfo.fxml"));
        Node child=loader.load(); //loader.load() must be used otherwise controller isn't created

        TeacherInfoController teacherInfoController=loader.getController();
        teacherInfoController.initiateTeacherPane(currentTeacher, newUser);

        Node childNode=teacherInfoController.getChildNode();
        infoPane.getChildren().add(childNode);

        //checking for registered courses( will be empty if not registered )
        registered_course = dbUtilities.getTeacherRegisteredCourses(currentTeacher.getId());
    }

    /**
     * To resuse the buttons of dashboard, the data inserted in the dashboard controller
     * @param teacher Teacher object containing their information
     * @param registered_course List of registered courses of the teacher (if any)
     * @param user User object of the teacher
     */
    public void assignDummyController(Teacher teacher, ArrayList<Courses> registered_course, User user){
        currentTeacher=teacher;
        this.registered_course=registered_course;
        currentUser=user;

    }



    void changeSceneWithInfoPane(ActionEvent event, String fxml, String choice) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        Node childNode = null;

        //declaring all the controller
        TeacherCourseRegPageController teacherCourseRegPageController;
        TeacherRegisteredCoursesController teacherRegisteredCoursesController;
        TeacherMarksController teacherMarksController;
        TeacherAttendanceController teacherAttendanceController;

        /*--------Result View Controller Remaining-------------*/

        if (choice.equals("regPage")) {
            teacherCourseRegPageController = loader.getController();
            teacherCourseRegPageController.initiateTeacher(currentTeacher, currentUser);
            ui_name.setText(teacherCourseRegPageController.getUiName());
            childNode=teacherCourseRegPageController.getPane();

        } else if (choice.equals("regDonePage")) {
            teacherRegisteredCoursesController =loader.getController();
            teacherRegisteredCoursesController.initiateRegisteredCourseView(currentTeacher, registered_course, currentUser);

//            ui_name.setText(studentRegisteredCoursesController.getUiName());
            childNode=teacherRegisteredCoursesController.getPane();

        }
        else if (choice.equals("teacherAttendance")) {
            teacherAttendanceController=loader.getController();
            teacherAttendanceController.initiateTeacherAttendanceController(currentTeacher,currentUser,registered_course);
            childNode=teacherAttendanceController.getPane();
        }else if (choice.equals("teacherMarks")) {
            teacherMarksController = loader.getController();
            teacherMarksController.initiateTeacherMarksController(currentTeacher,currentUser, registered_course);
            childNode=teacherMarksController.getPane();
        } else if (choice.equals("teacherDashBoard")) {
            System.out.println("invoking");
            TeacherInfoController teacherInfoController=loader.getController();
            teacherInfoController.initiateTeacherPane(currentTeacher, currentUser);
            childNode=teacherInfoController.getChildNode();
        }

//        else if(choice.equals("workspacePage")){
//            studentWorkspacePageController = loader.getController();
//            studentWorkspacePageController.initiateRegisteredCourseView(currentStudent, registered_course, currentUser);
//        }

        infoPane.getChildren().clear();
        infoPane.getChildren().add(childNode);
    }

    /**
     * For changing scenes in the dashboard
     * @param event Event of change scene button click
     * @param fxml File name of the UIs of the scene change
     * @param choice Option indicating views (regPage, regDonePage, logoutPage, teacherDashBoard, teacherMarks, teacherAttendance)
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    void changeSceneInDashboard(ActionEvent event, String fxml, String choice) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();

        Scene afterLoginScene = new Scene(root);

        //declaring all the controller
        TeacherCourseRegPageController teacherCourseRegPageController;
        TeacherRegisteredCoursesController teacherRegisteredCoursesController;
        TeacherMarksController teacherMarksController;
        TeacherAttendanceController teacherAttendanceController;


        if (choice.equals("regPage")) {
            teacherCourseRegPageController = loader.getController();
            teacherCourseRegPageController.initiateTeacher(currentTeacher, currentUser);


        } else if (choice.equals("regDonePage")) {
            teacherRegisteredCoursesController =loader.getController();
            teacherRegisteredCoursesController.initiateRegisteredCourseView(currentTeacher, registered_course, currentUser);


        } else if (choice.equals("logoutPage")) {
            System.out.println("Logging out");
        }
        else if (choice.equals("teacherDashBoard")) {
            TeacherDashBoardController teacherDashBoardController;
            teacherDashBoardController = loader.getController();
            teacherDashBoardController.initiateTeacherUser(currentUser);
        }else if (choice.equals("teacherMarks")) {
            teacherMarksController = loader.getController();
            teacherMarksController.initiateTeacherMarksController(currentTeacher,currentUser, registered_course);
        } else if (choice.equals("teacherAttendance")) {
            teacherAttendanceController=loader.getController();
            teacherAttendanceController.initiateTeacherAttendanceController(currentTeacher,currentUser,registered_course);
        }
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(afterLoginScene);
        window.show();
    }
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
     * Course button click function
     * @param event Event for course button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void courseBtnClicked(ActionEvent event) throws SQLException, IOException {
        if (registered_course==null || registered_course.isEmpty()) {
            changeSceneWithInfoPane(event, "teacherCourseRegPage.fxml", "regPage");
        }
        else {
            changeSceneWithInfoPane(event, "teacherRegisteredCourse.fxml", "regDonePage");
        }
    }
    /**
     * Marks button click function
     * @param event Event for marks button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void addMarksBtnClicked(ActionEvent event) throws SQLException, IOException {
        changeSceneWithInfoPane(event, "teacherMarks.fxml", "teacherMarks");
    }
    /**
     * Attendance button click function
     * @param event Event for attendance button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void takeAttendanceBtnClicked(ActionEvent event) throws SQLException, IOException {
        changeSceneWithInfoPane(event, "teacherAttendanceStudentList.fxml", "teacherAttendance");
    }
    /**
     * Dashboard button click function
     * @param event Event of button click
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    public void dashBoardBtnClicked(ActionEvent event) throws SQLException, IOException {
        changeSceneWithInfoPane(event, "teacherInfo.fxml", "teacherDashBoard");
    }
}
