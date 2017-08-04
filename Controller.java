package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    ArrayList<Produto> produtos = new ArrayList<>();
    ObservableList<Produto> observableList = FXCollections.observableList(produtos);
    double[] faturamento = {0};

    @FXML
    private Button gerenciaButton;

    @FXML
    private Button usuarioButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        produtos.add(new Produto("Abacaxi",1,1.0,"1"));
        produtos.add(new Produto("Uva",5,2.0,"2"));
        produtos.add(new Produto("Laranja",10,3.0,"3"));

        //Inicializando a janela de Gerencia
        gerenciaButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gerencia.fxml"));
            try {
                Parent root = loader.load();
                GerenciaController control = loader.getController();
                //Opcoes disponiveis em Gerencia
                control.setProdutos(produtos);
                control.settableView(observableList);
                control.inserir(observableList);
                control.atualizar();
                control.remover();
                control.faturamento(faturamento);
                Scene scene = new Scene(root, 400, 400);
                Stage stage = new Stage();
                stage.setTitle("Gerencia");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Inicializando a janela do Cliente
        usuarioButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cliente.fxml"));
            try {
                Parent root = loader.load();
                ClienteController control = loader.getController();
                //Opcoes disponiveis em Cliente
                control.setTableViewUser();
                control.inserirCarrinho(produtos);
                control.finalizarCompra(faturamento);
                control.removerUser(produtos);
                Scene scene = new Scene(root, 400, 400);
                Stage stage = new Stage();
                stage.setTitle("Carrinho");
                stage.setScene(scene);
                //Caso o cliente feche a janela
                stage.setOnCloseRequest(event1 -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Cancelamento de compra.");
                    alert.setContentText("Tem certeza que deseja sair?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        control.removerAll(produtos);
                    }
                });
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
