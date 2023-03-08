package mypack.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import userPack.Courses;
import userPack.Teacher;
import userPack.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TeacherMarksController implements Initializable {
    private Teacher currentTeacher;
    ArrayList<Courses>registered_course;
    HashMap<String, String> examType;
    private User currentUser;
    @FXML
    private VBox studentListVbox;
    @FXML
    private ComboBox<String> courseDropDown;
    @FXML
    private ComboBox<String> examTypeDropDown;
    @FXML
    private Label full_name_label;
    @FXML
    private Label courseLabel;
    @FXML
    private Label examLabel;

    public void initiateTeacherMarksController(Teacher teacher, User user, ArrayList<Courses> registered_course) throws SQLException {
        currentTeacher = teacher;
        currentUser = user;
        this.registered_course=registered_course;
        full_name_label.setText(teacher.getName());

        DbUtilities dbUtilities = new DbUtilities();
        ArrayList<Courses> registeredCourses = dbUtilities.getTeacherRegisteredCourses(currentTeacher.getId()); //may come empty if
        ArrayList<String> stringArrayList = new ArrayList<>();

        for (int i = 0; i < registeredCourses.size(); i++) {
            stringArrayList.add(registeredCourses.get(i).getCode() + ": " + registeredCourses.get(i).getTitle());
        }
        ObservableList<String> courseList = FXCollections.observableList(stringArrayList);
        courseDropDown.setItems(courseList);

        examType=new HashMap<>();
        examType.put("Quiz: 1", "quiz_1");
        examType.put("Quiz: 2", "quiz_2");
        examType.put("Quiz: 3", "quiz_3");
        examType.put("Quiz: 4", "quiz_4");
        examType.put("Semester Mid", "mid_marks");
        examType.put("Semester Final", "final_marks");
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
            Label studentId = new Label(studentList.get(i).split(" ")[1]);
            TextField markField = new TextField();
            markField.setPromptText("Input Marks");
            HBox.setMargin(studentId, new Insets(15, 20, 10, 20));
            HBox.setMargin(markField, new Insets(10, 10, 10, 20));
            studentBox.getChildren().addAll(studentId, markField);
            studentBox.setSpacing(20);
            studentListVbox.getChildren().add(studentBox);

        }
        studentListVbox.setAlignment(Pos.TOP_CENTER);
        return studentList;
    }

    public void onSubmitBtnClicked(ActionEvent event) {
        if (courseDropDown.getValue() == null || examTypeDropDown.getValue() == null || studentListVbox.getChildren().isEmpty()) {
            if (courseDropDown.getValue() == null) {
                courseLabel.setText("Please select course");
            }
            if (examTypeDropDown.getValue() == null) {
                examLabel.setText("Please select exam type");
            } else {
                examLabel.setText("");
            }
        } else {
            DbUtilities dbUtilities = new DbUtilities();
            for (int i = 0; i < studentListVbox.getChildren().size(); i++) {
                HBox hBox = (HBox) studentListVbox.getChildren().get(i);
                Label idLabel = (Label) hBox.getChildren().get(0);
                TextField markField = (TextField) hBox.getChildren().get(1);
                dbUtilities.updateStudentMarks(idLabel.getText(), Double.parseDouble(markField.getText()), courseDropDown.getValue().split(":")[0], examType.get(examTypeDropDown.getValue()));
            }
        }
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

    public String selectExamInDropDown(ActionEvent event) {
        String examType = examTypeDropDown.getValue();
        examLabel.setText("");
        return examType;
    }

    public void takeAttendanceBtnClicked(ActionEvent event) {
        System.out.println("Not implemented attendance");
    }

    public void courseBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher,registered_course, currentUser);
        if (registered_course==null || registered_course.isEmpty()) {
            teacherDashBoardController.changeSceneInDashboard(event, "teacherCourseRegPage.fxml", "regPage");
        }
        else {
            teacherDashBoardController.changeSceneInDashboard(event, "teacherRegisteredCourse.fxml", "regDonePage");
        }
    }

    public void marksBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.addMarksBtnClicked(event);
    }

    public void logoutBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.logoutBtnClicked(event);
    }

    public void dashBoardBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController=new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.dashBoardBtnClicked(event);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> examList = FXCollections.observableArrayList("Quiz: 1", "Quiz: 2", "Quiz: 3", "Quiz: 4", "Semester Mid", "Semester Final");
        examTypeDropDown.setItems(examList);
    }
}
