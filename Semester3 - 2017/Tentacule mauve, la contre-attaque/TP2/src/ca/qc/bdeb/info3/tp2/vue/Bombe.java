package ca.qc.bdeb.info3.tp2.vue;

import ca.qc.bdeb.info3.tp2.modele.Modele;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * Classe de la bombe qui détruit tous les ennemis.
 */
public class Bombe extends Bonus {

    private final Image img = Toolkit.getDefaultToolkit().getImage("bin\\images\\boni5.gif");

    /**
     * Constructeur de la classe.
     */
    public Bombe() {
        super(32, 32);
    }

    /**
     * Dessine la composante graphique.
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }

    /**
     * Éxécute l'action du bonus. En particulier, tue tous les ennemis à
     * l'écran.
     *
     * @param monde Monde actuel
     * @param modele Modele actuel
     */
    @Override
    public void executerAction(Monde monde, Modele modele) {
        monde.setTuerTousLesEnnemis(true);
    }

}
