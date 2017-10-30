package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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

    //Inicializando a TableView
    public void settableView(ObservableList<Produto> observableList){
        tableView.setItems(observableList);
        produtoColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("cod"));
        estoqueColumn.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        precoColumn.setCellValueFactory(new PropertyValueFactory<>("preco"));
    }

    public void inserir(ObservableList<Produto> observableList){
        inserirButton.setOnAction(event -> {
            for (Produto produto : produtos) {
                //Verificando se o produto ja existe e o gerente vai alterar o estoque e preço
                if (produto.getNome().equals(nomeTextField.getText()) && produto.getCod().equals(cogigoTextField.getText())) {
                    produto.setEstoque(produto.getEstoque() + Integer.parseInt(estoqueTextField.getText()));
                    produto.setPreco(Integer.parseInt(precoTextField.getText()));
                    return;
                }else if(produto.getNome().equals(nomeTextField.getText()) || produto.getCod().equals(cogigoTextField.getText())){
                    //Se o produto ja existir e ele digitar um codigo ou nome diferente, exibi mensagem de erro
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Nome ou Codigo do produto errados.");
                    alert.setContentText("Você digitou um nome ou codigo incorretos!");
                    alert.showAndWait();
                    return;
                }
            }
            //Saiu do for então não existe nenhum produco com o codigo e nome que deseja inserir, portanto inserir um novo produto
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
        });
    }

    //Inicializando a Array Produtos
    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public void atualizar(){
        atualizarButton.setOnAction(event -> tableView.refresh());
    }

    public void remover(){
        //Selecionando o item que deseja remover e removendo
        SelectionModel<Produto> selectionModel = tableView.getSelectionModel();
        removerButton.setOnAction(event -> {
            Produto produto = selectionModel.getSelectedItem();
            produtos.remove(produto);
        });
    }

    public void faturamento(double[] faturamento){
        faturamentoButton.setOnAction(event -> {
            //Chamando uma nova janela para exibir o faturamento
            FXMLLoader loader = new FXMLLoader(FaturamentoController.class.getResource("faturamento.fxml"));
            try {
                Parent root = loader.load();
                FaturamentoController control = loader.getController();
                //Passando o faturamento para a nova janela
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

