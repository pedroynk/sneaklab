package br.com.uniceplac.sneaklab.domain;

public class Admin {

    private long id;
    private int nivel;
    private String setor;
    private int idUser;

    //Construtores
    public Admin() {
    }

    public Admin(long idAdmin, int nivel, String setor, int idUser) {
        this.id = idAdmin;
        this.nivel = nivel;
        this.setor = setor;
        this.idUser = idUser;
    }

    //Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
