package br.com.uniceplac.sneaklab.domain;

public class Admin {

    private int idAdmin;
    private int nivel;
    private String setor;
    private int idUser;

    //Construtores
    public Admin() {
    }

    public Admin(int idAdmin, int nivel, String setor, int idUser) {
        this.idAdmin = idAdmin;
        this.nivel = nivel;
        this.setor = setor;
        this.idUser = idUser;
    }

    //Getters e Setters
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
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
}
