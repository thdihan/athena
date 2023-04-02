package mypack.project;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import userPack.Notification;

public class NotificationBoxController {

    @FXML
    private Text notification_text_sm;

    @FXML
    private Label notification_time_sm;

    @FXML
    private Pane single_notification_box_sm;

    @FXML
    private Button viewDetailsBtn;


    public void initiate(Notification notification){
        notification_text_sm.setText(notification.getNotificationText());
        notification_time_sm.setText(notification.getNotifacationDeadline());
    }

    public Pane getSmNotification() {
        return single_notification_box_sm;
    }
    @FXML
    void viewDetailsBtnClicked(ActionEvent event) {

    }

}

