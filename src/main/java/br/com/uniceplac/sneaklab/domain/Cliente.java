package br.com.uniceplac.sneaklab.domain;

public class Cliente {

    private long id;
    private String endereco;
    private String telefone;
    private String cpf;
    private long idUser;

    //Construtores
    public Cliente() {
    }

    public Cliente(long idCliente, String endereco, String telefone, String cpf, long idUser) {
        this.id = idCliente;
        this.endereco = endereco;
        this.telefone = telefone;
        this.cpf = cpf;
        this.idUser = idUser;
    }

    //Getters e Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
}
