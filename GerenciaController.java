package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Mariana on 29/07/2017.
 */
public class GerenciaController{
    private ArrayList<Produto> produtos = new ArrayList<>();

    @FXML
    private TableColumn<Produto, String> produtoColumn;

    @FXML
    private TableColumn<Produto, String> codigoColumn;

    @FXML
    private TableColumn<Produto, Integer> estoqueColumn;

    @FXML
    private TableView<Produto> tableView;

    @FXML
    private Button atualizarButton;

    @FXML
    private TableColumn<Produto, Double> precoColumn;

    @FXML
    private TextField precoTextField;

    @FXML
    private Button faturamentoButton;

    @FXML
    private TextField nomeTextField;

    @FXML
    private Button removerButton;

    @FXML
    private Button inserirButton;

    @FXML
    private TextField cogigoTextField;

    @FXML
    private TextField estoqueTextField;

    public void settableView(ObservableList<Produto> observableList){
        tableView.setItems(observableList);
        produtoColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("cod"));
        estoqueColumn.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        precoColumn.setCellValueFactory(new PropertyValueFactory<>("preco"));
    }

    public void inserir(ObservableList<Produto> observableList){
        inserirButton.setOnAction(event -> {
            int flag = 0;
            for (Produto produto : produtos) {
                if (produto.getNome().equals(nomeTextField.getText()) || produto.getCod().equals(cogigoTextField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Nome ou Codigo do produto errados.");
                    alert.setContentText("Você digitou um nome ou codigo já existente!");
                    alert.showAndWait();
                    flag = 1;
                }
            }
            if (flag == 0) {
                try {
                    observableList.add(new Produto(nomeTextField.getText(), Integer.parseInt(estoqueTextField.getText()), Double.parseDouble(precoTextField.getText()), cogigoTextField.getText()));
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Numero invalido.");
                    alert.setContentText("Você digitou um numero invalido em preço ou estoque!");
                    alert.showAndWait();
                    e.printStackTrace();
                }
            }
        });
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public void atualizar(){
        atualizarButton.setOnAction(event -> tableView.refresh());
    }

    public void remover(){
        SelectionModel<Produto> selectionModel = tableView.getSelectionModel();
        removerButton.setOnAction(event -> {
            Produto produto = selectionModel.getSelectedItem();
            produtos.remove(produto);
        });
    }

    public void faturamento(double[] faturamento){
        faturamentoButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(FaturamentoController.class.getResource("faturamento.fxml"));
            try {
                Parent root = loader.load();
                FaturamentoController control = loader.getController();
                control.faturamentoTela(faturamento[0]);
                Scene scene = new Scene(root, 300, 100);
                Stage stage = new Stage();
                stage.setTitle("Faturamento");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

