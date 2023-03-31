package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import userPack.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class SinglePostPageController {

    @FXML
    private Button backtoworkspaceBtn;

    @FXML
    private VBox comment_box;

    @FXML
    private TextField comment_textbox;

    @FXML
    private Label full_name_label;

    @FXML
    private Button postCommentBtn;

    @FXML
    private Label postType_label;

    @FXML
    private Text post_text;
    @FXML
    private  Label postDeadline_label;

    private User currentUser;

    private Student currentStudent;
    private ArrayList<Courses> registered_courses;
    private String workspaceName;

    private Post currentPost;

    @FXML
    private  Button downloadAttachmentBtn;
    public void initiate(String workspaceName, Student student, ArrayList<Courses> courses, User user, Post post) throws SQLException {
        this.workspaceName = workspaceName;
        this.currentStudent = student;
        this.currentUser = user;
        this.registered_courses = courses;
        this.currentPost = post;


        if(currentPost.getAttachment() == null) {
            downloadAttachmentBtn.setVisible(false);
        }
        postType_label.setText(post.getPost_type());
        post_text.setText(post.getPost_text());
        if(post.getDeadline() != null)
            postDeadline_label.setText("Deadline: "+post.getDeadline());


        DbUtilities fetchComment = new DbUtilities();
        ArrayList<Comment> allComment = fetchComment.getAllComments(currentPost.getPostid());


        for(Comment comment : allComment) {

            System.out.println(comment.getCommentText());
            VBox singlePost = new VBox();
            Pane postPane = new Pane();
            VBox postDetails = new VBox();
            Text commentText = new Text();
            Label commenterEmail = new Label();

            commenterEmail.setText(comment.getCommenterEmail());
            commentText.setText(comment.getCommentText());

            postPane.setStyle("-fx-padding: 0 0 16px 50px;");
            commenterEmail.setStyle("-fx-margin: 10px 0 0 0");

            postDetails.getChildren().addAll(commentText,commenterEmail);
            postPane.getChildren().add(postDetails);
            singlePost.getChildren().add(0,postPane);

            comment_box.getChildren().add(0,singlePost);
        }

    }

    @FXML
    void downloadAttachmentBtn_clicked(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            // Write the byte array data to a file with the chosen file name and path
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(currentPost.getAttachment());
            outStream.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Download Complete");
            alert.setHeaderText(null);
            alert.setContentText("The blob data has been downloaded and saved to " + file.getAbsolutePath());
            alert.showAndWait();
        }
         else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No blob data found in the database for the specified ID");
            alert.showAndWait();
        }
    }

    @FXML
    void backtoworkspaceBtnClicked(ActionEvent event){
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
    void postCommentBtnclicked(ActionEvent event) throws SQLException {
        String commentText = comment_textbox.getText();
        // Get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Create a formatter to convert to string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Convert to string
        String currentDateTimeString = currentDateTime.format(formatter);

        // generating id
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = dateFormat.format(new Date());
        Random random = new Random();
        int randomNum = random.nextInt(10000);
        randomNum %= 12;
        String uniqueCode = date + randomNum;

        Comment tempComment = new Comment();
        tempComment.setPostId(currentPost.getPostid());
        tempComment.setCommenterEmail(currentUser.getEmail());
        tempComment.setCommenterType(currentUser.getType());
        tempComment.setCommentText(commentText);
        tempComment.setCommentTime(currentDateTimeString);
        tempComment.setCommentId(uniqueCode);

        DbUtilities commentSetter = new DbUtilities();
        commentSetter.setComment(tempComment);

        // reload the page
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("singlePostPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene singlePostPageScene = new Scene(root);
        SinglePostPageController singlePostPageController = loader.getController();
        try {
            singlePostPageController.initiate(workspaceName,currentStudent, registered_courses, currentUser,currentPost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(singlePostPageScene);
        System.out.println("working");
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
