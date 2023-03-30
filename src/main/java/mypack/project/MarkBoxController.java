package mypack.project;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
/** Main class of the project
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */
public class MarkBoxController {
    @FXML
    private HBox studentBox;
    @FXML
    private Label studentId;
    @FXML
    private TextField markField;

    /**
     * To initiate Hbox with student id and mark field
     * @param id Id of the student
     * @param marks Marks obtainined by the student
     */
    public void initiateBox(String id, double marks){
        studentId.setText(id);
        if(marks>=0)
            markField.setText(String.valueOf(marks));
    }

    /**
     * Hbox containing id and marks of student
     * @return Hbox as node
     */
    public Node getChildNode(){
        return studentBox;
    }
}
