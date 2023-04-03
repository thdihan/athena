package mypack.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import userPack.Post;
import userPack.Submission;
import userPack.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SeeSubmissionController {

    @FXML
    private Pane infoPane;

    @FXML
    private VBox submissionBox;

    String workspaceName;
    User currentUser;
    Post currentPost;
    public void initiate(String workspaceName, Post post,User currentUser) throws SQLException {
        this.workspaceName = workspaceName;
        this.currentPost = post;
        this.currentUser = currentUser;
        DbUtilities dbUtilities = new DbUtilities();
        submissionBox.setSpacing(10);
        ArrayList<Submission> allSubmission=dbUtilities.getAllSubmission(post.getPostid());

        for(int i = 0;i < allSubmission.size();i++) {
            Button submissionButton = new Button();
            submissionButton.setText(currentUser.getEmail());
            submissionButton.setPrefWidth(300);
            submissionButton.setPrefHeight(50);
            // css for button
            submissionButton.setStyle("-fx-background-color:#FFF37B; -fx-background-radius: 10px;-fx-border-radius:10px;-fx-font-weight: bold; -fx-font-size: 14px");
            submissionButton.setOnMouseEntered(e -> {
                submissionButton.setStyle("-fx-background-color:#36373E; -fx-background-radius: 10px;-fx-border-radius:10px;-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #fff; -fx-border-width: 1px;-fx-border-color: #666");
            });

            submissionButton.setOnMouseExited(e -> {
                submissionButton.setStyle("-fx-background-color:#FFF37B; -fx-background-radius: 10px;-fx-border-radius:10px;-fx-font-weight: bold; -fx-font-size: 14px;-fx-text-fill: #000");
            });

            int finalI = i;
            submissionButton.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                fileChooser.setInitialFileName(currentUser.getEmail());
                File file = fileChooser.showSaveDialog(null);

                if (file != null) {
                    // Write the byte array data to a file with the chosen file name and path
                    FileOutputStream outStream = null;
                    try {
                        outStream = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        outStream.write(allSubmission.get(finalI).getSubmissionFile());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        outStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

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
            });
//            submissionButton.setText();
            submissionBox.getChildren().add(submissionButton);

        }

    }
    public Pane getInfoPane()
    {
        return infoPane;
    }


    @FXML
    public  void backtoworkspaceBtnClicked(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("singlePostPage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SinglePostPageController singlePostPageController = loader.getController();
        try {
            singlePostPageController.initiate(workspaceName,currentUser,currentPost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        infoPane.getChildren().clear();
        infoPane.getChildren().add(singlePostPageController.getPane());
    }
}
