package ca.qc.bdeb.info3.tp2.vue;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Classe du laser.
 */
public class Laser extends Projectile {

    /**
     * Constructeur de la classe.
     *
     * @param direction Direction du laser
     */
    public Laser(Direction direction) {
        super(direction, 7);
        if (direction == Direction.SUD || direction == Direction.NORD) {
            setSize(3, 20);
        } else {
            setSize(20, 3);
        }
    }

    /**
     * Dessine la composante graphique.
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

}
