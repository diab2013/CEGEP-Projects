package ca.qc.bdeb.info3.tp2.vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * La classe du personnage principal du jeu.
 */
public class Hero extends Personnage {

    private Image img = Toolkit.getDefaultToolkit().getImage("bin\\images\\herofront.gif");

    public enum ArmeEquipee {
        LASER, SHOTGUN;
    }
    private ArmeEquipee armeEquipee;

    /**
     * Constructeur de la classe.
     */
    public Hero() {
        super(3);
        setSize(22, 50);
        direction = Direction.SUD;
        armeEquipee = ArmeEquipee.LASER;
    }

    /**
     * Change l'image de l'héros en fonction de la direction vers laquelle il
     * est orienté.
     *
     * @param direction La direction du héros
     */
    @Override
    public void tourner(Direction direction) {
        switch (direction) {
            case NORD:
                this.direction = Direction.NORD;
                img = Toolkit.getDefaultToolkit().getImage("bin\\images\\heroback.gif");
                break;
            case SUD:
                this.direction = Direction.SUD;
                img = Toolkit.getDefaultToolkit().getImage("bin\\images\\herofront.gif");
                break;
            case OUEST:
                this.direction = Direction.OUEST;
                img = Toolkit.getDefaultToolkit().getImage("bin\\images\\herogauche.gif");
                break;
            case EST:
                this.direction = Direction.EST;
                img = Toolkit.getDefaultToolkit().getImage("bin\\images\\herodroite.gif");
                break;
        }
    }

    /**
     * Déplace l'héros vers une direction donnée.
     *
     * @param direction La direction du héros
     */
    public void bouger(Direction direction) {
        switch (direction) {
            case NORD:
                setLocation(getX(), getY() - vitesse);
                break;
            case SUD:
                setLocation(getX(), getY() + vitesse);
                break;
            case OUEST:
                setLocation(getX() - vitesse, getY());
                break;
            case EST:
                setLocation(getX() + vitesse, getY());
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

    public ArmeEquipee getArmeEquipee() {
        return armeEquipee;
    }

    public void setArmeEquipee(ArmeEquipee armeEquipee) {
        this.armeEquipee = armeEquipee;
    }

}
