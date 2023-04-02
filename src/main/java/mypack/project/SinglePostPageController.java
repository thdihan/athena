package mypack.project;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import userPack.*;

import java.io.*;
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
    private TextArea comment_textbox;

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

    @FXML
    private Pane infoPane;

    private User currentUser;

    private Student currentStudent;
    private ArrayList<Courses> registered_courses;
    private String workspaceName;

    private Post currentPost;

    @FXML
    private  Button downloadAttachmentBtn;

    @FXML
    private  Button submissionBtn;
    @FXML
    private  Button selectSubmissionBtn;

    @FXML
    Button seeSubmissionbtn;

    Submission currentSubmission;
    public void initiate(String workspaceName, Student student, ArrayList<Courses> courses, User user, Post post) throws SQLException {
        this.workspaceName = workspaceName;
        this.currentStudent = student;
        this.currentUser = user;
        this.registered_courses = courses;
        this.currentPost = post;

        // Button Visibility
        if(currentPost.getAttachment() == null) {
            downloadAttachmentBtn.setVisible(false);
        }

        if(currentUser.getType().equals("s") && currentPost.getPost_type().equals("assignment")) {
            submissionBtn.setVisible(true);
            selectSubmissionBtn.setVisible(true);
        }
        else{
            submissionBtn.setVisible(false);
            selectSubmissionBtn.setVisible(false);
        }
        if(currentUser.getType().equals("t") && currentPost.getPost_type().equals("assignment")) {
            seeSubmissionbtn.setVisible(true);
        }
        else{
            seeSubmissionbtn.setVisible(false);
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
    void selectSubmissionBtn_clicked(ActionEvent event) throws IOException {
// Create a FileChooser dialog box to allow the user to select the PDF file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File pdfFile = fileChooser.showOpenDialog(null);
        currentSubmission = new Submission();

        if (pdfFile != null) {
            // Read the contents of the PDF file into a byte array
            FileInputStream inputStream = new FileInputStream(pdfFile);
            byte[] pdfData = new byte[(int) pdfFile.length()];
            inputStream.read(pdfData);
            inputStream.close();


            // setData
            currentSubmission.setSubmissionFile(pdfData);
        }
    }

    @FXML
    void submissionBtn_clicked(ActionEvent event) throws SQLException {
        // generating id
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = dateFormat.format(new Date());
        Random random = new Random();
        int randomNum = random.nextInt(10000);
        randomNum %= 12;
        String uniqueCode = date + randomNum;

        // Get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentDateTimeString = currentDateTime.format(formatter);


        currentSubmission.setSubmissionId(uniqueCode);
        currentSubmission.setPostId(currentPost.getPostid());
        currentSubmission.setSubmitterEmail(currentUser.getEmail());
        currentSubmission.setSubmissionTime(currentDateTimeString);

        DbUtilities setSubmissiontoDatabase = new DbUtilities();
        setSubmissiontoDatabase.setSubmission(currentSubmission,currentPost);
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
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StudentSingleWorkspaceController studentSingleWorkspaceController = loader.getController();
        try {
            studentSingleWorkspaceController.initiateData(workspaceName,currentStudent, registered_courses, currentUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        infoPane.getChildren().clear();
        infoPane.getChildren().add(studentSingleWorkspaceController.getPane());
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
        commentSetter.setComment(tempComment,currentPost);

        // reload the page
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("singlePostPage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SinglePostPageController singlePostPageController = loader.getController();
        try {
            singlePostPageController.initiate(workspaceName,currentStudent, registered_courses, currentUser,currentPost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        infoPane.getChildren().clear();
        infoPane.getChildren().add(singlePostPageController.getPane());
    }


    @FXML
    public void seeSubmissionbtn_clicked(){}

    public Pane getPane() {
        return infoPane;
    }

    public  String getUiName() {
        return "Marks";
    }



}
