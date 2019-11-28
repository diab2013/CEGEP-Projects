package ca.qc.bdeb.info3.tp2.vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * La classe du méchant.
 */
public class Mechant extends Ennemi {

    protected Image img = Toolkit.getDefaultToolkit().getImage("bin\\images\\greenfront.gif");

    /**
     * Le constructeur de la classe.
     */
    public Mechant() {
        super(1, 1, 1);
        setSize(35, 42);
    }

    /**
     * Change l'image de l'ennemi en fonction de la direction vers laquelle il
     * est orienté.
     *
     * @param direction La direction de l'ennemi
     */
    @Override
    public void tourner(Direction direction) {
        switch (direction) {
            case NORD:
                img = Toolkit.getDefaultToolkit().getImage("bin\\images\\greenback.gif");
                break;
            default:
                img = Toolkit.getDefaultToolkit().getImage("bin\\images\\greenfront.gif");
                break;
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
        g.drawImage(img, 0, 0, this);
    }

}
