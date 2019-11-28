package ca.qc.bdeb.info3.tp2.vue;

import ca.qc.bdeb.info3.tp2.controleur.Controleur;
import ca.qc.bdeb.info3.tp2.modele.Joueur;
import ca.qc.bdeb.info3.tp2.modele.Modele;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Classe de la fenêtre principale du jeu.
 */
public class Fenetre extends JFrame implements Observer {

    private Controleur controleur;
    private Modele modele;
    private Monde monde;
    private Joueur joueurPrincipal = new Joueur("");
    private EcranAccueil fenetreAccueil;
    private JMenuBar mnuBar = new JMenuBar();
    private JMenu mnuFichier = new JMenu("Fichier");
    private JMenu mnuQuestion = new JMenu("?");
    private JMenuItem mnuFichierNouvellePartie = new JMenuItem("Nouvelle partie");
    private JMenuItem mnuFichierTableauScore = new JMenuItem("Tableau des scores...");
    private JMenuItem mnuFichierQuitter = new JMenuItem("Quitter");
    private JMenuItem mnuQuestionAide = new JMenuItem("Aide...");
    private JMenuItem mnuQuestionAPropos = new JMenuItem("À propos...");
    private ArrayList<Integer> listeKeyCodes = new ArrayList<>();
    private JPanel pnlInfoJeu = new JPanel();
    private JLabel lblTemps = new JLabel("0 : 00");
    private JLabel lblScore = new JLabel("Score : 0");

    /**
     * Constructeur de la classe.
     *
     * @param controleur Le contrôleur du jeu
     * @param modele Le modèle du jeu
     */
    public Fenetre(Controleur controleur, Modele modele) {
        this.controleur = controleur;
        this.modele = modele;
        this.monde = new Monde(listeKeyCodes, controleur, modele, joueurPrincipal);
        this.modele.addObserver(this);
        fenetreAccueil = new EcranAccueil(monde, this);

        // Ajouter des évènements sur certains éléments graphiques
        creerEvenements();

        // Créer l'interface graphique
        creerInterface();

        // Rendre la fenêtre visible
        setVisible(true);

        // Demander le nom du joueur
        String nom = null;
        boolean nomValide = false;
        do {
            nom = JOptionPane.showInputDialog(this, "Entrez votre nom.");
            if (nom == null || nom.equals("")) {
                nomValide = false;
            } else {
                nomValide = true;
            }
        } while (!nomValide);
        joueurPrincipal.setNom(nom);
    }

    /**
     * Met à jour l'affichage graphique des coeurs selon les points de vie
     * actuels de l'héro.
     */
    private void miseAJourCoeurs() {
        for (int i = 0; i < 3; i++) {
            Coeur coeur = new Coeur();
            pnlInfoJeu.add(coeur);
            coeur.setLocation(422 + 23 * i, 2);
            if (i > modele.getPointsVieHero() - 1) {
                coeur.perdreVie();
            }
        }
    }

    /**
     * Crée l'interface graphique de la fenêtre.
     */
    private void creerInterface() {
        // Création de la fenêtre
        setTitle("TENTACULE MAUVE : LA CONTRE-ATTAQUE");
        setIconImage(new ImageIcon("bin\\images\\window_icon.png").getImage());
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Création du menu
        setJMenuBar(mnuBar);
        mnuBar.add(mnuFichier);
        mnuBar.add(mnuQuestion);
        mnuFichier.add(mnuFichierNouvellePartie);
        mnuFichier.addSeparator();
        mnuFichier.add(mnuFichierTableauScore);
        mnuFichier.addSeparator();
        mnuFichier.add(mnuFichierQuitter);
        mnuQuestion.add(mnuQuestionAide);
        mnuQuestion.addSeparator();
        mnuQuestion.add(mnuQuestionAPropos);

        // Création de l'écran d'accueil
        add(fenetreAccueil);

        // Compacter et centrer la fenêtre
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Crée l'écran de jeu.
     */
    public void creerEcranPartie() {
        // Crée une nouvelle partie
        monde.setEnPause(false);
        monde.setNouvellePartie(true);
        monde.setPartieCommencee(true);
        this.requestFocus();

        // Met à jour le contenu de la fenêtre
        remove(fenetreAccueil);
        add(monde, BorderLayout.CENTER);

        // Création du panel contenant le score, le temps et les coeurs
        pnlInfoJeu.setLayout(null);
        pnlInfoJeu.setPreferredSize(new Dimension(512, 22));
        pnlInfoJeu.setBorder(BorderFactory.createLineBorder(new Color(24, 160, 104), 1));
        lblScore.setSize(new Dimension(80, 20));
        lblTemps.setSize(new Dimension(80, 20));
        lblScore.setLocation(30, 1);
        lblTemps.setLocation(241, 1);
        pnlInfoJeu.add(lblScore);
        pnlInfoJeu.add(lblTemps);
        miseAJourCoeurs();
        add(pnlInfoJeu, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    /**
     * Ajouter des évènements sur les éléments graphiques de la fenêtre
     */
    private void creerEvenements() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (validerAction("Êtes-vous certain de vouloir quitter le jeu ?", " Quitter le jeu")) {
                    quitterJeu();
                }
            }
        });
        mnuFichierNouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (monde.getPartieCommencee()) {
                    monde.setNouvellePartie(true);
                } else {
                    creerEcranPartie();
                    monde.setPartieCommencee(true);
                }
            }
        });
        mnuFichierTableauScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                monde.setEnPause(true);
                montrerScoreboard();
                if (monde.getPartieCommencee()) {
                    monde.setEnPause(false);
                }
            }
        });
        mnuFichierQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (validerAction("Êtes-vous certain de vouloir quitter le jeu ?", " Quitter le jeu")) {
                    quitterJeu();
                }
            }
        });
        mnuQuestionAide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                monde.setEnPause(true);
                montrerReglements();
                if (monde.getPartieCommencee()) {
                    monde.setEnPause(false);
                }
            }
        });
        mnuQuestionAPropos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                monde.setEnPause(true);
                JOptionPane.showMessageDialog(Fenetre.this, "Vlad Drelciuc & Diab Khanafer"
                        + "\nProjet remis avant le 8 décembre 2017", " À propos", JOptionPane.PLAIN_MESSAGE);
                if (monde.getPartieCommencee()) {
                    monde.setEnPause(false);
                }
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (!listeKeyCodes.contains(ke.getKeyCode())) {
                    listeKeyCodes.add(ke.getKeyCode());
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                listeKeyCodes.remove(new Integer(ke.getKeyCode()));
            }
        });
    }

    /**
     * Affiche une question à l'utilisateur dans un JOptionPane.
     *
     * @param question La question posée à l'utilisateur
     * @param titreFenetre Le titre de la fenêtre
     * @return La réponse de l'utilisateur
     */
    private boolean validerAction(String question, String titreFenetre) {
        monde.setEnPause(true);
        Object[] options = {"Oui", "Non"};
        boolean reponse = JOptionPane.showOptionDialog(Fenetre.this, question, titreFenetre,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                options, options[0]) == JOptionPane.YES_OPTION;
        monde.setEnPause(false);
        return reponse;
    }

    /**
     * Méthode de mise à jour par le patron observateur.
     *
     * @param o Obsersable
     * @param o1 Objet
     */
    @Override
    public void update(Observable o, Object o1) {
        pnlInfoJeu.removeAll();

        // Mettre à jour le score
        lblScore.setText("Score : " + modele.getScore());
        pnlInfoJeu.add(lblScore);

        // Mettre à jour le chronomètre de la partie
        lblTemps.setText(modele.getTemps());
        pnlInfoJeu.add(lblTemps);

        // Mettre à jour les coeurs (les vies)
        miseAJourCoeurs();

        revalidate();
        repaint();
    }

    /**
     * Montre les règlements du jeu dans un JOptionPane
     */
    public void montrerReglements() {
        JOptionPane.showMessageDialog(Fenetre.this,
                "PRINCIPE DU JEU"
                + "\nVous incarnez Diab, un personnage chargé de protéger "
                + "\nle monde contre l’invasion de l’armée de Tentacule "
                + "\nMauve. Armé d’un pistolet laser, vous devez tuer tous "
                + "\nles ennemis qui apparaîtront sur un ou plusieurs côtés "
                + "\nde l’écran par vagues de 3 secondes. Attention : plus "
                + "\nle temps file, plus il y aura d’ennemis à l’écran et "
                + "\nplus ils seront difficiles à tuer. Chaque ennemi tué "
                + "\naugmente votre score."
                + "\n"
                + "\nBONUS"
                + "\nAprès avoir été tués, certains ennemis laisseront "
                + "\ntomber à l’occasion, des bonus que vous pourrez "
                + "\nrécolter. Ces bonus comprennent : "
                + "\n- Des potions pour regénérer toutes vos vies;"
                + "\n- Une bombe qui détruit tous les ennemis sur l'écran;"
                + "\n- Une carabine qui permet de tirer dans 3 directions "
                + "\nsimultanément pendant quelques secondes."
                + "\n"
                + "\nFIN DE LA PARTIE"
                + "\nUne partie ne s’arrête que lorsque le héros perd ses "
                + "\n3 vies. Le joueur a alors l’option de recommencer une "
                + "\nnouvelle partie ou de quitter le jeu."
                + "\n"
                + "\nCONTRÔLES"
                + "\nTouches directionnelles (HAUT, BAS, GAUCHE et DROITE) "
                + "\npour se déplacer."
                + "\nBarre d’espacement (SPACE) pour tirer.", " Règlements", JOptionPane.PLAIN_MESSAGE);
    }
    
    /**
     * Enregistre le scoreboard et quitte le jeu.
     */
    private void quitterJeu(){
        modele.enregistrerScoreboard();
        System.exit(0);
    }
    
    /**
     * Montre le panneau des meilleurs scores dans un JOptionPane
     */
    private void montrerScoreboard(){
        JOptionPane.showMessageDialog(Fenetre.this,
                modele.getScoreboard(), " Meilleurs scores", JOptionPane.PLAIN_MESSAGE);
    }
}
