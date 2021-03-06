package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Mariana on 28/07/17.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Supermercado");
        primaryStage.setScene(new Scene(root, 300, 100));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
