package mypack.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ListBoxController {

    @FXML
    private Label code_label;

    @FXML
    private Label department_label;

    @FXML
    private Pane infoPane;

    @FXML
    private HBox list_hbox;

    @FXML
    private Label name_label;

    public HBox initiate(String code, String name, String dept){
        this.code_label.setText(code);
        this.name_label.setText(name);
        this.department_label.setText(dept);
        return list_hbox;
    }

}
