package ca.qc.bdeb.info3.tp2.vue;

import ca.qc.bdeb.info3.tp2.modele.Modele;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * Classe du bonus qui donne un shotgun.
 */
public class Shotgun extends Bonus {

    private final Image img = Toolkit.getDefaultToolkit().getImage("bin\\images\\boni2.gif");

    /**
     * Constructeur de la classe.
     */
    public Shotgun() {
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
     * Exécute l'action du bonus. En particulier, l'héros obtient un shotgun.
     *
     * @param monde Le monde du jeu
     * @param modele Le modèle du jeu
     */
    @Override
    public void executerAction(Monde monde, Modele modele) {
        monde.getHero().setArmeEquipee(Hero.ArmeEquipee.SHOTGUN);
    }

}
