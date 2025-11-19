package br.com.uniceplac.sneaklab.domain;

public class ItemPedido {
    private long id;
    private long idPedido;
    private long idProduto;
    private int quantidade;
    private double precounit;
    private double subtotal;

    //Construtores
    public ItemPedido() {
    }

    public ItemPedido(long id, long idPedido, long idProduto, int quantidade, double precounit, double subtotal) {
        this.id = id;
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.precounit = precounit;
        this.subtotal = subtotal;
    }

    //Getters e Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
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
