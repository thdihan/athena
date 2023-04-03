package mypack.project;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import userPack.Notification;
import userPack.Post;
import userPack.Student;
import userPack.User;

import java.io.IOException;
import java.sql.SQLException;

public class NotificationBoxController {

    @FXML
    private Text notification_text_sm;

    @FXML
    private Label notification_time_sm;

    @FXML
    private Pane single_notification_box_sm;

    @FXML
    private Button viewDetailsBtn;

    Notification currentNotification;

    User currentUser;
    Pane infoPane;

    Button approvebtn;

    @FXML
    Label reset_id;

    @FXML
    Pane resetNotification;

    String currentID;
    String currentType;
    public void initiate(Notification notification, User currentUser,Pane infoPane){
        this.infoPane = infoPane;
        this.currentUser = currentUser;
        currentNotification  = notification;
        notification_text_sm.setText(notification.getNotificationText());
        notification_time_sm.setText(notification.getNotifacationDeadline());
        viewDetailsBtn.setId("viewDetailsNotificationBtn");
    }

    public void resetRequestInitiate(User currentUser, String id,String type, Pane infoPane){
        this.infoPane = infoPane;
        this.currentUser = currentUser;
        reset_id.setText(id);
        currentID = id;
        currentType = type;
    }

    public  Pane getResetNotification() {
        return resetNotification;
    }
    public Pane getSmNotification() {
        return single_notification_box_sm;
    }
    @FXML
    void viewDetailsBtnClicked(ActionEvent event) throws SQLException {
        DbUtilities dbUtilities = new DbUtilities();
        Post post = dbUtilities.getPost(currentNotification.getPostId());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("singlePostPage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SinglePostPageController singlePostPageController = loader.getController();
        try {
            singlePostPageController.initiate(post.getCourseCode(), currentUser,post);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        infoPane.getChildren().clear();
        infoPane.getChildren().add(singlePostPageController.getPane());
    }


    @FXML
    public  void approvebtn_clicked(ActionEvent event) throws SQLException {
        DbUtilities dbUtilities = new DbUtilities();
        System.out.println(currentType + currentID);
        if(currentType.equals("s")){
            dbUtilities.delete_studentTakesCourse(currentID);
        }
        if(currentType.equals("t")){
            dbUtilities.delete_teacherTakesCourse(currentID);
        }
        dbUtilities.delete_resetRequest(currentID);
    }

    @FXML
    public  void declineButton_clicked(ActionEvent event) throws SQLException {
        DbUtilities dbUtilities = new DbUtilities();
        dbUtilities.delete_resetRequest(currentID);
    }

}

