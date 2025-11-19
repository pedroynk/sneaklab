package br.com.uniceplac.sneaklab.adapters.dtos;

import br.com.uniceplac.sneaklab.domain.Produto;

public class ProdutoDto {

    private long id;
    private String nome;
    private String sku;
    private Double preco;
    private int estoque;

    public ProdutoDto() {
    }

    public ProdutoDto(long id, String nome, String sku, Double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.sku = sku;
        this.preco = preco;
        this.estoque = estoque;
    }

    public static ProdutoDto fromDomain(Produto produto) {
        return new ProdutoDto(
                produto.getId(),
                produto.getNome(),
                produto.getSku(),
                produto.getPreco(),
                produto.getEstoque()
        );
    }

    public Produto toDomain() {
        Produto produto = new Produto();
        produto.setId(this.id);
        produto.setNome(this.nome);
        produto.setSku(this.sku);
        produto.setPreco(this.preco);
        produto.setEstoque(this.estoque);
        return produto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
