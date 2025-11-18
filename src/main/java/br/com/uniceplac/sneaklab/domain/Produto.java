package br.com.uniceplac.sneaklab.domain;

public class Produto {
    private int id;
    private String nome;
    private String sku;
    private Double preco;
    private int estoque;

    //Construtores
    public Produto() {
    }

    public Produto(int id, String nome, String sku, Double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.sku = sku;
        this.preco = preco;
        this.estoque = estoque;
    }

    //Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
