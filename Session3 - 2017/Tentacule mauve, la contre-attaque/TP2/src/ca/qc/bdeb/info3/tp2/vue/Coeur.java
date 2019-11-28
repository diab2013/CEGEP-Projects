package ca.qc.bdeb.info3.tp2.vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;

/**
 * Classe du coeur.
 */
public class Coeur extends JComponent {

    private Image img = Toolkit.getDefaultToolkit().getImage("bin\\images\\coeurplein.gif");

    /**
     * Constructeur de la classe.
     */
    public Coeur() {
        setSize(20, 18);
    }

    /**
     * Change l'image du coeur pour représenter une vie perdue.
     */
    public void perdreVie() {
        this.img = Toolkit.getDefaultToolkit().getImage("bin\\images\\coeurvide.gif");
        revalidate();
        repaint();
    }

    /**
     * Change l'image du coeur pour représenter une vie remplie.
     */
    public void remplirVie() {
        this.img = Toolkit.getDefaultToolkit().getImage("bin\\images\\coeurplein.gif");
        revalidate();
        repaint();
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
