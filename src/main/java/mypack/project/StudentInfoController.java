package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import userPack.Student;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
/** Controller class for student information pane in dashboard
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */
public class StudentInfoController {

    User currentUser;
    @FXML
    private Label fullname_view;
    @FXML
    private Label semester_view;
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
     * To initiate the student information in the pane
     * @param currentStudent Student object containing their personal information
     * @param user User object of the student
     */
    public void initiateStudentPane(Student currentStudent, User user){
        currentUser=user;
        email_view.setText(currentStudent.getEmail());
        fullname_view.setText(currentStudent.getName());
        dob_view.setText(currentStudent.getDob().toString());
        dept_view.setText(currentStudent.getDept());
        id_view.setText(currentStudent.getId());
        semester_view.setText(currentStudent.getSemester());
    }

    /**
     * To get a reference to the pane containing the student information
     * @return Student information pane as node
     */
    public Node getChildNode(){
        return infoPane;
    }

//    public StudentInfoController(){
//
//    }

    /**
     * To change scene update information when updated profile button is clicked
     * @param event Event of update profile button click
     * @throws IOException
     * @throws SQLException
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
