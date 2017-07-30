package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Macaxeira é o poder");
        primaryStage.setScene(new Scene(root, 300, 100));
        primaryStage.show();
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
