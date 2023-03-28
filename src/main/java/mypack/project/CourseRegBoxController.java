package mypack.project;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/** Main class of the project
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */
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

    /**
     * To initiate course registration Hbox and Vbox
     * @param courseCodeTitle Course code and title of the course
     * @param credits Number of credits of that course
     */
    public void initiateCourseReg(String courseCodeTitle, String credits){
        option.setText(courseCodeTitle);
        courseName.setText(courseCodeTitle);
        this.credits.setText(credits);
    }

    /**
     * TO get checkbox containing course
     * @return Checkbox as node
     */
    public Node getCheckHbox(){
        return newCourseInfo;
    }

    /**
     * To get Vbox containing registered course
     * @return Vbox as node
     */
    public Node getDoneVbox(){
        return courseInfo;
    }
}
