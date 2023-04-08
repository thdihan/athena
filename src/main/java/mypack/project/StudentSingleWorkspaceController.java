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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import userPack.Courses;
import userPack.Post;
import userPack.Student;
import userPack.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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



    // Post that will be transferred to database
    Post sendPost;
    @FXML
    private VBox postBox;
    @FXML
    private TextArea post_text_box;

    @FXML
    private Pane infoPane;

    @FXML
    private Button chooseAttachmentBtn;



    private byte[] attachment_data;
    public void initiateData(String workspaceName, User user) throws SQLException {

        currentUser = user;


        this.workspaceName = workspaceName;
        workspace_name.setText(workspaceName);
        ObservableList<String> postTypeOption = FXCollections.observableArrayList("Announcement", "Assignment");
        postType.setPromptText("Select post type");
        postType.setItems(postTypeOption);

        if(currentUser.getType().equals("s")){
            postType.setVisible(false);
        }



        DbUtilities fetchPost = new DbUtilities();
        ArrayList<Post> allPost = fetchPost.getAllPost(workspaceName);

        // Post Type key value set in hashmap.
        postTypeInput = new HashMap<>();
        postTypeInput.put("Announcement" , "announcement");
        postTypeInput.put("Assignment","assignment");


        for(Post post : allPost) {

            // Single Vbox
            VBox singlePost = new VBox();
            singlePost.setPrefWidth(834);
            singlePost.setMinHeight(140);
            singlePost.setStyle("-fx-background-color: #36373E; -fx-background-radius : 10px");

            // Post header : post giver and button
            Pane postHeaderPane = new Pane();
            postHeaderPane.setStyle("-fx-border-width : 0 0 1px 0; -fx-border-color: #666; -fx-background-radius : 10px");
            postHeaderPane.setPrefHeight(38);
            postHeaderPane.setPrefWidth(834);


            // post type
            Label postType_label = new Label();
            String postType = post.getPost_type().substring(0,1).toUpperCase() + post.getPost_type().substring(1,post.getPost_type().length());
            postType_label.setText(postType);
            postType_label.setStyle("-fx-text-fill: #fff37a; -fx-font-weight: bold");
            postType_label.setLayoutX(23);
            postType_label.setLayoutY(11);
            // Post Giver info
            Label postGiverTag = new Label();
            postGiverTag.setText("Posted By : ");
            postGiverTag.setStyle("-fx-text-fill: #fff; -fx-font-weight: bold");
            postGiverTag.setLayoutX(138);
            postGiverTag.setLayoutY(11);

            Label postGiverName = new Label();
            postGiverName.setText(post.getPost_giver_email());
            postGiverName.setStyle("-fx-text-fill: #fff");
            postGiverName.setLayoutX(213);
            postGiverName.setLayoutY(11);

            Button viewDetails = new Button();
            viewDetails.setText("View Details");
            viewDetails.setLayoutX(718);
            viewDetails.setLayoutY(7);
            viewDetails.setStyle("-fx-text-fill: #fff37a;-fx-background-color: trainsparent;-fx-font-weight: bold");

            postHeaderPane.getChildren().addAll(postType_label,postGiverTag,postGiverName,viewDetails);


            // Post Text Pane
            Pane postTextPane = new Pane();
            postTextPane.setPrefWidth(834);

            Text postText = new Text();
            postText.setText(post.getPost_text());
            postText.setLayoutX(22);
            postText.setLayoutY(17);
            postText.setStyle("-fx-fill: #fff;-fx-line-spacing: 5");
            postText.setWrappingWidth(800);
            postText.setLineSpacing(5);


            postTextPane.getChildren().add(postText);


            singlePost.getChildren().addAll(postHeaderPane,postTextPane);


//            postDetails.getChildren().addAll(postText,postGiverEmail, viewDetails);
//            postPane.getChildren().add(postDetails);
//            singlePost.getChildren().add(0,postPane);


            // View Details button action
            viewDetails.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("singlePostPage.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                SinglePostPageController singlePostPageController = loader.getController();
                try {
                    singlePostPageController.initiate(workspaceName,currentUser,post);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                infoPane.getChildren().clear();
                infoPane.getChildren().add(singlePostPageController.getPane());
            });

            Pane separator = new Pane();
            separator.setPrefHeight(20);
            postBox.getChildren().add(0,singlePost);
            postBox.getChildren().add(1,separator);
//             printing all value in console
//                System.out.println("Post ID: " + post.getPostid());
//                System.out.println("Course Code: " + post.getCourseCode());
//                System.out.println("Post Giver Email: " + post.getPost_giver_email());
//                System.out.println("Post Giver Type: " + post.getPost_giver_type());
//                System.out.println("Post Text: " + post.getPost_text());
//                System.out.println("Post Type: " + post.getPost_type());
//                System.out.println("Attachment Link: " + post.getAttachment());
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
    void chooseAttachmentBtnClicked(ActionEvent event) throws IOException {
        // Create a FileChooser dialog box to allow the user to select the PDF file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File pdfFile = fileChooser.showOpenDialog(null);


        if (pdfFile != null) {
            // Read the contents of the PDF file into a byte array
            FileInputStream inputStream = new FileInputStream(pdfFile);
            byte[] pdfData = new byte[(int) pdfFile.length()];
            inputStream.read(pdfData);
            inputStream.close();

            this.attachment_data = pdfData;
            System.out.println(attachment_data);
        }
    }

    @FXML
    void postBtnClicked(ActionEvent event) throws SQLException, IOException {
        String postText = post_text_box.getText();

        System.out.println(postText);

        System.out.println(postType.getValue());

        Post post = new Post();
        post.setPost_text(postText);

        if(currentUser.getType().equals("s")){
            post.setPost_type("announcement");
        }
        else{
            post.setPost_type(postTypeInput.get(postType.getValue()));
        }

        post.setAttachment(null);
        post.setCourseCode(workspaceName);
        post.setPost_giver_email(currentUser.getEmail());
        post.setPost_giver_type(currentUser.getType());
        post.setDeadline(null);
        post.setAttachment(attachment_data);

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

//        // printing all value in console
//                System.out.println("Post ID: " + post.getPostid());
//                System.out.println("Course Code: " + post.getCourseCode());
//                System.out.println("Post Giver Email: " + post.getPost_giver_email());
//                System.out.println("Post Giver Type: " + post.getPost_giver_type());
//                System.out.println("Post Text: " + post.getPost_text());
//                System.out.println("Post Type: " + post.getPost_type());
//                System.out.println("Attachment Link: " + post.getAttachment());
//                System.out.println("Deadline: " + post.getDeadline());

    }
    public  void setPost(Post post,String workspaceName, Student student, ArrayList<Courses> courses, User user) {
        this.sendPost = post;
        currentStudent = student;
        currentUser = user;
        registered_courses = courses;
        this.workspaceName = workspaceName;


    }
    public void setDeadline(ActionEvent event) throws IOException, SQLException {


        System.out.println("SETDEADLINE PAGE");
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("studentSingleWorkspacePage_deadline.fxml"));
        Parent root = loader.load();
        SetDeadlineController setDeadlineController = loader.getController();
        setDeadlineController.initiateData(workspaceName,currentUser,sendPost);

        infoPane.getChildren().clear();
        infoPane.getChildren().add(setDeadlineController.getInfoPane());
//        studentSingleWorkspaceController.setPost(sendPost,workspaceName,currentStudent, registered_courses, currentUser);
//        Scene deadlineScene = new Scene(root);
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene(deadlineScene);
//        window.show();



    }


    public Pane getPane() {
        return infoPane;
    }

    public  String getUiName() {
        return "Registered Courses";
    }



}
