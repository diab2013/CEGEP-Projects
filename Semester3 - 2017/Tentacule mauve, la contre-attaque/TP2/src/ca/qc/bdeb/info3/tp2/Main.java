package ca.qc.bdeb.info3.tp2;

import ca.qc.bdeb.info3.tp2.controleur.Controleur;
import javax.swing.UIManager;

/**
 * Classe Main du programme.
 *
 * @author Vlad Drelciuc et Diab Khanafer
 */
public class Main {

    /**
     * Méthode Main du programme.
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exc) {
        }
        Controleur controleur = new Controleur();
    }

}
