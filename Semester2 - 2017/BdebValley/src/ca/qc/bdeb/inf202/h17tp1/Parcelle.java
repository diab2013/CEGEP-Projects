package ca.qc.bdeb.inf202.h17tp1;

public class Parcelle {
//Variable pirvate pour l'encapsulation
    private int humidité = 0, engrais = 0;
    private Légumes légumes;

//Constructeur d'objet légumes
    public Parcelle() {
        légumes = new Légumes();
    }

//Setters de la classe
    public void setHumidité(int humidité) {
        this.humidité += humidité;
    }

    public void setEngrais(int engrais) {
        this.engrais += engrais;
    }

    public void setLégumes(Légumes légumes) {
        this.légumes = légumes;
    }

//Getters de la classe
    public Légumes getLégumes() {
        return légumes;
    }

    public int getHumidité() {
        return this.humidité;
    }

    public int getEngrais() {
        return this.engrais;
    }
}
