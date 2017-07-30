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
import java.util.ResourceBundle;

public class Controller implements Initializable{
    ArrayList<Produto> produtos = new ArrayList<>();
    ObservableList<Produto> observableList = FXCollections.observableList(produtos);
    double faturamento = 0;

    public double getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(double faturamento) {
        this.faturamento = faturamento;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    @FXML
    private Button gerenciaButton;

    @FXML
    private Button usuarioButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        produtos.add(new Produto("a",1,1.0,"1"));
        gerenciaButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gerencia.fxml"));
            try {
                Parent root = loader.load();
                GerenciaController control = (GerenciaController)loader.getController();
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

        usuarioButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cliente.fxml"));
            try {
                Parent root = (Parent) loader.load();
                ClienteController control = (ClienteController)loader.getController();
                control.setTableViewUser();
                control.inserirCarrinho(produtos);
                control.finalizarCompra(faturamento);
                control.removerUser(produtos);

                Scene scene = new Scene(root, 400, 400);
                Stage stage = new Stage();
                stage.setTitle("Carrinho");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        produtos.add(new Produto("a",1,1.0,"1"));
//        produtos.add(new Produto("b",2,2.0,"2"));
//        produtos.add(new Produto("c",3,3.0,"3"));

        //INTERFACE GERENTE



        //INTERFACE USUARIO


    }
}
