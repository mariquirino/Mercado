package sample;

/**
 * Created by jornadaciti on 28/07/17.
 */
public class ProdutoException extends Exception {

    public ProdutoException(int e) {
        super("Quantidade maior que em estoque. \n");
    }
}
