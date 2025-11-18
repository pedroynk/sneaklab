package br.com.uniceplac.sneaklab.domain;

public class ItemPedido {
    private int id;
    private int idPedido;
    private int idProduto;
    private int quantidade;
    private double precounit;
    private double subtotal;

    //Construtores
    public ItemPedido() {
    }

    public ItemPedido(int id, int idPedido, int idProduto, int quantidade, double precounit, double subtotal) {
        this.id = id;
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.precounit = precounit;
        this.subtotal = subtotal;
    }

    //Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecounit() {
        return precounit;
    }

    public void setPrecounit(double precounit) {
        this.precounit = precounit;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
