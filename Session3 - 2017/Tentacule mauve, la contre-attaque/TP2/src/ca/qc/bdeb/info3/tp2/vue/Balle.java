package ca.qc.bdeb.info3.tp2.vue;

import java.awt.Graphics;

/**
 * Classe de la balle du shotgun.
 */
public class Balle extends Projectile {

    /**
     * Constructeur de la classe.
     *
     * @param direction Enum de la direction vers laquelle le personne fait face
     */
    public Balle(Direction direction) {
        super(direction, 7);
        setSize(10, 10);
    }

    /**
     * Dessine la composante graphique.
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillOval(0, 0, getWidth(), getHeight());
    }

}
