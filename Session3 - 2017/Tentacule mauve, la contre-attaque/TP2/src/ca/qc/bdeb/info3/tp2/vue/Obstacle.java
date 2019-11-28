package ca.qc.bdeb.info3.tp2.vue;

import javax.swing.JComponent;

/**
 * Claase abstraite des obstacles.
 */
public abstract class Obstacle extends JComponent {

    /**
     * Constructeur de la classe.
     *
     * @param largeur Largeur de la composante graphique
     * @param hauteur Hauteur de la composante graphique
     */
    public Obstacle(int largeur, int hauteur) {
        setSize(largeur, hauteur);
    }

}
