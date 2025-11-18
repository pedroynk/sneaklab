package br.com.uniceplac.sneaklab.domain;

import java.util.Date;

public class Pagamento {
    private int id;
    private int idPedido;
    private Date data;
    private double valor;
    private TipoPagamento tipo;
    private StatusPagamento status;

    //Construtores
    public Pagamento() {
    }

    public Pagamento(int id, int idPedido, Date data, double valor, TipoPagamento tipo, StatusPagamento status) {
        this.id = id;
        this.idPedido = idPedido;
        this.data = data;
        this.valor = valor;
        this.tipo = tipo;
        this.status = status;
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

    public TipoPagamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamento tipo) {
        this.tipo = tipo;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }
}
