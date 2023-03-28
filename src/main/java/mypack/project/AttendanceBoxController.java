package mypack.project;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
/** Main class of the project
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

public class AttendanceBoxController {
    @FXML
    private HBox studentBox;
    @FXML
    private CheckBox studentId;
    @FXML
    private Label nameLabel;

    /**
     * To initiate Hbox with student name and id
     * @param id Id of student
     * @param name Name of student
     */
    public void initiateBox(String id, String name){
        studentId.setText(id);
        nameLabel.setText(name);
    }

    /**
     * To get Hbox containing student id and name
     * @return Hbox as node
     */
    public Node getChildNode(){
        return studentBox;
    }
}
