package sample;

/**
 * Created by Mariana on 28/07/17.
 */
public class Compra{
    private String prod, cod;
    private double preco;
    private int qtd = 0;

    public Compra(String prod, String cod, double preco, int qtd) {
        this.prod = prod;
        this.cod = cod;
        this.preco = preco;
        this.qtd = qtd;
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }
}
