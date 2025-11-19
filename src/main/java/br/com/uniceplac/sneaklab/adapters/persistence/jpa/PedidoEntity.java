package br.com.uniceplac.sneaklab.adapters.persistence.jpa;

import br.com.uniceplac.sneaklab.domain.StatusPedido;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "pedidos")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente", nullable = false)
    private long idCliente;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    private double total;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    public PedidoEntity() {
    }

    public PedidoEntity(Long id, long idCliente, Date data, double total, StatusPedido status) {
        this.id = id;
        this.idCliente = idCliente;
        this.data = data;
        this.total = total;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
