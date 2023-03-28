package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import userPack.Courses;
import userPack.Student;
import userPack.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentSingleWorkspaceController {

    @FXML
    private Label full_name_label;

    @FXML
    private Button postBtn;

    @FXML
    private ChoiceBox<?> postType;

    @FXML
    private Label workspace_name;

    private User currentUser;

    private Student currentStudent;
    private ArrayList<Courses> registered_courses;

    @FXML
    private VBox postBox;

    public void initiateData(String workspaceName, Student student, ArrayList<Courses> courses, User user) throws SQLException {
        currentStudent = student;
        currentUser = user;
        registered_courses = courses;

        workspace_name.setText(workspaceName);


        DbUtilities fetchPost = new DbUtilities();
        ArrayList<Post> allPost = fetchPost.getAllPost(workspaceName);

        for(Post post : allPost) {
            VBox singlePost = new VBox();
            Pane postPane = new Pane();
            Text postText = new Text();
            Label postGiverEmail = new Label();
            postGiverEmail.setText(post.getPost_giver_email());
            postPane.setStyle("-fx-padding: 0 0 16px 50px;");
            postText.setText(post.getPost_text());
            postPane.getChildren().addAll(postText,postGiverEmail);
            singlePost.getChildren().add(postPane);

            postBox.getChildren().add(singlePost);
            // printing all value in console
//                System.out.println("Post ID: " + post.getPostid());
//                System.out.println("Course Code: " + post.getCourseCode());
//                System.out.println("Post Giver Email: " + post.getPost_giver_email());
//                System.out.println("Post Giver Type: " + post.getPost_giver_type());
//                System.out.println("Post Text: " + post.getPost_text());
//                System.out.println("Post Type: " + post.getPost_type());
//                System.out.println("Attachment Link: " + post.getAttachment_link());
//                System.out.println("Deadline: " + post.getDeadline());
        }


    }
    @FXML
    void courseBtnClicked(ActionEvent event) {

    }

    @FXML
    void dashboardBtnClicked(ActionEvent event) {

    }

    @FXML
    void logoutBtnClicked(ActionEvent event) {

    }

    @FXML
    void progressBtnClicked(ActionEvent event) {

    }

    @FXML
    void resultBtnClicked(ActionEvent event) {

    }

}
