package ca.qc.bdeb.info202.Elements;

import ca.qc.bdeb.info202.Gestion.Race;
import java.io.Serializable;

public abstract class Element implements Serializable {

    private static int ID = 0;
    private int numeroSerie; 
    private String name;
    private Race race;
    private int prixProdMIN;
    private int prixProdGAS;
    private int hp;
    private int def;

    public Element() {
        numeroSerie = ++ID;
    }

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        Element.ID = ID;
    }

    public int getPrixProdGAS() {
        return prixProdGAS;
    }

    public void setPrixProdGAS(int prixProdGAS) {
        this.prixProdGAS = prixProdGAS;
    }
    
    public int getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(int numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public int getPrixProdMIN() {
        return prixProdMIN;
    }

    public void setPrixProdMIN(int prixProdMin) {
        this.prixProdMIN = prixProdMin;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    @Override
    public String toString() {
        return "ID:\t" + getNumeroSerie()+ "\n"
                + "Name:\t" + getName() + "\n"
                + "Race:\t" + getRace() + "\n"
                + "HP:\t" + getHp() + "\n"
                + "DEF: \t" + getDef() + "\n"
                + "Coût:\t" + getPrixProdMIN() + " MIN";
    }



    
}
