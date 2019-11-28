package ca.qc.bdeb.info3.tp2.modele;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

/**
 * Classe Modèle du programme. Sert à la logistique.
 */
public class Modele extends Observable {

    // Éléments essentiels au fonctionnement du jeu (évalués pour le TP)
    private int pointsVieHero;
    private int score;
    private int temps;
    private boolean partieTerminee = false;

    // Éléments optionels servant à la gestion du tableau des meilleurs scores
    private Joueur joueurDeLaPartieTerminee;
    private ArrayList<Joueur> listeJoueurs = new ArrayList<>();

    // Éléments optionels servant à la musique d'ambiance du jeu
    private File fichierMusical;
    private Clip clipMusical;
    private AudioInputStream streamMusique;

    // Timer optionel augmentant le temps de 1 à chaque 1000 ms
    private Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            temps++;
            miseAJourVue();

            // Si la musique ne joue pas déjà, il faut la partir
            if (!clipMusical.isRunning() || (clipMusical.getMicrosecondPosition() >= clipMusical.getMicrosecondLength())) {
                chargerMusiquePrincipale();
                clipMusical.start();
            }
        }
    });

    /**
     * Constructeur de la classe.
     */
    public Modele() {
        // Charger les fichiers avec la musique d'ambiance du jeu
        chargerMusiquePrincipale();

        // Charger le fichier avec les scores enregistrés jusqu'ici
        // Sert au tableau des meilleurs scores
        chargerListeJoueursPrecedents();
    }

    /**
     * Le héros perd une vie. Gère aussi la mort du héros.
     *
     * @param nomJoueurPrincipal Nom du joueur actuel
     */
    public void heroPerdUneVie(String nomJoueurPrincipal) {
        pointsVieHero -= 1;
        if (pointsVieHero < 1) {
            /* Si le héro meurt et que la partie se termine, on crée un nouvel
            joueur (joueurDeLaPartieTerminee) ayant le nom du joueur principal. 
            On enregistrera plus tard le nom et le score de ce nouveau joueur au scoreboard. 
            Ainsi, Fenetre travaille avec un seul joueur principal (ex: Bob) tant 
            que la fenêtre est ouverte, alors que le scoreboard enregistrera des 
            joueurs Bob différents à chaque partie qu'on termine (donc Bob peut avoir 
            la 1e place avec 25 points et la 2e place avec 13 points au scoreboard)*/
            this.joueurDeLaPartieTerminee = new Joueur(nomJoueurPrincipal);
            terminerPartie();
        }
        miseAJourVue();
    }

    /**
     * Le héros gagne une vie.
     */
    public void heroGagneUneVie() {
        pointsVieHero += 1;
        miseAJourVue();
    }

    /**
     * Gère la fin de partie : arrête le Timer, fait jouer la musique de mort et
     * sauvegarde le score actuel.
     */
    private void terminerPartie() {
        partieTerminee = true;
        jouerMusiqueMort();
        timer.stop();
        sauvegarderScoreDePartie();
        miseAJourVue();
    }

    /**
     * Associe le score actuel au joueur de la partie qui vient de finir.
     */
    private void sauvegarderScoreDePartie() {
        joueurDeLaPartieTerminee.setPoints(score);
        listeJoueurs.add(joueurDeLaPartieTerminee);
    }

    /**
     * Sert à charger et à faire joueur la musique de mort lorsque le personnage
     * meurt.
     */
    private void jouerMusiqueMort() {
        try {
            File fileMort = new File("bin\\sound\\death_sound.wav");
            AudioInputStream musicMort = AudioSystem.getAudioInputStream(fileMort);
            Clip clipMort = AudioSystem.getClip();
            clipMort.open(musicMort);
            clipMort.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Gère le début d'une nouvelle partie. Réinitialise les variables
     * nécéssaires et repart le timer.
     */
    public void nouvellePartie() {
        partieTerminee = false;
        pointsVieHero = 3;
        score = 0;
        temps = 0;
        timer.start();
        miseAJourVue();
    }

    /**
     * Ajoute un certain nombre de points au score.
     *
     * @param nombrePoints Nombre de points (valeur de l'ennemi tué ou du bonus
     * récupéré)
     */
    public void ajusterScore(int nombrePoints) {
        score += nombrePoints;
        miseAJourVue();
    }

    /**
     * Charge la chanson principale du jeu.
     */
    private void chargerMusiquePrincipale() {
        try {
            fichierMusical = new File("bin\\sound\\background_music.wav");
            streamMusique = AudioSystem.getAudioInputStream(fichierMusical);
            clipMusical = AudioSystem.getClip();
            clipMusical.open(streamMusique);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Enregistre la liste des joueurs (pour le scoreboard) lorsque la fenêtre
     * est fermée.
     */
    public void enregistrerScoreboard() {
        try {
            FileOutputStream fichierSauvegarde = new FileOutputStream("bin\\leaderboard.dat");
            ObjectOutputStream objetSauvegarde = new ObjectOutputStream(fichierSauvegarde);

            objetSauvegarde.writeObject(listeJoueurs);
            objetSauvegarde.flush();
            objetSauvegarde.close();
        } catch (FileNotFoundException error) {
        } catch (IOException error) {
        }
    }

    /**
     * Charger la liste des joueurs précédents (pour le scoreboard) lorsque le
     * jeu est lancé.
     */
    private void chargerListeJoueursPrecedents() {
        try {
            FileInputStream fichierSauvegarde = new FileInputStream("bin\\leaderboard.dat");
            ObjectInputStream objetSauvegarde = new ObjectInputStream(fichierSauvegarde);

            listeJoueurs = (ArrayList<Joueur>) objetSauvegarde.readObject();
            if (listeJoueurs == null) {
                listeJoueurs = new ArrayList<>();
            }
        } catch (ClassNotFoundException error) {
        } catch (IOException error) {
        }
    }

    /**
     * Met la partie en pause : arrête la musique et le timer.
     */
    public void pauseLaPartie() {
        clipMusical.stop();
        timer.stop();
    }

    /**
     * Reprend la partie après une pause : repart la musique et le timer.
     */
    public void continuerLaPartie() {
        clipMusical.start();
        timer.start();
    }

    public int getPointsVieHero() {
        return pointsVieHero;
    }

    public int getScore() {
        return score;
    }

    public boolean getPartieTerminee() {
        return partieTerminee;
    }

    public String getTemps() {
        int minute = temps / 60;
        int seconde = temps % 60;
        if (seconde < 10) {
            return "" + minute + " : 0" + seconde;
        } else {
            return "" + minute + " : " + seconde;
        }
    }

    public String getScoreboard() {
        String scoreboard = "";
        scoreboard += "TOP 5 DES SCORES \n";
        Collections.sort(listeJoueurs);
        if (listeJoueurs.isEmpty()) {
            scoreboard += "Il n'y a aucun score à afficher pour l'instant.";
        } else {
            for (int i = 0; i < listeJoueurs.size(); i++) {
                if (i < 5) {
                    scoreboard += ("\n" + (i + 1) + " - " + listeJoueurs.get(i));
                }
            }
        }
        return scoreboard;
    }
    
    /**
     * Met à jour la Vue (MVC).
     */
    private void miseAJourVue() {
        setChanged();
        notifyObservers();
    }
}
