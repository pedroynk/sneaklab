package br.com.uniceplac.sneaklab.adapters.dtos;

import br.com.uniceplac.sneaklab.domain.Pedido;
import br.com.uniceplac.sneaklab.domain.StatusPedido;

import java.util.Date;

public class PedidoDto {

    private long id;
    private long idCliente;
    private Date data;
    private double total;
    private StatusPedido status;

    public PedidoDto() {
    }

    public PedidoDto(long id, long idCliente, Date data, double total, StatusPedido status) {
        this.id = id;
        this.idCliente = idCliente;
        this.data = data;
        this.total = total;
        this.status = status;
    }

    public static PedidoDto fromDomain(Pedido pedido) {
        return new PedidoDto(
                pedido.getId(),
                pedido.getIdCliente(),
                pedido.getData(),
                pedido.getTotal(),
                pedido.getStatus()
        );
    }

    public Pedido toDomain() {
        Pedido pedido = new Pedido();
        pedido.setId(this.id);
        pedido.setIdCliente(this.idCliente);
        pedido.setData(this.data);
        pedido.setTotal(this.total);
        pedido.setStatus(this.status);
        return pedido;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
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
