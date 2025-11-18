package br.com.uniceplac.sneaklab.domain;

import java.util.Date;

public class Pedido {
    private int id;
    private int idCliente;
    private Date data;
    private double total;
    private StatusPedido status;

    //Construtores
    public Pedido() {
    }

    public Pedido(int id, int idCliente, Date data, double total, StatusPedido status) {
        this.id = id;
        this.idCliente = idCliente;
        this.data = data;
        this.total = total;
        this.status = status;
    }

    //Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }
}
