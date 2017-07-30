package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Mariana on 29/07/2017.
 */
public class ClienteController {
    ArrayList<Compra> compras = new ArrayList<>();
    ObservableList<Compra> observableList = FXCollections.observableList(compras);
    double fat = 0;

    @FXML
    private Button inserirProdButton;

    @FXML
    private TableColumn<Produto, Integer> UserqtdColumn;

    @FXML
    private TextField qtdTextField;

    @FXML
    private TableColumn<Produto, String> UserprodutoColumn;

    @FXML
    private TableView<Compra> tableViewUser;

    @FXML
    private Button removerProdButton;

    @FXML
    private Button comprarButton;

    @FXML
    private TextField codigoProdTextField;

    @FXML
    private TableColumn<Produto, String> UsercodigoColumn;

    @FXML
    private TableColumn<Produto, Double> UserprecoColumn;


    public void setTableViewUser() {
        tableViewUser.setItems(observableList);
        UserprodutoColumn.setCellValueFactory(new PropertyValueFactory<>("prod"));
        UsercodigoColumn.setCellValueFactory(new PropertyValueFactory<>("cod"));
        UserprecoColumn.setCellValueFactory(new PropertyValueFactory<>("preco"));
        UserqtdColumn.setCellValueFactory(new PropertyValueFactory<>("qtd"));
    }

    public void inserirCarrinho(ArrayList<Produto> produtos) {
        inserirProdButton.setOnAction(event -> {
            //se der agrupar o qtd quando o msm pedido for feito mais de uma vez
            for (int i = 0; i < produtos.size(); i++) {
                if (produtos.get(i).getCod().equals(codigoProdTextField.getText())) {
                    try {
                        produtos.get(i).saida(Integer.parseInt(qtdTextField.getText()));
                        observableList.add(new Compra(produtos.get(i).getNome(), produtos.get(i).getCod(), produtos.get(i).getPreco(), Integer.parseInt(qtdTextField.getText())));
                        tableViewUser.refresh();
                    } catch (ProdutoException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Estoque Excedido.");
                        alert.setContentText("Quantidade maior que em estoque!");
                        alert.showAndWait();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void finalizarCompra(double faturamento) {
        comprarButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de compra");
            alert.setHeaderText("Finalização do Pedido.");
            alert.setContentText("Tem certerza que deseja finalizar a compra?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                for (Compra compra : compras) {
                    faturamento += compra.getPreco() * ((double) compra.getQtd());
                }
                compras.clear();
                tableViewUser.refresh();
            }
        });
    }

    public void removerUser(ArrayList<Produto> produtos) {
        SelectionModel<Compra> userSelectionModel = tableViewUser.getSelectionModel();
        removerProdButton.setOnAction(event -> {
            Compra compra = userSelectionModel.getSelectedItem();
            //Tentar melhorar isso de adicionar o estaque na hr de remover
            for (Produto produto : produtos) {
                if (produto.getCod().equals(compra.getCod())) {
                    produto.setEstoque(produto.getEstoque() + compra.getQtd());
                }
            }
            compras.remove(compra);
            tableViewUser.refresh();
        });
    }
}

