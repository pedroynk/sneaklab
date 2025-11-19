package br.com.uniceplac.sneaklab.adapters.persistence.jpa;

import br.com.uniceplac.sneaklab.domain.StatusPagamento;
import br.com.uniceplac.sneaklab.domain.TipoPagamento;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "pagamentos")
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pedido", nullable = false)
    private int idPedido;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date data;

    @Column(nullable = false)
    private double valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPagamento tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status;

    //Construtores
    public PagamentoEntity() {
    }

    public PagamentoEntity(Long id, int idPedido, Date data, double valor,
                           TipoPagamento tipo, StatusPagamento status) {
        this.id = id;
        this.idPedido = idPedido;
        this.data = data;
        this.valor = valor;
        this.tipo = tipo;
        this.status = status;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
