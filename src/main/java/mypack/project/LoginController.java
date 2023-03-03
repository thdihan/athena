package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passField;
    @FXML
    private Label wrongField;

    public void loginCode(ActionEvent event) {
        try {
            DbUtilities dbUtilities = new DbUtilities();
            if(emailField.getText().isEmpty() || passField.getText().isEmpty())
                wrongField.setText("Email and password field can't be left empty !!");
            else if (dbUtilities.loginNow(emailField.getText(), passField.getText())) {
                Main m = new Main();
                wrongField.setText("Valid username and password !!");
//                newUser = new User(userTxt.getText(), passTxt.getText());
//                changeSceneAnother("afterlogin.fxml", event);
//                m.changeScene("afterlogin.fxml");
            } else {
                wrongField.setText("Invalid username or password !!");
            }
        } catch (SQLException e) {
            System.out.println(e);
            wrongField.setText("Login check error");
        }
    }
}
