package mypack.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import userPack.Courses;
import userPack.Student;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller class for academic progress view of students
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */
public class StudentAcademicProgressController {
    @FXML
    TableView<AcademicProgressModel> academicProgressModelTableView;
    @FXML
    TableColumn<AcademicProgressModel, String> courseCodeColumn;
    @FXML
    TableColumn<AcademicProgressModel, Double> attendanceColumn;
    @FXML
    TableColumn<AcademicProgressModel, Double> quiz1Column;
    @FXML
    TableColumn<AcademicProgressModel, Double> quiz2Column;
    @FXML
    TableColumn<AcademicProgressModel, Double> midColumn;
    @FXML
    TableColumn<AcademicProgressModel, Double> quiz3Column;
    @FXML
    TableColumn<AcademicProgressModel, Double> quiz4Column;
    @FXML
    TableColumn<AcademicProgressModel, Double> finalColumn;
    @FXML
    TableColumn<AcademicProgressModel, Double> progressColumn;
    @FXML
    TableColumn<AcademicProgressModel, String> gradeColumn;
    @FXML
    private Label full_name_label;
    private Student currentStudent;
    private ArrayList<Courses> registered_course;
    private User currentUser;
    ObservableList<AcademicProgressModel>academicProgressModels;

    /**
     * To initiate the data and view of the academic progress view of students
     * @param currentStudent Student currently logged in
     * @param registered_courses List of registered courses of the student. May contain empty list if not registered
     * @param user User object containing email and password of the current user
     */
    void initiateAcademicProgressView(Student currentStudent, ArrayList<Courses> registered_courses, User user){
        this.currentStudent=currentStudent;
        this.registered_course=registered_courses;
        currentUser=user;

        full_name_label.setText(currentStudent.getName());
        //REMAINING: Functionalities of the academic progress
        DbUtilities dbUtilities=new DbUtilities();
        academicProgressModels=FXCollections.observableArrayList(dbUtilities.getStudentProgressData(currentStudent));

        //setting up the columns
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        quiz1Column.setCellValueFactory(new PropertyValueFactory<>("quiz_1"));
        quiz2Column.setCellValueFactory(new PropertyValueFactory<>("quiz_2"));
        midColumn.setCellValueFactory(new PropertyValueFactory<>("mid_marks"));
        quiz3Column.setCellValueFactory(new PropertyValueFactory<>("quiz_3"));
        quiz4Column.setCellValueFactory(new PropertyValueFactory<>("quiz_4"));
        finalColumn.setCellValueFactory(new PropertyValueFactory<>("final_marks"));
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        academicProgressModelTableView.setItems(academicProgressModels);
    }

    /**
     * Dashboard button click function
     * @param event Event of button click
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    public void dashboardBtnClicked(ActionEvent event) throws IOException,SQLException{
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_course, currentUser);
        studentDashBoardController.dashboardBtnClicked(event);

    }

    /**
     * Progress button click function
     * @param event Event for progress button click
     */
    public void progressBtnClicked (ActionEvent event){
        System.out.println("Currently in progress page");
    }

    /**
     * Course button click function
     * @param event Event for course button click
     * @throws SQLException If problems with query
     * @throws IOException If problmes with input/output
     */
    public void courseBtnClicked (ActionEvent event) throws SQLException, IOException {
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_course, currentUser);
        studentDashBoardController.courseBtnClicked(event);
    }

    /**
     * Result button click function
     * @param event Event for result button clicked
     */
    public void resultBtnClicked (ActionEvent event){
        System.out.println("Result btn for progress not done");
    }

    /**
     * Logout button click function
     * @param event Event for logout button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void logoutBtnClicked (ActionEvent event) throws SQLException, IOException {
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_course, currentUser);
        studentDashBoardController.logoutBtnClicked(event);
    }
}
