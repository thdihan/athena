package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    public void initiate(Admin admin){
        currentAdmin = admin;
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
    }


    public Pane getInfoPane() {
        return infoPane;
    }

}
