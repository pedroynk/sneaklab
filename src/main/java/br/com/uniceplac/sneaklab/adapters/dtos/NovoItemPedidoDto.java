package br.com.uniceplac.sneaklab.adapters.dtos;

public class NovoItemPedidoDto {

    private long idProduto;
    private int quantidade;

    public NovoItemPedidoDto() {
    }

    public NovoItemPedidoDto(long idProduto, int quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
