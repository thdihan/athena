package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import userPack.Admin;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ResetAllDataController {

    @FXML
    private Button confirmBtn;

    @FXML
    private Pane infoPane;

    @FXML
    private PasswordField password_textbox;

    private Admin currentAdmin;
    @FXML
    Label warning;

    @FXML
    Pane warning_box;

    public void initiate(Admin admin){
        currentAdmin = admin;
        warning_box.setVisible(false);
    }
    @FXML
    void confirmBtn_clicked(ActionEvent event) throws SQLException, NoSuchAlgorithmException {
        String pass = password_textbox.getText();
        LoginController loginController = new LoginController();
        DbUtilities dbUtilities = new DbUtilities();
        if(dbUtilities.loginNow(currentAdmin.getEmail(),loginController.encryptPassword(pass))){
            dbUtilities.reset();
        }
        else{
            System.out.println("not valid");
        }

        warning_box.setVisible(true);
        warning.setText("Reset Successful !");
        password_textbox.clear();;
    }


    public Pane getInfoPane() {
        return infoPane;
    }

}
