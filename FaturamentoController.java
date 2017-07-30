package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mariana on 28/07/17.
 */
public class FaturamentoController implements Initializable{

    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void faturamentoTela2(Double f){
        this.label.setText(String.valueOf(f));
    }
}
