package model;

public class Medicament {

    private int  id;
    private String Nom;
    private String Dosage;
    private String Forme;
    private String DCI;
    private String Classe;
    private String AMM;
    private int Duree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDosage() {
        return Dosage;
    }

    public void setDosage(String dosage) {
        Dosage = dosage;
    }

    public String getForme() {
        return Forme;
    }

    public void setForme(String forme) {
        Forme = forme;
    }

    public String getDCI() {
        return DCI;
    }

    public void setDCI(String DCI) {
        this.DCI = DCI;
    }

    public String getClasse() {
        return Classe;
    }

    public void setClasse(String classe) {
        Classe = classe;
    }

    public String getAMM() {
        return AMM;
    }

    public void setAMM(String AMM) {
        this.AMM = AMM;
    }


}
