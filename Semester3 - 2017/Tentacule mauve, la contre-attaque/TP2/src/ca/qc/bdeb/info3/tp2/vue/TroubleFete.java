package ca.qc.bdeb.info3.tp2.vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * Classe du trouble fête.
 */
public class TroubleFete extends Ennemi {

    private Image img = Toolkit.getDefaultToolkit().getImage("bin\\images\\purplefront.gif");

    /**
     * Constructeur de la classe.
     */
    public TroubleFete() {
        super(2, 1, 3);
        setSize(35, 42);
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
     * Change l'image de l'ennemi en fonction de la direction vers laquelle il
     * est orienté.
     *
     * @param direction La direction de l'ennemi
     */
    @Override
    public void tourner(Direction direction) {
        switch (direction) {
            case NORD:
                img = Toolkit.getDefaultToolkit().getImage("bin\\images\\purpleback.gif");
                break;
            default:
                img = Toolkit.getDefaultToolkit().getImage("bin\\images\\purplefront.gif");
                break;
        }
    }

}
