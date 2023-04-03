package mypack.project;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import userPack.Courses;

import java.util.ArrayList;

public class ResultBoxController {

    @FXML
    private Label grade;

    @FXML
    private Label courseName;

    @FXML
    private Pane infoPane;

    @FXML
    private HBox singleCourseGradeBox;

    @FXML
    private TitledPane singleSemester;
    @FXML
    private VBox courseResultContainer;

    public void initiateSingleCourse(String courseName, String grade) {
        this.courseName.setText(courseName);
        this.grade.setText(grade);
    }

    public HBox getSingleCourse() {
        return singleCourseGradeBox;
    }

    public void initiateTitlePane(ArrayList<Node> allCourseResult) {
        for (int i = 0;i < allCourseResult.size();i++) {
            courseResultContainer.getChildren().add(allCourseResult.get(i));
        }
    }

    public TitledPane getTitlePane(){
        return singleSemester;
    }

}
