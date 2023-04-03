package mypack.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import userPack.Courses;
import userPack.Post;
import userPack.Student;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class SetDeadlineController {

    @FXML
    private Pane infoPane;
    @FXML
    private TextField time_hh;
    @FXML
    private TextField time_mm;
    @FXML
    private DatePicker deadlineDate;
    private User currentUser;
    private String workspaceName;

    @FXML
    private ComboBox<String> am_pm;
    private  HashMap<String, String> timeTypeSelection;
    Post sendPost;
    Post currentPost;
    public void initiateData(String workspaceName,  User user, Post post) throws SQLException {
        currentUser = user;
        this.workspaceName = workspaceName;
        sendPost = post;

        ObservableList<String> timeTypeSet = FXCollections.observableArrayList("AM", "PM");
        am_pm.setItems(timeTypeSet);
        am_pm.setValue("AM");
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
        String timeformat = am_pm.getValue();
        if(Integer.parseInt(hour) <= 12 && Integer.parseInt(min) <= 59){
            date += " " + hour +":"+min+" "+timeformat;
            System.out.println(date);

            sendPost.setDeadline(date);
            DbUtilities postSetting = new DbUtilities();
            postSetting.setPost(sendPost);
        }

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
            studentSingleWorkspaceController.initiateData(workspaceName, currentUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        infoPane.getChildren().clear();
        infoPane.getChildren().add(studentSingleWorkspaceController.getPane());


    }

    public  Pane getInfoPane() {
        return infoPane;
    }
}
