package ca.qc.bdeb.info3.tp2.vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * Classe du buisson.
 */
public class Buisson extends Obstacle {

    private Image img = Toolkit.getDefaultToolkit().getImage("bin\\images\\buisson.gif");

    /**
     * Constructeur de la classe.
     */
    public Buisson() {
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

}
