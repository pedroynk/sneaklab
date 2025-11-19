package br.com.uniceplac.sneaklab.adapters.dtos;

import br.com.uniceplac.sneaklab.domain.Pagamento;
import br.com.uniceplac.sneaklab.domain.StatusPagamento;
import br.com.uniceplac.sneaklab.domain.TipoPagamento;

import java.util.Date;

public class PagamentoDto {

    private long id;
    private long idPedido;
    private Date data;
    private double valor;
    private TipoPagamento tipo;
    private StatusPagamento status;

    // Construtores
    public PagamentoDto() {
    }

    public PagamentoDto(long id, long idPedido, Date data, double valor,
                        TipoPagamento tipo, StatusPagamento status) {
        this.id = id;
        this.idPedido = idPedido;
        this.data = data;
        this.valor = valor;
        this.tipo = tipo;
        this.status = status;
    }

    public static PagamentoDto fromDomain(Pagamento pagamento) {
        return new PagamentoDto(
                pagamento.getId(),
                pagamento.getIdPedido(),
                pagamento.getData(),
                pagamento.getValor(),
                pagamento.getTipo(),
                pagamento.getStatus()
        );
    }

    // Getters e Setters
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public TipoPagamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamento tipo) {
        this.tipo = tipo;
    }
}
