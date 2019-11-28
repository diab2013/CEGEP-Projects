package ca.qc.bdeb.inf202.h17tp1;

public class Légumes {

    protected double nbr_Jour_Croissance = 0.0, nbr_Jour_Mature, prix_Vente;
    protected String plant_Mature, plan_Non_Mature, plant_état = " ";
    protected final String PLANT_POURRI = "~", PLANT_VIDE = " ";

    public Légumes() {
        plant_état = PLANT_VIDE;
    }

//Setters de la classe
    public void setPrix_Vente(double prix_Vente) {
        this.prix_Vente = prix_Vente;
    }

    public void setNbr_Jour_Croissance(double nbr_Jour_Croissance) {
        this.nbr_Jour_Croissance = nbr_Jour_Croissance;
    }

    public void setNbr_Jour_Mature(double nbr_Jour_Mature) {
        this.nbr_Jour_Mature = nbr_Jour_Mature;
    }

    public void setPlant_Mature(String plant_Mature) {
        this.plant_Mature = plant_Mature;
    }

    public void setPlan_Non_Mature(String plan_Non_Mature) {
        this.plan_Non_Mature = plan_Non_Mature;
    }

    public void setPlant_état(String plant_état) {
        this.plant_état = plant_état;
    }

//Getters de la classe
    public String getPlant_Mature() {
        return plant_Mature;
    }
    
    public String getPlan_Non_Mature() {
        return plan_Non_Mature;
    }
    
    public String getPlant_état() {
        return plant_état;
    }
    
    public String getPlant_Pourri() {
        return PLANT_POURRI;
    }
    
    public String getPlant_vide() {
        return PLANT_VIDE;
    }
    
    public double getPrix_Vente() {
        return prix_Vente;
    }
    
    public double getNbr_Jour_Croissance() {
        return nbr_Jour_Croissance;
    }
    
    public double getNbr_Jour_Mature() {
        return nbr_Jour_Mature;
    }
}
