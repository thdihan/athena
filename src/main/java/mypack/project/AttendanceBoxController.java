package mypack.project;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AttendanceBoxController {
    @FXML
    private HBox studentBox;
    @FXML
    private CheckBox studentId;
    @FXML
    private Label nameLabel;
    public void initiateBox(String id, String name){
        studentId.setText(id);
        nameLabel.setText(name);
    }
    public Node getChildNode(){
        return studentBox;
    }
}
