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
/** Controller class for teacher information pane in dashboard
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

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

    /**
     * To initiate the teacher information in the pane
     * @param currentTeacher Teacher object containing their information
     * @param user User object of the teacher
     */
    public void initiateTeacherPane(Teacher currentTeacher, User user){
        currentUser=user;
        email_view.setText(currentTeacher.getEmail());
        fullname_view.setText(currentTeacher.getName());
        dob_view.setText(currentTeacher.getDob().toString());
        dept_view.setText(currentTeacher.getDept());
        id_view.setText(currentTeacher.getId());
    }

    /**
     * To get a reference to the pane containing the information
     * @return The pane element as node
     */
    public Node getChildNode(){
        return infoPane;
    }

//    public StudentInfoController(){
//
//    }

    /**
     * To go to update profile view
     * @param event Event of update profile button click
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
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
