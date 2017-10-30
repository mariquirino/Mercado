package sample;

/**
 * Created by Mariana on 28/07/17.
 */
public class Produto {
    private int estoque;
    private double preco;
    private String cod;
    private String nome;

    public Produto(String nome, int estoque, double preco, String cod) {
        this.nome = nome;
        this.estoque = estoque;
        this.preco = preco;
        this.cod = cod;
    }

    public void saida(int s) throws ProdutoException {
        if (estoque >= s){
            estoque -= s;
        }else{
            throw new ProdutoException(s);
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
}
