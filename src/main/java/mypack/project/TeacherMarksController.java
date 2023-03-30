package mypack.project;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
/** Controller class for adding marks
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

public class TeacherMarksController implements Initializable {
    private Teacher currentTeacher;
    ArrayList<Courses> registered_course;
    HashMap<String, String> examType;
    private String choice;
    private User currentUser;
    @FXML
    private VBox studentListVbox;
    @FXML
    private Button listBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private ScrollPane scrollPane;
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
    @FXML
    private Label studentLabel;
    @FXML
    private Label optionLabel;
    @FXML
    private Button addBtn;
    @FXML
    private Button modifyBtn;

    /**
     * To initiate the add marks view during scene change
     * @param teacher Teacher object containing their information
     * @param user User object of the teacher
     * @param registered_course List of registered courses of the teacher if any
     * @throws SQLException If problems with query
     */
    public void initiateTeacherMarksController(Teacher teacher, User user, ArrayList<Courses> registered_course) throws SQLException {
        currentTeacher = teacher;
        currentUser = user;
        this.registered_course = registered_course;
        full_name_label.setText(teacher.getName());
        scrollPane.setVisible(false);
        studentListVbox.setVisible(false);
        courseDropDown.setVisible(false);
        examTypeDropDown.setVisible(false);
        submitBtn.setVisible(false);
        listBtn.setVisible(false);

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
    public Node getHbox(String id, double marks) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("markBox.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        MarkBoxController markBoxController=loader.getController();

        markBoxController.initiateBox(id, marks);
        Node childNode= markBoxController.getChildNode();
        return childNode;
    }

    /**
     * To generate the student list
     * @param event Event of get list button click
     * @return List of students based on course code selected
     */
    public ArrayList<String> onGetStudentListBtnClicked(ActionEvent event) throws IOException {
        //return empty list if exceptions

        DbUtilities dbUtilities = new DbUtilities();
        ArrayList<String> studentList = new ArrayList<>();
        if (selectCourseInDropDown(event) == null)
            return studentList;
        if (choice.equals("add")) {
            studentList = dbUtilities.getStudentList(selectCourseInDropDown(event));
            if(studentList.isEmpty()){
                studentLabel.setText("No students registered for this course yet");
                addTransition(studentLabel);
                return studentList;
            }
//        System.out.println(studentList.get(0));
            studentListVbox.getChildren().clear();
            for (int i = 0; i < studentList.size(); i++) {
                studentListVbox.getChildren().add(getHbox(studentList.get(i).split(" ")[1], -1));

            }
        } else {
            TreeMap<String, Double> treeMap = new TreeMap<>();
            treeMap.putAll(dbUtilities.getStudentMarkList(selectCourseInDropDown(event), examType.get(selectExamInDropDown(event))));
            if (treeMap.isEmpty()) {
                studentLabel.setText("No students registered or previously marks not added");
                addTransition(studentLabel);
                return studentList;
            }
            studentListVbox.getChildren().clear();
            for (Map.Entry<String, Double> entry : treeMap.entrySet()) {
                studentListVbox.getChildren().add(getHbox(String.valueOf(entry.getKey()), entry.getValue()));

            }
        }
        studentListVbox.setAlignment(Pos.TOP_CENTER);
        studentLabel.setText("");
        scrollPane.setVisible(true);
        studentListVbox.setVisible(true);
        return studentList;
    }

    /**
     * To check if valid marks has been given as input
     * @param str Marks as string
     * @return True if string contains only numeric else False
     */
    public boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+(\\.\\d+)?");
    }
    /**
     * To submit the newly added marks
     * @param event Event of submit button click
     */
    public void onSubmitBtnClicked(ActionEvent event) {
        if (courseDropDown.getValue() == null || examTypeDropDown.getValue() == null || studentListVbox.getChildren().isEmpty()) {
            if (courseDropDown.getValue() == null) {
                courseLabel.setText("Please select course");
            }
            if (studentListVbox.getChildren().isEmpty()) {
                studentLabel.setText("Please generate student list to continue");
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
                if (markField.getText().equals("")) {
                    studentLabel.setText("Please fill all the mark fields before submitting");
                    break;
                } else if (!isNumeric(markField.getText())) {
                    studentLabel.setText("Please input valid marks");
                    break;
                }
                System.out.println(studentListVbox.getChildren().size());
                System.out.println(markField.getText());
                dbUtilities.updateStudentMarks(idLabel.getText(), Double.parseDouble(markField.getText()), courseDropDown.getValue().split(":")[0], examType.get(examTypeDropDown.getValue()));
                studentLabel.setText("Marks updated successfully");
                addTransition(studentLabel);
//                System.out.println("IN");
//                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
////                FadeTransition fade = new FadeTransition(Duration.seconds(1), studentLabel);
////                fade.setToValue(0);
////                fade.setOnFinished(event1 -> studentLabel.setText(""));
////                SequentialTransition seq = new SequentialTransition(pauseTransition, fade);
////                seq.play();
//
//                pauseTransition.setOnFinished(event1 -> studentLabel.setText(""));
//                pauseTransition.play();

            }
            scrollPane.setVisible(false);
            studentListVbox.setVisible(false);
            studentListVbox.getChildren().clear();

        }
    }

    /**
     * Option to add marks
     * @param event Event of add marks button click
     */
    public void onAddMarksBtnClicked(ActionEvent event) {
        choice = "add";
        optionLabel.setText("Adding marks");
        studentListVbox.getChildren().clear();
        studentListVbox.setVisible(false);
        listBtn.setVisible(true);
        submitBtn.setVisible(true);
        scrollPane.setVisible(false);

        courseDropDown.setVisible(true);
        examTypeDropDown.setVisible(true);
        modifyBtn.setVisible(true);
        addBtn.setVisible(false);

        modifyBtn.setLayoutX(535);
        modifyBtn.setLayoutY(83);
        addBtn.setLayoutX(535);
        addBtn.setLayoutY(130);

        modifyBtn.setPrefHeight(37);
//        modifyBtn.setPrefWidth(129);
        addBtn.setPrefHeight(37);
//        addBtn.setPrefWidth(129);
    }

    /**
     * Option to modify marks
     * @param event Event of modify marks button click
     */
    public void onModifyMarksBtnClicked(ActionEvent event) {
        choice = "modify";
        optionLabel.setText("Modifying marks");
        studentListVbox.getChildren().clear();
        studentListVbox.setVisible(false);
        listBtn.setVisible(true);
        submitBtn.setVisible(true);
        scrollPane.setVisible(false);

        courseDropDown.setVisible(true);
        examTypeDropDown.setVisible(true);
        addBtn.setVisible(true);
        modifyBtn.setVisible(false);
    }

    /**
     * To get the selected course in dropdown
     * @param event Event of selection
     * @return Course code as string
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

    /**
     * To get the exam type in drop down
     * @param event Event of selection
     * @return Exam type as string
     */
    public String selectExamInDropDown(ActionEvent event) {
        String examType = examTypeDropDown.getValue();
        examLabel.setText("");
        return examType;
    }
    /**
     * Attendance button click function
     * @param event Event for attendance button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void takeAttendanceBtnClicked(ActionEvent event) throws SQLException, IOException {
        TeacherDashBoardController teacherDashBoardController = new TeacherDashBoardController();
        teacherDashBoardController.assignDummyController(currentTeacher, registered_course, currentUser);
        teacherDashBoardController.takeAttendanceBtnClicked(event);
    }
    /**
     * Course button click function
     * @param event Event for course button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
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
     * @param event Event of marks button click
     */
    public void marksBtnClicked(ActionEvent event) {
        System.out.println("Already in marks page");
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

    /**
     * To add transition to the labels
     * @param label Label to add the transition
     */
    public void addTransition(Label label){
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(event1 -> label.setText(""));
        pauseTransition.play();
    }

    /**
     * To initiate the exam dropdown
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> examList = FXCollections.observableArrayList("Quiz: 1", "Quiz: 2", "Quiz: 3", "Quiz: 4", "Semester Mid", "Semester Final");
        examTypeDropDown.setItems(examList);
    }
}
