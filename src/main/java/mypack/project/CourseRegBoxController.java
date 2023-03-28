package mypack.project;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CourseRegBoxController {
    @FXML
    private HBox newCourseInfo;
    @FXML
    private CheckBox option;
    @FXML
    private VBox courseInfo;
    @FXML
    private Label courseName;
    @FXML
    private Label credits;
    public void initiateCourseReg(String courseCodeTitle, String credits){
        option.setText(courseCodeTitle);
        courseName.setText(courseCodeTitle);
        this.credits.setText(credits);
    }

    public Node getCheckHbox(){
        return newCourseInfo;
    }

    public Node getDoneVbox(){
        return courseInfo;
    }
}
