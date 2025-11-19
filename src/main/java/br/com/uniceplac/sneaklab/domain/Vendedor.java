package br.com.uniceplac.sneaklab.domain;

public class Vendedor {
    private long id;
    private String loja;
    private String cnpj;
    private double reputacao;
    private long idUser;

    //Construtores
    public Vendedor() {
    }

    public Vendedor(long idVendedor, String loja, String cnpj, double reputacao, long idUser) {
        this.id = idVendedor;
        this.loja = loja;
        this.cnpj = cnpj;
        this.reputacao = reputacao;
        this.idUser = idUser;
    }

    //Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public double getReputacao() {
        return reputacao;
    }

    public void setReputacao(double reputacao) {
        this.reputacao = reputacao;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
}
