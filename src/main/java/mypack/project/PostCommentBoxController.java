package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PostCommentBoxController {

    @FXML
    private Text CommentText;

    @FXML
    private Label commentGiverName;

    @FXML
    private Label postGiverName;

    @FXML
    private Text postText;

    @FXML
    private VBox singlePost_Vbox;

    @FXML
    private VBox singlePost_Vbox1;

    @FXML
    private Button viewDetailsBtn;

    @FXML
    void viewDetailsBtnClicked(ActionEvent event) {

    }

}
