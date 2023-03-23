package mypack.project;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    public ArrayList<String> onGetStudentListBtnClicked(ActionEvent event) {
        //return empty list if exceptions
        DbUtilities dbUtilities = new DbUtilities();
        ArrayList<String> studentList = new ArrayList<>();
        if (selectCourseInDropDown(event) == null)
            return studentList;
        studentList = dbUtilities.getStudentList(selectCourseInDropDown(event));
//        System.out.println(studentList.get(0));
        studentListVbox.getChildren().clear();
        for (int i = 0; i < studentList.size(); i++) {
            HBox studentBox = new HBox();
            CheckBox studentId = new CheckBox(studentList.get(i).split(" ")[1]);
            Label nameLabel = new Label(studentList.get(i).split(":")[0]);
            HBox.setMargin(studentId, new Insets(15, 10, 10, 20));
            HBox.setMargin(nameLabel, new Insets(15, 10, 10, 10));
            studentBox.getChildren().addAll(studentId, nameLabel);
            studentBox.setSpacing(20);
            studentListVbox.getChildren().add(studentBox);

        }
        studentListVbox.setAlignment(Pos.TOP_CENTER);
        submitLabel.setText("");
        scrollPane.setVisible(true);
        studentListVbox.setVisible(true);
        return studentList;

    }

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


    public void takeAttendanceBtnClicked(ActionEvent event) {
        System.out.println("Already in attendance page");
    }

    public void courseBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController = new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        if (registered_course == null || registered_course.isEmpty()) {
            teacherDashBoardController.changeSceneInDashboard(event, "teacherCourseRegPage.fxml", "regPage");
        } else {
            teacherDashBoardController.changeSceneInDashboard(event, "teacherRegisteredCourse.fxml", "regDonePage");
        }
    }

    public void marksBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController = new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.addMarksBtnClicked(event);
    }

    public void logoutBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController = new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.logoutBtnClicked(event);
    }

    public void dashBoardBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController = new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.dashBoardBtnClicked(event);
    }
}
