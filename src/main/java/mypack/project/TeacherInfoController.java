package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import userPack.Student;
import userPack.Teacher;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;

public class TeacherInfoController {
    User currentUser;
    @FXML
    private Label fullname_view;
    @FXML
    private Label dept_view;
    @FXML
    private Label id_view;
    @FXML
    private Label dob_view;
    @FXML
    private Label email_view;
    @FXML
    private Pane infoPane;

    public void initiateTeacherPane(Teacher currentTeacher, User user){
        currentUser=user;
        email_view.setText(currentTeacher.getEmail());
        fullname_view.setText(currentTeacher.getName());
        dob_view.setText(currentTeacher.getDob().toString());
        dept_view.setText(currentTeacher.getDept());
        id_view.setText(currentTeacher.getId());
    }

    public Node getChildNode(){
        return infoPane;
    }

//    public StudentInfoController(){
//
//    }

    public void onUpdateProfileBtnClicked(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("updateProfile.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        UpdateProfileController updateProfileController=loader.getController();
        updateProfileController.initiateUpdateProfileController(currentUser);

        Node childNode=updateProfileController.getChildNode();
        infoPane.getChildren().clear();
        infoPane.getChildren().add(childNode);
    }
}
