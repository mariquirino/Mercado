package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
            for (Produto produto: produtos) {
                if (produto.getCod().equals(codigoProdTextField.getText())){
                    try {
                        produto.saida(Integer.parseInt(qtdTextField.getText()));
                        for (Compra compra: compras) {
                            if (compra.getCod().equals(codigoProdTextField.getText())){
                                compra.setQtd(compra.getQtd() + Integer.parseInt(qtdTextField.getText()));
                                tableViewUser.refresh();
                                return;
                            }
                        }
                        observableList.add(new Compra(produto.getNome(), produto.getCod(), produto.getPreco(), Integer.parseInt(qtdTextField.getText())));
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

    public void finalizarCompra(double[] faturamento) {
        comprarButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de compra");
            alert.setHeaderText("Finalização do Pedido.");
            alert.setContentText("Tem certerza que deseja finalizar a compra?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                for (Compra compra : compras) {
                    faturamento[0] += compra.getPreco() * compra.getQtd();
                }
                compras.clear();
                tableViewUser.refresh();
            }
        });
    }

    public void removerUser(ArrayList<Produto> produtos) {
        SelectionModel<Compra> selectionModel = tableViewUser.getSelectionModel();
        removerProdButton.setOnAction(event -> {
            Compra compra = selectionModel.getSelectedItem();
            if (compra != null) {
                for (Produto produto : produtos) {
                    if (produto.getCod().equals(compra.getCod())) {
                        produto.setEstoque(produto.getEstoque() + compra.getQtd());
                        compra.setQtd(0);
                    }
                }
                compras.remove(compra);
                tableViewUser.refresh();
            }
        });
    }
}
