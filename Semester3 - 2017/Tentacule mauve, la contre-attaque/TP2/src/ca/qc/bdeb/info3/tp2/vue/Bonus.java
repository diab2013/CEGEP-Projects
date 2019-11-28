package ca.qc.bdeb.info3.tp2.vue;

import ca.qc.bdeb.info3.tp2.modele.Modele;
import javax.swing.JComponent;

/**
 * Classe abstraite des bonus ramassables après la mort d'un ennemi.
 */
public abstract class Bonus extends JComponent {

    private final int VALEUR = 5;
    
    /**
     * Constructeur de la classe.
     *
     * @param largeur Largeur de la composante graphique
     * @param hauteur Hauteur de la composante graphique
     */
    public Bonus(int largeur, int hauteur) {
        setSize(largeur, hauteur);
    }

    /**
     * Effectue une action différente pour chaque bonus.
     *
     * @param monde Monde actuel
     * @param modele Modele actuel
     */
    protected abstract void executerAction(Monde monde, Modele modele);

    public int getValeur() {
        return VALEUR;
    }
    
    

}
