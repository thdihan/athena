package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import userPack.Courses;
import userPack.Student;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/** Controller class for registered course page
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

public class StudentWorkspacePageController {
    @FXML
    private VBox course_box;
    private User currentUser;

    private Student currentStudent;
    @FXML
    private  Pane infoPane;
    private ArrayList<Courses> registered_courses;

    /**
     * To initiate the data and registered course view page
     * @param student Student currently logged in
     * @param courses List of registered courses
     * @param user User currenly logged in
     * @throws SQLException If problems with query
     */
    public void initiateRegisteredCourseView(ArrayList<Courses> courses, User user) throws SQLException {
        registered_courses=courses;
        currentUser=user;
        if(registered_courses.size()  == 0) System.out.println("NULLLLLLLL");
        for(int i=0 ; i<registered_courses.size() ; i++){
            System.out.println("working");
//            Pane pane =new Pane();
            VBox pane = new VBox();
            VBox vBox=new VBox();
            vBox.setSpacing(2);

            Button singleWorkspace = new Button();
            singleWorkspace.setStyle("-fx-min-width: 150px");
            singleWorkspace.setText(registered_courses.get(i).getCode());
//            Label course=new Label(registered_courses.get(i).getCode() + ": "+ registered_courses.get(i).getTitle());
//            course.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
//            Label credit=new Label("Credits: "+registered_courses.get(i).getCredit().toString());
//            vBox.getChildren().addAll(course, credit);
            vBox.getChildren().addAll(singleWorkspace);
//            VBox.getMargin(vBox,new Insets(0, 0, 0, 10));
            pane.setStyle("-fx-padding: 0 0 16px 50px;");
            pane.getChildren().add(vBox);

            course_box.getChildren().add(pane);

            int finalI = i;
            singleWorkspace.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("studentSingleWorkspacePage.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                StudentSingleWorkspaceController studentSingleWorkspaceController = loader.getController();
                try {
                    studentSingleWorkspaceController.initiateData(registered_courses.get(finalI).getCode(),currentStudent, registered_courses, currentUser);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                infoPane.getChildren().clear();
                infoPane.getChildren().add(studentSingleWorkspaceController.getPane());
//                Parent root = null;
//                try {
//                    root = loader.load();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//                Scene afterLoginScene = new Scene(root);
//                StudentSingleWorkspaceController studentSingleWorkspaceController = loader.getController();
//                try {
//                    studentSingleWorkspaceController.initiateData(registered_courses.get(finalI).getCode(),currentStudent, registered_courses, currentUser);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//
//                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                window.setScene(afterLoginScene);
//                window.show();
            });
        }

    }

    /**
     * Logout button click function
     * @param event Event for logout button clicked
     * @throws SQLException If problems with query
     * @throws IOException If problems with input/output
     */
    public void logoutBtnClicked (ActionEvent event) throws SQLException, IOException {
        StudentDashBoardController studentDashBoardController=new StudentDashBoardController();
        studentDashBoardController.assignDummyController(currentStudent, registered_courses, currentUser);
        studentDashBoardController.logoutBtnClicked(event);
    }

    public Pane getPane() {
        return infoPane;
    }

    public  String getUiName() {
        return "Course Registration Page";
    }
}
