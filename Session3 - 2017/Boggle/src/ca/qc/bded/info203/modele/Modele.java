package ca.qc.bded.info203.modele;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Modele extends Observable {
    
//Liste de mot loaded du document et liste de mot utilisé lors de la partie
    private final ArrayList<String> listMots = new ArrayList();
    private final ArrayList<String> listUsed = new ArrayList();

//Tableau de vérification pour faire déterminer les adjacents
    private boolean[][] tabClicked = new boolean[4][4];
    private boolean[][] tabAdjacent = new boolean[4][4];

//Points joueurs
    private int pointsJoueur = 0;
    private boolean existe = false;
    private boolean used = false;

//Rangée - Colonne 
    private int ran = 4;
    private int col = 4;

//Enum pour les methodes update() des Observers
    public enum UPDATE {
        TIMER,
        ADJCT,
        NEW,
        END;
    }
    private UPDATE status;

//Timer et toutes les variables nécessaires à son fonctionnement
    private int time = 180;
    private int timeMinutes;
    private int timeSecondes;
    private Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            time--;
            timeMinutes = time / 60;
            timeSecondes = time % 60;
            status = UPDATE.TIMER;
            majAll();
            if (time == 0) {
                timer.stop();
                status = UPDATE.END;
                majAll();
            }
        }
    });

    /**
     * Constructeur de la classe Modele
     */
    public Modele() {
        loadListe();
        timer.start();
    }

    /**
     * La methode demande à l'utilisateur de confirmer son action. Elle ensuite change la taille
     * du tableau de buttons
     * @param i Le nombre indiquant la longueur et largeur du tableau de buttons ainsi
     *          que l'écriture du JOptionPane
     */
    public void switchCase(int i) {
        if (JOptionPane.showConfirmDialog(null, "La taille deviendra " + i + " x " + i, i + " x " + i, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            status = UPDATE.NEW;
            ran = i;
            col = i;
            majAll();
        }
    }

    /**
     * Met fin au jeu et dit aux vues de s'update pour cet effet.
     */
    public void endGame() {
        status = UPDATE.END;
        majAll();
    }

    /**
     * Reinitialise les variables du jeu et dit aux vues de s'update pour cet effet.
     */
    public void newGame() {
        status = UPDATE.NEW;
        tabAdjacent = new boolean[ran][col];
        tabClicked = new boolean[ran][col];
        listUsed.clear();
        time = 180;
        pointsJoueur = 0;
        majAll();
        timer.start();
    }

    /**
     * Remet à false tout les cases du tableau qui vérifie si le button à été cliqué
     */
    public void resetClicked() {
        for (int i = 0; i < tabClicked.length; i++) {
            for (int j = 0; j < tabClicked[i].length; j++) {
                tabClicked[i][j] = false;
            }
        }
    }

    /**
     * Remet à false tout les cases du tableau qui vérifie si le button est adjacent
     */
    public void resetAdjacent() {
        for (int i = 0; i < tabAdjacent.length; i++) {
            for (int j = 0; j < tabAdjacent[i].length; j++) {
                tabAdjacent[i][j] = false;
            }
        }
    }

    /**
     * Met à true tout cases adjacentes aux coordonnées reçu par la methode, elle vérifie
     * à ce que le programme ne bug par en cliquant sur des buttons situés aux extrimitées
     * @param posX Position X du button cliqué
     * @param posY Position Y du button cliqué
     */
    public void checkAdj(int posX, int posY) {
        tabClicked[posX][posY] = true;
        try {
            if (!tabClicked[posX - 1][posY - 1]) {
                tabAdjacent[posX - 1][posY - 1] = true;
            }
        } catch (ArrayIndexOutOfBoundsException err) {
        }
        try {
            if (!tabClicked[posX - 1][posY]) {
                tabAdjacent[posX - 1][posY] = true;
            }
        } catch (ArrayIndexOutOfBoundsException err) {
        }
        try {
            if (!tabClicked[posX - 1][posY + 1]) {
                tabAdjacent[posX - 1][posY + 1] = true;
            }
        } catch (ArrayIndexOutOfBoundsException err) {
        }
        try {
            if (!tabClicked[posX][posY - 1]) {
                tabAdjacent[posX][posY - 1] = true;
            }
        } catch (ArrayIndexOutOfBoundsException err) {
        }
        try {
            if (!tabClicked[posX][posY + 1]) {
                tabAdjacent[posX][posY + 1] = true;
            }
        } catch (ArrayIndexOutOfBoundsException err) {
        }
        try {
            if (!tabClicked[posX + 1][posY - 1]) {
                tabAdjacent[posX + 1][posY - 1] = true;
            }
        } catch (ArrayIndexOutOfBoundsException err) {
        }
        try {
            if (!tabClicked[posX + 1][posY]) {
                tabAdjacent[posX + 1][posY] = true;
            }
        } catch (ArrayIndexOutOfBoundsException err) {
        }
        try {
            if (!tabClicked[posX + 1][posY + 1]) {
                tabAdjacent[posX + 1][posY + 1] = true;
            }
        } catch (ArrayIndexOutOfBoundsException err) {
        }
        status = UPDATE.ADJCT;
        majAll();
    }

    /**
     * Envois un lettre généré de façon random, avec un certain pourcentage pour chacune, pour 
     * chaque button du jeu créé
     * @return La lettre généré de façon random
     */
    public String lettreGen() {
        Random random = new Random();
        int r = random.nextInt(1000);
        String lettre;
        if (r <= 80) {
            lettre = "A";
        } else if (r <= 89) {
            lettre = "B";
        } else if (r <= 123) {
            lettre = "C";
        } else if (r <= 160) {
            lettre = "D";
        } else if (r <= 331) {
            lettre = "E";
        } else if (r <= 342) {
            lettre = "F";
        } else if (r <= 351) {
            lettre = "G";
        } else if (r <= 358) {
            lettre = "H";
        } else if (r <= 436) {
            lettre = "I";
        } else if (r <= 441) {
            lettre = "J";
        } else if (r == 442) {
            lettre = "K";
        } else if (r <= 497) {
            lettre = "L";
        } else if (r <= 527) {
            lettre = "M";
        } else if (r <= 598) {
            lettre = "N";
        } else if (r <= 651) {
            lettre = "O";
        } else if (r <= 681) {
            lettre = "P";
        } else if (r <= 695) {
            lettre = "QU";
        } else if (r <= 761) {
            lettre = "R";
        } else if (r <= 839) {
            lettre = "S";
        } else if (r <= 911) {
            lettre = "T";
        } else if (r <= 974) {
            lettre = "U";
        } else if (r <= 990) {
            lettre = "V";
        } else if (r == 991) {
            lettre = "W";
        } else if (r <= 995) {
            lettre = "X";
        } else if (r <= 998) {
            lettre = "Y";
        } else {
            lettre = "Z";
        }
        return lettre;
    }
    
    /**
     * Check la taille du mot fait par le joueur et lui attribut un nombre de points selon sa taille
     * @param mot Le mot fait par le joueur en cliquant sut les buttons
     */
    private void checkTaille(String mot) {
        if (mot.length() > 2 && mot.length() <= 4) {
            pointsJoueur += 1;
        } else if (mot.length() == 5) {
            pointsJoueur += 2;
        } else if (mot.length() == 6) {
            pointsJoueur += 3;
        } else if (mot.length() == 7) {
            pointsJoueur += 5;
        } else {
            pointsJoueur += 11;
        }
    }

    /**
     * Vérifie si le mot existe, à déjà été utilisé, et s'il doit être ajouter au dictionnaire
     * @param motFinal Mot finalisé écrit par le joueur
     */
    public void checkmot(String motFinal) {
        existe = false;
        used = false;
        if (listUsed.contains(motFinal.toLowerCase())) {
            JOptionPane.showMessageDialog(null, "Vous avez deja utilisé le mot ' " + motFinal + " ' avant.", "Ajout d'un mot", JOptionPane.OK_OPTION);
            existe = true;
            used = true;
        } else if (listMots.contains(motFinal.toLowerCase()) && !listUsed.contains(motFinal.toLowerCase())) {
            listUsed.add(motFinal.toLowerCase());
            checkTaille(motFinal);
            existe = true;
        }
        if ((motFinal.length() >= 3) && (existe == false)) {
            int answer = JOptionPane.showConfirmDialog(null, "Voulez vous ajoutez le mot " + motFinal + " à la liste des mots possibles?", "Ajout d'un mot", JOptionPane.YES_NO_OPTION);
            if (answer == 0) {
                ajoutMot(motFinal);
                listMots.add(motFinal.toLowerCase());
            }
        } else if (existe == false) {
            JOptionPane.showMessageDialog(null, "Le mot doit être de 3 lettres minimum.", "Mot incorrect", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Permet l'écriture du mot dans le fichier contenant tous les mots
     * @param mot Le mot que la méthode doit écrire dans le fichier
     */
    private void ajoutMot(String mot) {
        try {
            PrintWriter write = new PrintWriter(new FileOutputStream("liste_francais.txt", true));
            write.println(mot.toLowerCase());
            write.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Le fichier est introuvable.", "Fichier introuvable", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Permet de "load" le fichier txt incluant tous les mots pour ensuite les mettre dans une 
     * liste de mot
     */
    private void loadListe() {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("liste_francais.txt"));
            String ligne;
            while ((ligne = fileReader.readLine()) != null) {
                listMots.add(ligne);
            }
            fileReader.close();
        } catch (FileNotFoundException err) {
            JOptionPane.showMessageDialog(null, "Le fichier n'a pas été trouvée!");
            System.exit(0);
        } catch (IOException err) {
            JOptionPane.showMessageDialog(null, "Le fichier n'a pu être lu!");
            System.exit(0);
        }
    }

    /**
     * Se met en "changed" state et "notify" les Observers du changement pour qu'ils se mettent
     * à jour
     */
    private void majAll() {
        setChanged();
        notifyObservers();
    }

    /**
     * Getter pour le nombre de points du joueur
     * @return Le nombre de points
     */
    public int getPoints() {
        return pointsJoueur;
    }

    /**
     * Getter pour le nombre de rangées du tableau
     * @return Le nombre de rangée
     */
    public int getRan() {
        return ran;
    }

    /**
     * Getter pour le nombre de colonnes du tableau
     * @return Le nombre de colonne
     */
    public int getCol() {
        return col;
    }

    /**
     * Getter pour le nombres de minutes restant
     * @return Le nombre de minutes
     */
    public int getTimeMinutes() {
        return timeMinutes;
    }

    /**
     * Getter pour le nombres de secondes restant
     * @return Le nombre de secondes
     */
    public int getTimeSecondes() {
        return timeSecondes;
    }

    /**
     * Getter pour le status actuel du update
     * @return Le status du update
     */
    public UPDATE getStatus() {
        return status;
    }

    /**
     * Getter pour la case du tableau adjacent à des coordonnées spécifiques    
     * @param i Coordonnée en X du tableau
     * @param j Coordonnée en Y du tableau
     * @return La valeur de la case du tableau (true/false)
     */
    public boolean getTabAdjacent(int i, int j) {
        return tabAdjacent[i][j];
    }

    /**
     * Getter pour la variable "existe"
     * @return La valeur de "existe" (true/false)
     */
    public boolean isExiste() {
        return existe;
    }

    /**
     * Getter pour la variable "used"
     * @return La valeur de "used" (true/false)
     */
    public boolean isUsed() {
        return used;
    }

}
