package ca.qc.bdeb.info3.tp2.vue;

/**
 * Classe abstraite des ennemis.
 */
public abstract class Ennemi extends Personnage {

    private int valeurScore;
    private int pointsVie;

    /**
     * Constructeur de la classe.
     *
     * @param pointsVie Nombre de points de vie de l'ennemi
     * @param vitesse Vitesse de déplacement de l'ennemi
     * @param valeurScore Nombre de point que gagne le héro à la mort de
     * l'ennemi
     */
    public Ennemi(int pointsVie, int vitesse, int valeurScore) {
        super(vitesse);
        this.pointsVie = pointsVie;
        this.valeurScore = valeurScore;
        direction = Direction.SUD;
    }

    /**
     * Déplace l'ennemi vers la position actuelle du héro.
     *
     * @param positionXHero Position en X du héro
     * @param positionYHero Position en Y du héro
     */
    protected void bouger(int positionXHero, int positionYHero) {
        if (getX() < positionXHero) {
            direction = Direction.EST;
            tourner(direction);
            setLocation(getX() + vitesse, getY());
        }
        if (getX() > positionXHero) {
            direction = Direction.OUEST;
            tourner(direction);
            setLocation(getX() - vitesse, getY());
        }
        if (getY() < positionYHero) {
            direction = Direction.SUD;
            tourner(direction);
            setLocation(getX(), getY() + vitesse);
        }
        if (getY() > positionYHero) {
            direction = Direction.NORD;
            tourner(direction);
            setLocation(getX(), getY() - vitesse);
        }
    }

    public void perdreUneVie() {
        pointsVie--;
    }

    public int getValeurScore() {
        return valeurScore;
    }

    public int getPointsVie() {
        return pointsVie;
    }

}
