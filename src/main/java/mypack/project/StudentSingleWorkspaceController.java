package mypack.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import userPack.Courses;
import userPack.Student;
import userPack.User;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class StudentSingleWorkspaceController {

    @FXML
    private Label full_name_label;

    @FXML
    private Button postBtn;

    @FXML
    private ComboBox<String> postType;

    @FXML
    private Label workspace_name;
    private String workspaceName;

    private User currentUser;

    private Student currentStudent;
    private ArrayList<Courses> registered_courses;
    private HashMap<String, String> postTypeInput;

    @FXML
    private VBox postBox;
    @FXML
    private TextArea post_text_box;


    @FXML
    private VBox datetime;
    public void initiateData(String workspaceName, Student student, ArrayList<Courses> courses, User user) throws SQLException {
        currentStudent = student;
        currentUser = user;
        registered_courses = courses;



        this.workspaceName = workspaceName;
        workspace_name.setText(workspaceName);
        ObservableList<String> postTypeOption = FXCollections.observableArrayList("Announcement", "Assignment");
        postType.setPromptText("Select post type");
        postType.setItems(postTypeOption);

        DbUtilities fetchPost = new DbUtilities();
        ArrayList<Post> allPost = fetchPost.getAllPost(workspaceName);

        // Post Type key value set in hashmap.
        postTypeInput = new HashMap<>();
        postTypeInput.put("Announcement" , "announcement");
        postTypeInput.put("Assignment","assignment");


        for(Post post : allPost) {
            VBox singlePost = new VBox();
            Pane postPane = new Pane();
            VBox postDetails = new VBox();
            Text postText = new Text();
            Label postGiverEmail = new Label();
            Button viewDetails = new Button();

            viewDetails.setText("View Details");
            postGiverEmail.setText(post.getPost_giver_email());
            postText.setText(post.getPost_text());

            postPane.setStyle("-fx-padding: 0 0 16px 50px;");
            viewDetails.setStyle("-fx-margin: 10px 0 0 0");
            postGiverEmail.setStyle("-fx-margin: 10px 0 0 0");

            postDetails.getChildren().addAll(postText,postGiverEmail, viewDetails);
            postPane.getChildren().add(postDetails);
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

        // kept off choice list for student off
        System.out.println(currentUser.getType());
//        if(currentUser.getType().equals("s")){
//            System.out.println("Studenttype found");
//            postType.setVisible(false);
//        }


    }

    @FXML
    void postBtnClicked(ActionEvent event) throws SQLException {
        String postText = post_text_box.getText();
        System.out.println(postText);

        System.out.println(postType.getValue());

        Post post = new Post();
        post.setPost_text(postText);
        post.setPost_type(postTypeInput.get(postType.getValue()));
        post.setAttachment_link(null);
        post.setCourseCode(workspaceName);
        post.setPost_giver_email(currentUser.getEmail());
        post.setPost_giver_type(currentUser.getType());
        post.setDeadline(null);

        // generating id
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = dateFormat.format(new Date());
        Random random = new Random();
        int randomNum = random.nextInt(10000);
        randomNum %= 12;
        String uniqueCode = date + randomNum;

        post.setPostid(uniqueCode.toString());


        DbUtilities postSetting = new DbUtilities();
        postSetting.setPost(post);
//        // printing all value in console
//                System.out.println("Post ID: " + post.getPostid());
//                System.out.println("Course Code: " + post.getCourseCode());
//                System.out.println("Post Giver Email: " + post.getPost_giver_email());
//                System.out.println("Post Giver Type: " + post.getPost_giver_type());
//                System.out.println("Post Text: " + post.getPost_text());
//                System.out.println("Post Type: " + post.getPost_type());
//                System.out.println("Attachment Link: " + post.getAttachment_link());
//                System.out.println("Deadline: " + post.getDeadline());

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
