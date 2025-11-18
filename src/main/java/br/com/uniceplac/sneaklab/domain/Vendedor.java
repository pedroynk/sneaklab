package br.com.uniceplac.sneaklab.domain;

public class Vendedor {
    private int idVendedor;
    private String loja;
    private String cnpj;
    private double reputacao;
    private int idUser;

    //Construtores
    public Vendedor() {
    }

    public Vendedor(int idVendedor, String loja, String cnpj, double reputacao, int idUser) {
        this.idVendedor = idVendedor;
        this.loja = loja;
        this.cnpj = cnpj;
        this.reputacao = reputacao;
        this.idUser = idUser;
    }

    //Getters e Setters
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
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
}
