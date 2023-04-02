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
import userPack.Admin;
import userPack.Courses;
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

public class AdminDashBoardController {


    private User currentUser; //this object will contain the email, pass and type of the user for querying
    private Admin currentAdmin;
    private ArrayList<Courses> registered_course;
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

    public Admin getCurrentAdmin() {
        return currentAdmin;
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
    public void initiateAdmin(User newUser) throws SQLException, IOException {
        currentUser = newUser;
        DbUtilities dbUtilities = new DbUtilities();
        currentAdmin = dbUtilities.getAdminInfo(currentUser.getEmail());
        System.out.println(currentAdmin.getName());
        side_fullname_view.setText(currentAdmin.getName());

        //loading the teacherInfo fxml as child to the pane
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminInfo.fxml"));
        Node child=loader.load(); //loader.load() must be used otherwise controller isn't created

        AdminInfoController adminInfoController=loader.getController();
        adminInfoController.initiateAdminPane(currentAdmin, newUser);

        Node childNode=adminInfoController.getChildNode();
        infoPane.getChildren().add(childNode);

        //checking for registered courses( will be empty if not registered )
        registered_course = dbUtilities.getTeacherRegisteredCourses(currentAdmin.getId());
    }

    /**
     * To resuse the buttons of dashboard, the data inserted in the dashboard controller
     * @param admin Teacher object containing their information
     * @param registered_course List of registered courses of the teacher (if any)
     * @param user User object of the teacher
     */
    public void assignDummyController(Admin admin, ArrayList<Courses> registered_course, User user){
        currentAdmin=admin;
        this.registered_course=registered_course;
        currentUser=user;

    }



    void changeSceneWithInfoPane(ActionEvent event, String fxml, String choice) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        Node childNode = null;


        DbUtilities dbUtilities = new DbUtilities();
        registered_course = dbUtilities.getTeacherRegisteredCourses(currentAdmin.getId());
        //declaring all the controller
        TeacherCourseRegPageController teacherCourseRegPageController;
        TeacherRegisteredCoursesController teacherRegisteredCoursesController;
        TeacherMarksController teacherMarksController;
        TeacherAttendanceController teacherAttendanceController;
        StudentWorkspacePageController studentWorkspacePageController;
        AddUser addUser;
        /*--------Result View Controller Remaining-------------*/
    if (choice.equals("adminDashBoard")) {
            System.out.println("invoking");
            AdminInfoController adminInfoController=loader.getController();
            adminInfoController.initiateAdminPane(currentAdmin, currentUser);
            childNode=adminInfoController.getChildNode();
        }else if(choice.equals("addUser")){
            addUser = loader.getController();
            childNode=addUser.getInfoPane();
        }

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


        if (choice.equals("logoutPage")) {
            System.out.println("Logging out");
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
//
//    /**
//     * Course button click function
//     * @param event Event for course button clicked
//     * @throws SQLException If problems with query
//     * @throws IOException If problems with input/output
//     */
//    public void courseBtnClicked(ActionEvent event) throws SQLException, IOException {
//        if (registered_course==null || registered_course.isEmpty()) {
//            changeSceneWithInfoPane(event, "teacherCourseRegPage.fxml", "regPage");
//        }
//        else {
//            changeSceneWithInfoPane(event, "teacherRegisteredCourse.fxml", "regDonePage");
//        }
//    }
//    /**
//     * Marks button click function
//     * @param event Event for marks button clicked
//     * @throws SQLException If problems with query
//     * @throws IOException If problems with input/output
//     */
//    public void addMarksBtnClicked(ActionEvent event) throws SQLException, IOException {
//        changeSceneWithInfoPane(event, "teacherMarks.fxml", "teacherMarks");
//    }
//    /**
//     * Attendance button click function
//     * @param event Event for attendance button clicked
//     * @throws SQLException If problems with query
//     * @throws IOException If problems with input/output
//     */
//    public void takeAttendanceBtnClicked(ActionEvent event) throws SQLException, IOException {
//        changeSceneWithInfoPane(event, "teacherAttendanceStudentList.fxml", "teacherAttendance");
//    }
//    /**
//     * Dashboard button click function
//     * @param event Event of button click
//     * @throws IOException If problems with input/output
//     * @throws SQLException If problems with query
//     */
    public void dashBoardBtnClicked(ActionEvent event) throws SQLException, IOException {
        changeSceneWithInfoPane(event, "adminInfo.fxml", "adminDashBoard");
    }
//
    @FXML
    void addUser_btn_clicked(ActionEvent event) throws SQLException, IOException {
        changeSceneWithInfoPane(event, "addUser.fxml", "addUser");
    }
}
