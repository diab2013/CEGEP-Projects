package ca.qc.bdeb.info3.tp2.vue;

import javax.swing.JComponent;

/**
 * Classe abstraite des personnages.
 */
public abstract class Personnage extends JComponent implements Bougeable {

    protected int vitesse;
    protected Direction direction;

    /**
     * Constructeur de la classe.
     *
     * @param vitesse Vitesse du personnage
     */
    public Personnage(int vitesse) {
        this.vitesse = vitesse;
    }

    /**
     * Change l'image du personnage en fonction de la direction vers laquelle il
     * est orienté.
     *
     * @param direction La direction du personnage
     */
    public abstract void tourner(Direction direction);

    public Direction getDirection() {
        return direction;
    }

    public int getVitesse() {
        return vitesse;
    }
}
