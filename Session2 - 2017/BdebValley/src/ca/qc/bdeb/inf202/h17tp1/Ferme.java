package ca.qc.bdeb.inf202.h17tp1;

/**
 * Ferme � cultiver. Vous ne pouvez changer la signature des m�thodes publiques
 * mais vous pouvez ajouter des m�thodes au besoin.
 */
public class Ferme {

    /**
     * Nombre maximal de semences qu'on peut acheter par jour.
     */
    private final int SEMENCES_MAX_PAR_JOUR = 6;
//Le terain de la ferme
    public final Parcelle[][] terrain = new Parcelle[5][5];
//Les variables de la ferme    
    private int StockConcombre = 0, StockTomate = 0, StockPatate = 0, StockSalade = 0, StockBetterave = 0, StockTotal;
    private int nombreLegumeVendus = 0, nombreJour = 1, nbrActions = 2, semencesAchetees = 0;
    private double gainTotal = 0, Encaisse = 100.0;
//Les prix de chaques légumes
    private final double PRIX_TOMATE = 2.0, PRIX_PATATE = 2.5, PRIX_BETTERAVE = 2.0, PRIX_SALADE = 1.5, PRIX_CONCOMBRE = 1.0;

    public Ferme() {
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                terrain[i][j] = new Parcelle();
            }
        }
    }

    /**
     * Arrose toutes les parcelles de la ferme.
     */
    public void arroser() {
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                terrain[i][j].setHumidité(1);
            }
        }
    }

    /**
     * R�pand de l'engrais sur toutes les parcelles de la ferme.
     */
    public void fertiliser() {
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                terrain[i][j].setEngrais(1);
            }
        }
        this.Encaisse -= 2;
    }

    /**
     * R�colte les plants m�rs sur toutes les parcelles de la ferme et les vend
     * au march�. Le profit est automatiquement ajout� � l'encaisse de la ferme.
     * <p>
     * return Le profit de la vente.
     */
    public double recolter() {
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                if (terrain[i][j].getLégumes().getPlant_état().equals(terrain[i][j].getLégumes().getPlant_Mature())) {
                    nombreLegumeVendus++;
                    gainTotal += terrain[i][j].getLégumes().getPrix_Vente();
                    terrain[i][j] = new Parcelle();
                }
            }
        }
        return gainTotal;
    }

    /**
     * Plante une certaine semence dans les parcelles non cultiv�es de la ferme.
     *
     * @param semence Le nom de la semence ?lanter.
     * @param nombreAPlanter Le nombre maximal de parcelles ?nsemencer.
     */
    public void planter(Semences semence, int nombreAPlanter) {
        int counter = 0, nbrPlanter = 0;
        switch (semence) {
            case TOMATE:
                nbrPlanter = nombreAPlanter;
                for (int i = 0; i < terrain.length; i++) {
                    for (int j = 0; j < terrain[i].length && counter < nombreAPlanter; j++) {
                        if (" ".equals(terrain[i][j].getLégumes().getPlant_état())) {
                            Tomate tomate = new Tomate();
                            terrain[i][j].setLégumes(tomate);
                            nombreAPlanter--;
                        }
                    }
                }
                nbrPlanter -= nombreAPlanter;
                StockTomate -= nbrPlanter;
                getPhoto();
                break;
            case CONCOMBRE:
                nbrPlanter = nombreAPlanter;
                for (int i = 0; i < terrain.length; i++) {
                    for (int j = 0; j < terrain[i].length && counter < nombreAPlanter; j++) {
                        if (" ".equals(terrain[i][j].getLégumes().getPlant_état())) {
                            Concombre concombre = new Concombre();
                            terrain[i][j].setLégumes(concombre);
                            setStockConcombre(-1);
                            nombreAPlanter--;
                        }
                    }
                }
                nbrPlanter -= nombreAPlanter;
                StockConcombre -= nbrPlanter;
                getPhoto();
                break;
            case PATATE:
                nbrPlanter = nombreAPlanter;
                for (int i = 0; i < terrain.length; i++) {
                    for (int j = 0; j < terrain[i].length && counter < nombreAPlanter; j++) {
                        if (" ".equals(terrain[i][j].getLégumes().getPlant_état())) {
                            Patate patate = new Patate();
                            terrain[i][j].setLégumes(patate);
                            nombreAPlanter--;
                        }
                    }
                }
                nbrPlanter -= nombreAPlanter;
                StockPatate -= nbrPlanter;
                getPhoto();
                break;
            case SALADE:
                nbrPlanter = nombreAPlanter;
                for (int i = 0; i < terrain.length; i++) {
                    for (int j = 0; j < terrain[i].length && counter < nombreAPlanter; j++) {
                        if (" ".equals(terrain[i][j].getLégumes().getPlant_état())) {
                            Salade salade = new Salade();
                            terrain[i][j].setLégumes(salade);
                            nombreAPlanter--;
                        }
                    }
                }
                nbrPlanter -= nombreAPlanter;
                StockSalade -= nbrPlanter;
                getPhoto();
                break;
            case BETTERAVE:
                nbrPlanter = nombreAPlanter;
                for (int i = 0; i < terrain.length; i++) {
                    for (int j = 0; j < terrain[i].length && counter < nombreAPlanter; j++) {
                        if (" ".equals(terrain[i][j].getLégumes().getPlant_état())) {
                            Betterave betterave = new Betterave();
                            terrain[i][j].setLégumes(betterave);
                            nombreAPlanter--;
                        }
                    }
                }
                nbrPlanter -= nombreAPlanter;
                StockBetterave -= nbrPlanter;
                getPhoto();
                break;
        }
    }

    /**
     * Peut effectuer au moins une action quotidienne.
     *
     * @return true s'il reste au moins une action.
     */
    public boolean peutEffectuerActionAujourdhui() {
        if (nbrActions > 2 || nbrActions <= 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Peut semer aujourd'hui.
     *
     * @return true si le fermier peut semer.
     */
    public boolean peutPlanterAujourdhui() {
        if (getStockTotal() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Peut acheter des semences aujourd'hui.
     *
     * @return true si le fermier peut acheter.
     */
    public boolean peutAcheterDesSemencesAujourdhui() {
        if (semencesAchetees != SEMENCES_MAX_PAR_JOUR) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Passe � la journ�e suivante.
     */
    public void prochaineJournee() {
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                if (" ".equals(terrain[i][j].getLégumes().getPlant_état())) {

                } else {
                    terrain[i][j].getLégumes().setNbr_Jour_Croissance(terrain[i][j].getLégumes().getNbr_Jour_Croissance() + 1);
                    if (terrain[i][j].getEngrais() > 0) {
                        terrain[i][j].getLégumes().setNbr_Jour_Croissance(terrain[i][j].getLégumes().getNbr_Jour_Croissance() + 0.5);
                        terrain[i][j].setEngrais(-1);
                    }
                    if (terrain[i][j].getHumidité() > 0) {
                        terrain[i][j].getLégumes().setNbr_Jour_Croissance(terrain[i][j].getLégumes().getNbr_Jour_Croissance() + 0.5);
                        terrain[i][j].setHumidité(-1);
                    }
                    adjacents(i, j);
                    //S'assure que le plant ne passera pas de non mature à pourri en une journée
                    if (terrain[i][j].getLégumes().getNbr_Jour_Croissance() > terrain[i][j].getLégumes().getNbr_Jour_Mature() && terrain[i][j].getLégumes().getPlant_état().equals(terrain[i][j].getLégumes().getPlan_Non_Mature())) {
                        terrain[i][j].getLégumes().setPlant_état(terrain[i][j].getLégumes().getPlant_Mature());
                        terrain[i][j].getLégumes().setNbr_Jour_Croissance(terrain[i][j].getLégumes().getNbr_Jour_Mature());
                    }
                    //Si le nombre de jour du plant dépasse la maturité, le plant devient pourri
                    if (terrain[i][j].getLégumes().getNbr_Jour_Croissance() > terrain[i][j].getLégumes().getNbr_Jour_Mature()) {
                        terrain[i][j].getLégumes().setPlant_état(terrain[i][j].getLégumes().getPlant_Pourri());
                    }
                    //Si le nombre de jour du plant est égal au nombre de jour de maturtié, le plant devient mature
                    if (terrain[i][j].getLégumes().getNbr_Jour_Croissance() == terrain[i][j].getLégumes().getNbr_Jour_Mature()) {
                        terrain[i][j].getLégumes().setPlant_état(terrain[i][j].getLégumes().getPlant_Mature());
                    }
                    //Si le nombre de jour du plant est supérieur au nombre de jour de maturité plus deux, 
                    //cela veut dire que la journée où le plant pourri se décompose à passée, donc le plant
                    //est retiré
                    if (terrain[i][j].getLégumes().getNbr_Jour_Croissance() > terrain[i][j].getLégumes().getNbr_Jour_Mature() + 2) {
                        terrain[i][j] = new Parcelle();
                        terrain[i][j].setEngrais(1);
                        terrain[i][j].setHumidité(1);
                    }
                }
            }
        }
        setSemencesAchetees(0);
        setNbrActions(2);
        setNombreJour(1);
    }
    
    /**
     * Ajoute les bonus de journée pour la présence de plants similiaire
     * adjacents
     */
    public void adjacents(int i, int j) {
        try {
            if (terrain[i][j].getLégumes().getPlant_état().equals(terrain[i][j - 1].getLégumes().getPlan_Non_Mature())) {
                terrain[i][j].getLégumes().setNbr_Jour_Croissance(terrain[i][j].getLégumes().getNbr_Jour_Croissance() + 0.5);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //Ne rien faire
        }
        try {
            if (terrain[i][j].getLégumes().getPlant_état().equals(terrain[i][j + 1].getLégumes().getPlan_Non_Mature())) {
                terrain[i][j].getLégumes().setNbr_Jour_Croissance(terrain[i][j].getLégumes().getNbr_Jour_Croissance() + 0.5);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //Ne rien faire
        }
        try {
            if (terrain[i][j].getLégumes().getPlant_état().equals(terrain[i + 1][j].getLégumes().getPlan_Non_Mature())) {
                terrain[i][j].getLégumes().setNbr_Jour_Croissance(terrain[i][j].getLégumes().getNbr_Jour_Croissance() + 0.5);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //Ne rien faire
        }
        try {
            if (terrain[i][j].getLégumes().getPlant_état().equals(terrain[i - 1][j + 1].getLégumes().getPlan_Non_Mature())) {
                terrain[i][j].getLégumes().setNbr_Jour_Croissance(terrain[i][j].getLégumes().getNbr_Jour_Croissance() + 0.5);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //Ne rien faire
        }
    }

    /**
     * Donne l'humidit?oyenne des parcelles du champ.
     *
     * @return La moyenne.
     */
    public double getHumiditeMoyenne() {
        int humidite = 0;
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                humidite += terrain[i][j].getHumidité();
            }
        }
        return humidite / 25;
    }

    /**
     * Donne la fertilit� moyenne des parcelles du champ.
     *
     * @return La moyenne.
     */
    public double getFertiliteMoyenne() {
        double fertilite = 0.0;
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                fertilite += terrain[i][j].getEngrais();
            }
        }
        return fertilite / 25;
    }

    /**
     * Donne une photo a�rienne du champ faisant �tat de la maturit� des
     * cultures.
     *
     * @return La matrice des parcelles cultiv�es.
     */
    public String[][] getPhoto() {
        String[][] temp = new String[5][5];
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                temp[i][j] = terrain[i][j].getLégumes().getPlant_état();
            }
        }
        System.out.println("    1 2 3 4 5");
        System.out.println("   -----------");
        for (int i = 0; i < temp.length; i++) {
            System.out.print(i + 1 + "- |");
            for (int j = 0; j < temp[i].length; j++) {
                System.out.print(temp[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println("   -----------");
        return temp;
    }

    /**
     * Acheter des semences au march� (maximum 6).
     *
     * @param semence Le type de semences � acheter.
     * @param quantite Le nombre de semences, maximum 6.
     *
     * @return Le prix des semences achet�es.
     */
    public double acheterSemences(Semences semence, int quantite) {
        double montantAchat = 0;
        switch (semence) {
            case BETTERAVE:
                montantAchat = quantite * PRIX_BETTERAVE;
                StockBetterave += quantite;
                this.Encaisse -= montantAchat;
                break;
            case TOMATE:
                montantAchat = quantite * PRIX_TOMATE;
                StockTomate += quantite;
                this.Encaisse -= montantAchat;
                break;
            case SALADE:
                montantAchat = quantite * PRIX_SALADE;
                StockSalade += quantite;
                this.Encaisse -= montantAchat;
                break;
            case PATATE:
                montantAchat = quantite * PRIX_PATATE;
                StockPatate += quantite;
                this.Encaisse -= montantAchat;
                break;
            case CONCOMBRE:
                montantAchat = quantite * PRIX_CONCOMBRE;
                StockConcombre += quantite;
                this.Encaisse -= montantAchat;
                break;
        }
        return montantAchat;
    }

//Setters de la classe
    public int getSemencesAchetees() {
        return semencesAchetees;
    }

    public void setSemencesAchetees(int semencesAchetees) {
        this.semencesAchetees = semencesAchetees;
    }

    public void setStockConcombre(int StockConcombre) {
        this.StockConcombre += StockConcombre;
    }

    public void setStockTomate(int StockTomate) {
        this.StockTomate += StockTomate;
    }

    public void setStockPatate(int StockPatate) {
        this.StockPatate += StockPatate;
    }

    public void setStockSalade(int StockSalade) {
        this.StockSalade += StockSalade;
    }

    public void setStockTotal(int StockTotal) {
        this.StockTotal += StockTotal;
    }

    public void setNombreLegumeVendus(int nombreLegume) {
        this.nombreLegumeVendus = nombreLegume;
    }

    public void setNombreJour(int nombreJour) {
        this.nombreJour += nombreJour;
    }

    public void setGainTotal(double gainTotal) {
        this.gainTotal = gainTotal;
    }

    public void setNbrActions(int nbrActions) {
        this.nbrActions = nbrActions;
    }

    public void setEncaisse(double Encaisse) {
        this.Encaisse = Encaisse;
    }

//Getters de la classe
    public int getStockSemencesTomate() {
        return StockTomate;
    }

    public int getStockSemencesConcombre() {
        return StockConcombre;
    }

    public int getStockSemencesSalade() {
        return StockSalade;
    }

    public int getStockSemencesPatate() {
        return StockPatate;
    }

    public int getStockSemencesBetterave() {
        return StockBetterave;
    }

    public int getStockTotal() {
        return (StockBetterave + StockConcombre + StockPatate + StockSalade + StockTomate);
    }

    public double getEncaisse() {
        return this.Encaisse;
    }

    public int getNombreLegumeVendus() {
        return nombreLegumeVendus;
    }

    public int getNombreJour() {
        return nombreJour;
    }

    public double getGainTotal() {
        return gainTotal;
    }

    public double getGainMoyen() {
        return (gainTotal / nombreJour);
    }

    public int getSEMENCES_MAX_PAR_JOUR() {
        return SEMENCES_MAX_PAR_JOUR;
    }

    public int getActionsRestantesAujourdhui() {
        return nbrActions;
    }
}
