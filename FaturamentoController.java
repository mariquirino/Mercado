package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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

    public void faturamentoTela(Double f){
        this.label.setText("Faturamento: R$" + String.valueOf(f));
    }
}
