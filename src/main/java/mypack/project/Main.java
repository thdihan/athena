package mypack.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/** Main class of the project
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */
public class Main extends Application {
    private Stage stage;

    /**
     * Function to initiate tables
     * @throws SQLException If problems with query
     */
    public void initiateTables() throws SQLException {
        DbUtilities dbUtilities=new DbUtilities();
        dbUtilities.initiateTablesWithQuery();



    }

    /**
     * Function to start/initiate the project
     * @param primaryStage The initial window/stage of the application
     * @throws IOException If problems with input/output
     * @throws SQLException If problems with query
     */
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        stage=primaryStage;
        Parent root= FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        Scene scene = new Scene(root);

//        primaryStage.getIcons().add(new Image("online-learning.png"));
        primaryStage.setTitle("Athena: Making student life easier");
        primaryStage.setResizable(false);

        initiateTables();

        primaryStage.setScene(scene);

        primaryStage.show();
    }

//    public void changeScene(String fxml) throws IOException{
//        Parent root =FXMLLoader.load(getClass().getResource(fxml));
//        stage.getScene().setRoot(root);
//    }

    /**
     * Main function of the project
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}