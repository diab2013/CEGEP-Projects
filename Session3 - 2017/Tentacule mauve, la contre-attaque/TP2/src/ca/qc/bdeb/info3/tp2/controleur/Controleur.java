package ca.qc.bdeb.info3.tp2.controleur;

import ca.qc.bdeb.info3.tp2.modele.Modele;
import ca.qc.bdeb.info3.tp2.vue.Fenetre;

/**
 * Contrôleur du programme.
 */
public class Controleur {

    private Modele modele = new Modele();
    private Fenetre fenetre = new Fenetre(this, modele);

    /**
     * Transmet au Modele que l'héros du jeu perd une vie.
     *
     * @param nomJoueur Nom du joueur actuel (sert à la gestion de fin de
     * partie)
     */
    public void heroPerdUneVie(String nomJoueur) {
        modele.heroPerdUneVie(nomJoueur);
    }

    /**
     * Transmet au Modele d'ajouter un certain nombre de points au score.
     *
     * @param nombrePoints Nombre de points à ajouter (correspond à la valeur
     * d'un ennemi tué ou d'un bonus récupéré)
     */
    public void ajusterScore(int nombrePoints) {
        modele.ajusterScore(nombrePoints);
    }
}
