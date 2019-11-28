package ca.qc.bdeb.info3.tp2.vue;

import ca.qc.bdeb.info3.tp2.modele.Modele;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * Classe des potions.
 */
public class Potion extends Bonus {

    private final Image img = Toolkit.getDefaultToolkit().getImage("bin\\images\\boni6.gif");

    /**
     * Constructeur de la classe.
     */
    public Potion() {
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
     * Exécute l'action du bonus. En particulier, l'héro récupère toutes ses
     * vies perdues.
     *
     * @param monde Le monde du jeu
     * @param modele Le Modèle du jeu
     */
    @Override
    public void executerAction(Monde monde, Modele modele) {
        while (modele.getPointsVieHero() != 3) {
            modele.heroGagneUneVie();
        }
    }

}
