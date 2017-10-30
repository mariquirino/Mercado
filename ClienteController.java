package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Optional;

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

    //Inicializando a TableView
    public void setTableViewUser() {
        tableViewUser.setItems(observableList);
        UserprodutoColumn.setCellValueFactory(new PropertyValueFactory<>("prod"));
        UsercodigoColumn.setCellValueFactory(new PropertyValueFactory<>("cod"));
        UserprecoColumn.setCellValueFactory(new PropertyValueFactory<>("preco"));
        UserqtdColumn.setCellValueFactory(new PropertyValueFactory<>("qtd"));
    }

    public void inserirCarrinho(ArrayList<Produto> produtos) {
        inserirProdButton.setOnAction(event -> {
            //Verificando se o produto que eu desejo comprar consta em produtos
            for (Produto produto: produtos) {
                if (produto.getCod().equals(codigoProdTextField.getText())){
                    //Achou o produto que desejo inserir no carrinho
                    try {
                        //Removendo do estoque da gerencia
                        produto.saida(Integer.parseInt(qtdTextField.getText()));
                        //Verificando se ele ja existe no carrinho
                        for (Compra compra: compras) {
                            if (compra.getCod().equals(codigoProdTextField.getText())){
                                //Se existir no carrinho eu adiciono a qtd
                                compra.setQtd(compra.getQtd() + Integer.parseInt(qtdTextField.getText()));
                                tableViewUser.refresh();
                                return;
                            }
                        }
                        //Se nao exitir no carrinho eu insiro um novo
                        observableList.add(new Compra(produto.getNome(), produto.getCod(), produto.getPreco(), Integer.parseInt(qtdTextField.getText())));
                        tableViewUser.refresh();
                    } catch (ProdutoException e) {
                        //Se tentar retirar mais do que existe em estoque exibir erro
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
            //Dialog para a pessoa confirmar a compra
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de compra");
            alert.setHeaderText("Finalização do Pedido.");
            alert.setContentText("Tem certerza que deseja finalizar a compra?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //Se a compra for confirmada aumentar o faturamento e limpar o carrinho para novas compras
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
                        //Se o codigo que eu desejo remover for igual oq consta em produtos, devolver ao estoque
                        produto.setEstoque(produto.getEstoque() + compra.getQtd());
                        compra.setQtd(0);
                    }
                }
                //Removendo o item selecionado
                compras.remove(compra);
                tableViewUser.refresh();
            }
        });
    }

    //Caso a pessoa feche o carrinho devolver ao estoque
    public void removerAll(ArrayList<Produto> produtos){
        for (Compra compra: compras) {
            for (Produto produto : produtos) {
                if (produto.getCod().equals(compra.getCod())) {
                    //Se o codigo que eu desejo remover for igual oq consta em produtos, devolver ao estoque
                    produto.setEstoque(produto.getEstoque() + compra.getQtd());
                }
            }
        }
        compras.clear();
    }
}

