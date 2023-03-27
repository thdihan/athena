package mypack.project;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import userPack.Courses;
import userPack.Teacher;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
/** Controller class for attendance taking view of teacher
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

public class TeacherAttendanceController {
    private Teacher currentTeacher;
    ArrayList<Courses> registered_course;
    HashMap<String, String> examType;
    private User currentUser;
    @FXML
    private Label full_name_label;
    @FXML
    private ComboBox<String> courseDropDown;
    @FXML
    private Label courseLabel;
    @FXML
    private VBox studentListVbox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label submitLabel;


    /**
     * To initiate the required data while changing scenes
     * @param teacher Teacher taking the attendance
     * @param user User object of the teacher
     * @param registered_course List of registered courses of teacher (if any)
     * @throws SQLException If problems with query
     */
    public void initiateTeacherAttendanceController(Teacher teacher, User user, ArrayList<Courses> registered_course) throws SQLException {
        currentTeacher = teacher;
        currentUser = user;
        this.registered_course = registered_course;
        full_name_label.setText(teacher.getName());
        scrollPane.setVisible(false);
        studentListVbox.setVisible(false);

        DbUtilities dbUtilities = new DbUtilities();
        ArrayList<Courses> registeredCourses = dbUtilities.getTeacherRegisteredCourses(currentTeacher.getId()); //may come empty if
        ArrayList<String> stringArrayList = new ArrayList<>();

        for (int i = 0; i < registeredCourses.size(); i++) {
            stringArrayList.add(registeredCourses.get(i).getCode() + ": " + registeredCourses.get(i).getTitle());
        }
        ObservableList<String> courseList = FXCollections.observableList(stringArrayList);
        courseDropDown.setItems(courseList);

        examType = new HashMap<>();
        examType.put("Quiz: 1", "quiz_1");
        examType.put("Quiz: 2", "quiz_2");
        examType.put("Quiz: 3", "quiz_3");
        examType.put("Quiz: 4", "quiz_4");
        examType.put("Semester Mid", "mid_marks");
        examType.put("Semester Final", "final_marks");
    }

    /**
     * To obtain the information of selected course in dropdown
     * @param event Event of course selection
     * @return Selected course as string. Else return null
     */
    public String selectCourseInDropDown(ActionEvent event) {
        if (courseDropDown.getValue() == null) {
            courseLabel.setText("Please select course");
            return null;
        }
        String[] course = courseDropDown.getValue().split(":");
        String courseCode = course[0];
//        testLabel.setText(courseCode);
        courseLabel.setText("");
        return courseCode;
    }

    public Node getHbox(String id, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("attendanceBox.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        AttendanceBoxController attendanceBoxController=loader.getController();

        attendanceBoxController.initiateBox(id,name);
        Node childNode= attendanceBoxController.getChildNode();
        return childNode;
    }

    /**
     * To generate the student list in the Vbox
     * @param event Event of get list button click
     * @return List of students based on course code
     */
    public ArrayList<String> onGetStudentListBtnClicked(ActionEvent event) throws IOException {
        //return empty list if exceptions
        DbUtilities dbUtilities = new DbUtilities();
        ArrayList<String> studentList = new ArrayList<>();
        if (selectCourseInDropDown(event) == null)
            return studentList;
        studentList = dbUtilities.getStudentList(selectCourseInDropDown(event));
//        System.out.println(studentList.get(0));
        studentListVbox.getChildren().clear();
        for (int i = 0; i < studentList.size(); i++) {
//            HBox studentBox = new HBox();
//            CheckBox studentId = new CheckBox(studentList.get(i).split(" ")[1]);
//            Label nameLabel = new Label(studentList.get(i).split(":")[0]);
//            HBox.setMargin(studentId, new Insets(15, 10, 10, 20));
//            HBox.setMargin(nameLabel, new Insets(15, 10, 10, 10));
//            studentBox.getChildren().addAll(studentId, nameLabel);
//            studentBox.setSpacing(20);
            studentListVbox.getChildren().add(getHbox(studentList.get(i).split(" ")[1], studentList.get(i).split(":")[0]));

        }
//        studentListVbox.setAlignment(Pos.TOP_CENTER);
        submitLabel.setText("");
        scrollPane.setVisible(true);
        studentListVbox.setVisible(true);
        return studentList;

    }

    /**
     * To submit the attendance when button is clicked
     * @param event Event of submit button click
     */
    public void onSubmitBtnClicked(ActionEvent event) {
        if (courseDropDown.getValue() == null || studentListVbox.getChildren().isEmpty()) {
            if (courseDropDown.getValue() == null) {
                courseLabel.setText("Please select course");
            }
            if(studentListVbox.getChildren().isEmpty()){
                submitLabel.setText("Please generate student list to continue");
            }

        } else {

            DbUtilities dbUtilities = new DbUtilities();
            ArrayList<String> presentList = new ArrayList<>();
            ArrayList<String> absentList = new ArrayList<>();
            String courseCode = courseDropDown.getValue().split(":")[0];

            for (int i = 0; i < studentListVbox.getChildren().size(); i++) {
                HBox hBox = (HBox) studentListVbox.getChildren().get(i);
                CheckBox idBox = (CheckBox) hBox.getChildren().get(0);
//                TextField markField = (TextField) hBox.getChildren().get(1);
                if (idBox.isSelected()) {
                    presentList.add(idBox.getText());
                } else {
                    absentList.add(idBox.getText());
                }
            }
            dbUtilities.takeAttendance(courseCode, presentList, absentList, currentTeacher);
            submitLabel.setText("Attendance taken successfully");
            PauseTransition pauseTransition =new PauseTransition(Duration.seconds(3));
            pauseTransition.setOnFinished(event1 -> submitLabel.setText(""));
            pauseTransition.play();
            scrollPane.setVisible(false);
            studentListVbox.setVisible(false);
            studentListVbox.getChildren().clear();
        }
    }

    /**
     * Course attendance click function
     * @param event Event of attendance button click
     */
    public void takeAttendanceBtnClicked(ActionEvent event) {
        System.out.println("Already in attendance page");
    }

    /**
     * Course button click function
     * @param event Event of course button click
     * @throws SQLException If problems with query
     * @throws IOException If problems with input output
     */
    public void courseBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController = new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        if (registered_course == null || registered_course.isEmpty()) {
            teacherDashBoardController.changeSceneInDashboard(event, "teacherCourseRegPage.fxml", "regPage");
        } else {
            teacherDashBoardController.changeSceneInDashboard(event, "teacherRegisteredCourse.fxml", "regDonePage");
        }
    }
    /**
     * Marks button click function
     * @param event Event of Marks button click
     * @throws SQLException If problems with query
     * @throws IOException If problems with input output
     */
    public void marksBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController = new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.addMarksBtnClicked(event);
    }
    /**
     * Logout button click function
     * @param event Event for logout button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void logoutBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController = new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.logoutBtnClicked(event);
    }
    /**
     * Dashboard button click function
     * @param event Event of button click
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    public void dashBoardBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController = new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.dashBoardBtnClicked(event);
    }
}
