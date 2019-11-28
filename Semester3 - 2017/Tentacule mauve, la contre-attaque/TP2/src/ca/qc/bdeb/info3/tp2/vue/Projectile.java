package ca.qc.bdeb.info3.tp2.vue;

import javax.swing.JComponent;

/**
 * Classe abstraite du projectile.
 */
public abstract class Projectile extends JComponent implements Bougeable {

    Direction direction;
    private int vitesse;

    /**
     * Constructeur de la classe.
     *
     * @param direction Direction du projectile
     * @param vitesse Vitesse du projectile
     */
    public Projectile(Direction direction, int vitesse) {
        this.direction = direction;
        this.vitesse = vitesse;
    }

    /**
     * Déplace le projectile vers sa direction initiale.
     */
    public void voler() {
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
            case NORD_OUEST:
                setLocation(getX() - vitesse, getY() - vitesse);
                break;
            case NORD_EST:
                setLocation(getX() + vitesse, getY() - vitesse);
                break;
            case SUD_OUEST:
                setLocation(getX() - vitesse, getY() + vitesse);
                break;
            case SUD_EST:
                setLocation(getX() + vitesse, getY() + vitesse);
                break;
        }
    }
}
