package mypack.project;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class MarkBoxController {
    @FXML
    private HBox studentBox;
    @FXML
    private Label studentId;
    @FXML
    private TextField markField;
    public void initiateBox(String id, double marks){
        studentId.setText(id);
        if(marks>=0)
            markField.setText(String.valueOf(marks));
    }
    public Node getChildNode(){
        return studentBox;
    }
}
