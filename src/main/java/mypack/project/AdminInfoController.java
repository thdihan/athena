package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import userPack.*;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** Controller class for teacher information pane in dashboard
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

public class AdminInfoController {
    User currentUser;
    @FXML
    private Label fullname_view;
    @FXML
    private Label dept_view;
    @FXML
    private Label id_view;
    @FXML
    private Label dob_view;
    @FXML
    private Label email_view;
    @FXML
    private Pane infoPane;
    @FXML
    VBox notificationPanal_Vbox;

    @FXML
    Button userDataFileChooseBtn;

    Admin currentAdmin;


    public Node getNotificationiBoxNode(Notification notification) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("notificationBox.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        NotificationBoxController notificationBoxController = loader.getController();
        notificationBoxController.initiate(notification,currentUser,infoPane);
        Node childNode=notificationBoxController.getSmNotification();
        return childNode;
    }

    public Node getResetBoxNode(String id,String type) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("notificationBox.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        NotificationBoxController notificationBoxController = loader.getController();
        notificationBoxController.resetRequestInitiate(currentUser,id,type,infoPane,currentAdmin);
        Node childNode=notificationBoxController.getResetNotification();


        return childNode;
    }
    /**
     * To initiate the teacher information in the pane
     * @param currentAdmin Teacher object containing their information
     * @param user User object of the teacher
     */
    public void initiateAdminPane(Admin currentAdmin, User user) throws SQLException, IOException {
        currentUser=user;
        email_view.setText(currentAdmin.getEmail());
        fullname_view.setText(currentAdmin.getName());
        dob_view.setText(currentAdmin.getDob().toString());
        id_view.setText(currentAdmin.getId());

        this.currentAdmin = currentAdmin;


        // get notifications
        DbUtilities dbUtilities = new DbUtilities();
        ArrayList<Pair2<String, String>> newRequest = dbUtilities.getAllReset();

        for(int i = 0;i < newRequest.size();i++){
            notificationPanal_Vbox.getChildren().add(getResetBoxNode(newRequest.get(i).getKey(),newRequest.get(i).getValue()));
            Pane separator = new Pane();
            separator.setPrefHeight(20);
            notificationPanal_Vbox.getChildren().add(separator);
        }
//        for(int i = 0;i < 5;i++){
//            if(i == allNotification.size()) break;
//            System.out.println("Working");
//            notificationPanal_Vbox.getChildren().add(getNotificationiBoxNode(allNotification.get(i)));
//            Pane separator = new Pane();
//            separator.setPrefHeight(20);
//            notificationPanal_Vbox.getChildren().add(separator);
//        }

        currentAdmin = dbUtilities.getAdminInfo(currentUser.getEmail());
    }

    /**
     * To get a reference to the pane containing the information
     * @return The pane element as node
     */
    public Node getChildNode(){
        return infoPane;
    }

//    public StudentInfoController(){
//
//    }

    /**
     * To go to update profile view
     * @param event Event of update profile button click
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    public void onUpdateProfileBtnClicked(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("updateProfile.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        UpdateProfileController updateProfileController=loader.getController();
        updateProfileController.initiateUpdateProfileController(currentUser);

        Node childNode=updateProfileController.getChildNode();
        infoPane.getChildren().clear();
        infoPane.getChildren().add(childNode);
    }


}
