package br.com.uniceplac.sneaklab.domain;

public class Cliente {

    private int idCliente;
    private String endereco;
    private String telefone;
    private String cpf;
    private int idUser;

    //Construtores
    public Cliente() {
    }

    public Cliente(int idCliente, String endereco, String telefone, String cpf, int idUser) {
        this.idCliente = idCliente;
        this.endereco = endereco;
        this.telefone = telefone;
        this.cpf = cpf;
        this.idUser = idUser;
    }

    //Getters e Setters
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
