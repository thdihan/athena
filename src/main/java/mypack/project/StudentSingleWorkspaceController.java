package mypack.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import userPack.Courses;
import userPack.Student;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private  ComboBox<String> am_pm;
    private  HashMap<String, String> timeTypeSelection;

    // Post that will be transferred to database
    Post sendPost;
    @FXML
    private VBox postBox;
    @FXML
    private TextArea post_text_box;

    @FXML
    private TextField time_hh;
    @FXML
    private TextField time_mm;
    @FXML
    private DatePicker deadlineDate;
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
            singlePost.getChildren().add(0,postPane);

            postBox.getChildren().add(0,singlePost);
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
    void postBtnClicked(ActionEvent event) throws SQLException, IOException {
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


        if(post.getPost_type().equals("assignment")) {
            sendPost = post;
            setDeadline(event);
        }
        else {
            DbUtilities postSetting = new DbUtilities();
            postSetting.setPost(post);
            // back to workspace
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("studentSingleWorkspacePage.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Scene afterLoginScene = new Scene(root);
            StudentSingleWorkspaceController studentSingleWorkspaceController = loader.getController();
            try {
                studentSingleWorkspaceController.initiateData(workspaceName,currentStudent, registered_courses, currentUser);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(afterLoginScene);
            window.show();
        }

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
    public  void setPost(Post post,String workspaceName, Student student, ArrayList<Courses> courses, User user) {
        this.sendPost = post;
        currentStudent = student;
        currentUser = user;
        registered_courses = courses;
        this.workspaceName = workspaceName;
    }
    public void setDeadline(ActionEvent event) throws IOException {


        System.out.println("SETDEADLINE PAGE");
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("studentSingleWorkspacePage_deadline.fxml"));
        Parent root = loader.load();
        StudentSingleWorkspaceController studentSingleWorkspaceController = loader.getController();
        studentSingleWorkspaceController.setPost(sendPost,workspaceName,currentStudent, registered_courses, currentUser);
        Scene deadlineScene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(deadlineScene);
        window.show();



    }

    @FXML
    public void onConfirmBtnClicked(ActionEvent event) throws SQLException {

        timeTypeSelection = new HashMap<>();
        timeTypeSelection.put("AM","am");
        timeTypeSelection.put("PM","pm");
//        LocalDate localDate = deadlineDate.getValue();
        String date = deadlineDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(date);

        String hour = time_hh.getText();
        String min = time_mm.getText();

        if(Integer.parseInt(hour) <= 12 && Integer.parseInt(min) <= 59){
            date += " " + hour +":"+min+":00";
            System.out.println(date);

            sendPost.setDeadline(date);
            DbUtilities postSetting = new DbUtilities();
            postSetting.setPost(sendPost);
        }
//        String timeTypeValue = am_pm.getValue();
//        System.out.println(timeTypeValue);
        //         printing all value in console
        System.out.println("Post ID: " + sendPost.getPostid());
        System.out.println("Course Code: " + sendPost.getCourseCode());
        System.out.println("Post Giver Email: " + sendPost.getPost_giver_email());
        System.out.println("Post Giver Type: " + sendPost.getPost_giver_type());
        System.out.println("Post Text: " + sendPost.getPost_text());
        System.out.println("Post Type: " + sendPost.getPost_type());
        System.out.println("Attachment Link: " + sendPost.getAttachment_link());
        System.out.println("Deadline: " + sendPost.getDeadline());



        // back to workspace
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("studentSingleWorkspacePage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene afterLoginScene = new Scene(root);
        StudentSingleWorkspaceController studentSingleWorkspaceController = loader.getController();
        try {
            studentSingleWorkspaceController.initiateData(workspaceName,currentStudent, registered_courses, currentUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(afterLoginScene);
        window.show();

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
