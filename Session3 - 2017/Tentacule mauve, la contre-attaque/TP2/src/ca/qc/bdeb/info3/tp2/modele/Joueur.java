package ca.qc.bdeb.info3.tp2.modele;

import java.io.Serializable;

/**
 * Classe du joueur dont on enregistre le nom et le score pour le tableau des
 * meilleurs scores.
 */
public class Joueur implements Serializable, Comparable {

    private String nom;
    private int points = 0;

    /**
     * Constructeur de la classe
     *
     * @param nom Nom du joueur
     */
    public Joueur(String nom) {
        this.nom = nom.toUpperCase();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom.toUpperCase();
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Affiche les caractéristiques du joueur de manière ordonnée.
     *
     * @return Les caractéristiques du joueur
     */
    @Override
    public String toString() {
        String info;
        return info = ("Nom du joueur : " +nom +"\n"
                +"Nombre de points : " +points);
    }

    /**
     * Compare le score du joueur actuel et d'un autre joueur. Sert à utiliser
     * Collections.sort(listeJoueurs) en fonction du score des joueurs dans
     * Modèle.
     *
     * @param autreJoueur L'autre joueur dont on cherche à comparer le score
     * @return La différence entre les points de l'autre joueur et du joueur
     * actuel
     */
    @Override
    public int compareTo(Object autreJoueur) {
        int pointsAutreJoueur = ((Joueur) autreJoueur).getPoints();
        return pointsAutreJoueur - this.points;
    }

}
