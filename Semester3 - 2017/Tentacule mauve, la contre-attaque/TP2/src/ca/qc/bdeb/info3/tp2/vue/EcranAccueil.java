package ca.qc.bdeb.info3.tp2.vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Classe de l'écran d'accueil.
 */
public class EcranAccueil extends JPanel {

    private final int LARGEUR = 512, HAUTEUR = 534;
    private ImageIcon nouvelleParie = new ImageIcon("bin\\images\\nouvelle_partie.png");
    private ImageIcon reglements = new ImageIcon("bin\\images\\reglements.png");
    private Image background = Toolkit.getDefaultToolkit().getImage("bin\\images\\accueil.png");
    private JButton btnNouvellePartie = new JButton(nouvelleParie);
    private JButton btnReglements = new JButton(reglements);
    Monde monde;
    Fenetre fenetre;

    /**
     * Constructeur de l'écran d'accueil.
     * 
     * @param monde Le monde du jeu
     * @param fenetre La fenêtre du jeu
     */
    public EcranAccueil(Monde monde, Fenetre fenetre) {
        this.fenetre = fenetre;
        this.monde = monde;
        
        // Le monde est en pause tant que l'utilisateur est sur l'écran d'accueil
        monde.setEnPause(true);
        
        // Créer l'écran d'accueil
        setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
        setLayout(null);
        
        // Ajoute les boutons à l'écran
        add(btnNouvellePartie);
        btnNouvellePartie.setBounds(156, 178, 200, 60);
        add(btnReglements);
        btnReglements.setBounds(156, 356, 200, 60);
        
        // Crée les actions des butons
        creerActions();
    }

    /**
     * Crée les actions des boutons.
     */
    private void creerActions() {
        btnNouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fenetre.creerEcranPartie();
            }
        });
        btnReglements.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fenetre.montrerReglements();
            }
        });
    }

    /**
     * Dessine l'arrière-plan de la fenêtre.
     * 
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }

}
