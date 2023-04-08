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
    @FXML
    Pane submissionBtnPane;

    @FXML
    VBox submissionVbox;

    Submission currentSubmission;
    public void initiate(String workspaceName, User user, Post post) throws SQLException {
        this.workspaceName = workspaceName;
//        this.currentStudent = student;
        this.currentUser = user;
//        this.registered_courses = courses;
        this.currentPost = post;

        // Button Visibility
        if(currentPost.getAttachment() == null) {
            downloadAttachmentBtn.setVisible(false);
        }

        if(currentUser.getType().equals("s") && currentPost.getPost_type().equals("assignment")) {
            submissionVbox.setVisible(true);
            submissionVbox.getChildren().clear();
            selectSubmissionBtn.setLayoutY(0);
            submissionBtn.setLayoutY(50);
            submissionVbox.getChildren().addAll(selectSubmissionBtn,submissionBtn);
        }

        else if(currentUser.getType().equals("t") && currentPost.getPost_type().equals("assignment")) {
            submissionVbox.setVisible(true);
            submissionVbox.getChildren().clear();
            submissionVbox.getChildren().add(seeSubmissionbtn);
        }
        else {
            submissionVbox.setVisible(false);
        }
        String postType = post.getPost_type().substring(0,1).toUpperCase() + post.getPost_type().substring(1,post.getPost_type().length());
        postType_label.setText(postType);
        post_text.setText(post.getPost_text());
        if(post.getDeadline() != null)
            postDeadline_label.setText("Deadline: "+post.getDeadline());


        DbUtilities fetchComment = new DbUtilities();
        ArrayList<Comment> allComment = fetchComment.getAllComments(currentPost.getPostid());


        for(Comment comment : allComment) {

            // Single Vbox
            VBox singlePost = new VBox();
            singlePost.setPrefWidth(834);
            singlePost.setMinHeight(100);
            singlePost.setStyle("-fx-background-color: #36373E; -fx-background-radius : 10px");

            // Post header : post giver and button
            Pane postHeaderPane = new Pane();
            postHeaderPane.setStyle("-fx-border-width : 0 0 1px 0; -fx-border-color: #666; -fx-background-radius : 10px");
            postHeaderPane.setPrefHeight(38);
            postHeaderPane.setPrefWidth(834);

            // Post Giver info
            Label postGiverTag = new Label();
            postGiverTag.setText("Commented By : ");
            postGiverTag.setStyle("-fx-text-fill: #fff; -fx-font-weight: bold");
            postGiverTag.setLayoutX(23);
            postGiverTag.setLayoutY(11);

            Label postGiverName = new Label();
            postGiverName.setText(comment.getCommenterEmail());
            postGiverName.setStyle("-fx-text-fill: #fff");
            postGiverName.setLayoutX(125);
            postGiverName.setLayoutY(11);

            postHeaderPane.getChildren().addAll(postGiverTag,postGiverName);

            // Post Text Pane
            Pane postTextPane = new Pane();
            postTextPane.setPrefWidth(834);

            Text postText = new Text();
            postText.setText(comment.getCommentText());
            postText.setLayoutX(22);
            postText.setLayoutY(17);
            postText.setStyle("-fx-fill: #fff");

            postTextPane.getChildren().add(postText);


            singlePost.getChildren().addAll(postHeaderPane,postTextPane);
            Pane separator = new Pane();
            separator.setPrefHeight(20);

            comment_box.getChildren().add(0,singlePost);
            comment_box.getChildren().add(1,separator);
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
            studentSingleWorkspaceController.initiateData(workspaceName, currentUser);
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
            singlePostPageController.initiate(workspaceName, currentUser,currentPost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        infoPane.getChildren().clear();
        infoPane.getChildren().add(singlePostPageController.getPane());
    }


    @FXML
    public void seeSubmissionbtn_clicked(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("seeSubmission.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SeeSubmissionController seeSubmissionController = loader.getController();
        try {
            seeSubmissionController.initiate(workspaceName,currentPost,currentUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        infoPane.getChildren().clear();
        infoPane.getChildren().add(seeSubmissionController.getInfoPane());

    }

    public Pane getPane() {
        return infoPane;
    }

    public  String getUiName() {
        return "Marks";
    }



}
