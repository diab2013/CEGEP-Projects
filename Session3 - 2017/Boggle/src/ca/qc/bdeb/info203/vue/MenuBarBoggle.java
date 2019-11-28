package ca.qc.bdeb.info203.vue;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBarBoggle extends JMenuBar {
//Menus
    private final JMenu mnuFichier = new JMenu("Fichier");
    private final JMenu mnuAide = new JMenu("Aide");
    private final JMenu mnuOptions = new JMenu("Options");

//MenuItems
    private final JMenuItem mnuItemNouvellePartie = new JMenuItem("Nouvelle partie");
    private final JMenuItem mnuItem4x4 = new JMenuItem("4 x 4");
    private final JMenuItem mnuItem5x5 = new JMenuItem("5 x 5");
    private final JMenuItem mnuItem6x6 = new JMenuItem("6 x 6");
    private final JMenuItem mnuItem7x7 = new JMenuItem("7 x 7");
    private final JMenuItem mnuItemQuitter = new JMenuItem("Quitter");
    private final JMenuItem mnuItemPropos = new JMenuItem("À propos");

    /**
     * Constructeur de la class MenuBarLettre
     */
    public MenuBarBoggle() {
        mnuOptions.add(mnuItem4x4);
        mnuOptions.add(mnuItem5x5);
        mnuOptions.add(mnuItem6x6);
        mnuOptions.add(mnuItem7x7);

        mnuFichier.add(mnuItemNouvellePartie);
        mnuFichier.add(mnuOptions);
        mnuFichier.addSeparator();
        mnuFichier.add(mnuItemQuitter);

        mnuAide.add(mnuItemPropos);

        this.add(mnuFichier);
        this.add(mnuAide);
    }

    /**
     * Getter du menu fichier
     *
     * @return Le menu fichier
     */
    public JMenu getMnuFichier() {
        return mnuFichier;
    }

    /**
     * Getter du menu aide
     *
     * @return Le menu aide
     */
    public JMenu getMnuAide() {
        return mnuAide;
    }

    /**
     * Getter du menu options
     *
     * @return Le menu optons
     */
    public JMenu getMnuOptions() {
        return mnuOptions;
    }

    /**
     * Getter du menu item nouvelle partie
     *
     * @return Le menu item nouvelle partie
     */
    public JMenuItem getMnuItemNouvellePartie() {
        return mnuItemNouvellePartie;
    }

    /**
     * Getter du menu item 4 x 4
     *
     * @return Le menu item 4 x 4
     */
    public JMenuItem getMnuItem4x4() {
        return mnuItem4x4;
    }

    /**
     * Getter du menu item 5 x 5
     *
     * @return Le menu item 5 x 5
     */
    public JMenuItem getMnuItem5x5() {
        return mnuItem5x5;
    }

    /**
     * Getter du menu item 6 x 6
     *
     * @return Le menu item 6 x 6
     */
    public JMenuItem getMnuItem6x6() {
        return mnuItem6x6;
    }

    /**
     * Getter du menu item 7 x 7
     *
     * @return Le menu item 7 x 7
     */
    public JMenuItem getMnuItem7x7() {
        return mnuItem7x7;
    }

    /**
     * Getter du menu item item quitter
     *
     * @return Le menu item quitter
     */
    public JMenuItem getMnuItemQuitter() {
        return mnuItemQuitter;
    }

    /**
     * Getter du menu item à propos
     *
     * @return Le menu item à propos
     */
    public JMenuItem getMnuItemPropos() {
        return mnuItemPropos;
    }
}
