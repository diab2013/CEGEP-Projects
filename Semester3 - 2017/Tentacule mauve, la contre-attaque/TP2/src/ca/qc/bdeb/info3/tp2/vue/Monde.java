package ca.qc.bdeb.info3.tp2.vue;

import ca.qc.bdeb.info3.tp2.controleur.Controleur;
import ca.qc.bdeb.info3.tp2.modele.Joueur;
import ca.qc.bdeb.info3.tp2.modele.Modele;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_UP;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Classe du monde du jeu.
 */
public class Monde extends JPanel {

    private final int LARGEUR = 512, HAUTEUR = 512;
    private ArrayList<Obstacle> listeObstacles = new ArrayList<>();
    private ArrayList<Ennemi> listeEnnemis = new ArrayList<>();
    private ArrayList<Projectile> listeProjectiles = new ArrayList<>();
    private ArrayList<Integer> listeKeyCodes = new ArrayList<>();
    private ArrayList<Object> listeObjetsAEffacer = new ArrayList<>();
    private ArrayList<Bonus> listeBonus = new ArrayList<>();
    private Rectangle rectangleTemporaire = new Rectangle();
    private Joueur joueur;
    private Random random = new Random();
    private Controleur controleur;
    private Modele modele;
    private boolean projectileTirable = true;
    private int vagueEnnemis = 0;
    private int vagueDuShotgun = 0;
    private int recurrence = 0;
    private boolean nouvellePartie = true;
    private boolean tuerTousLesEnnemis = false;
    private boolean enPause = false;
    private boolean partieCommencee = false;

    private Image imageSol = Toolkit.getDefaultToolkit().getImage("bin\\images\\sol.gif");
    private Hero hero = new Hero();

    /**
     * Constructeur de la classe.
     *
     * @param listeKeyCodes Liste des touches appuyées par l'utilisateur
     * @param controleur Le contrôleur du jeu
     * @param modele Le modèle du jeu
     * @param joueur Le joueur actuel
     */
    public Monde(ArrayList<Integer> listeKeyCodes, Controleur controleur, Modele modele, Joueur joueur) {
        this.modele = modele;
        this.controleur = controleur;
        this.listeKeyCodes = listeKeyCodes;
        this.joueur = joueur;

        // Formatter le monde 
        setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
        setLayout(null);

        // Commencer une nouvelle partie
        nouvellePartie = true;
        thread.start();
    }

    /**
     * Thread principal du programme.
     */
    private Thread thread = new Thread() {
        @Override
        public void run() {
            while (true) {
                // Incrémentation de la récurrence (une récurrence correspond à un délai de 15 ms)
                recurrence++;

                // Gestion de la pause
                if (enPause) {
                    modele.pauseLaPartie();
                    while (enPause) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                        }
                    }
                    modele.continuerLaPartie();
                }

                // Le shotgun redevient un laser après 4 vagues
                if (hero.getArmeEquipee() == Hero.ArmeEquipee.SHOTGUN) {
                    if ((vagueEnnemis - vagueDuShotgun - 4) % 5 == 0) {
                        hero.setArmeEquipee(Hero.ArmeEquipee.LASER);
                    }
                }

                // Un projectile est tirable toutes les 225 ms
                if (recurrence % 15 == 0) {
                    projectileTirable = true;
                }

                // Une vague d'ennemis apparait aux 3000 ms
                if (recurrence % 200 == 0) {
                    creerVagueEnnemis();
                }

                // Gestion des actions du héros (bouger et tirer des projectiles)
                actionHero();

                // Gestion des collisions héros + bonus
                for (Bonus bonus : listeBonus) {
                    if (hero.getBounds().intersects(bonus.getBounds())) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                bonus.executerAction(Monde.this, modele);
                                controleur.ajusterScore(bonus.getValeur());
                                if (bonus instanceof Shotgun) {
                                    vagueDuShotgun = vagueEnnemis;
                                }
                                listeObjetsAEffacer.add(bonus);
                            }
                        });
                        bonus.executerAction(Monde.this, modele);
                        if (bonus instanceof Shotgun) {
                            vagueDuShotgun = vagueEnnemis;
                        }
                        listeObjetsAEffacer.add(bonus);
                    }
                }

                // Gestion des collisions ennemis
                for (Ennemi ennemi1 : listeEnnemis) {
                    // Ennemi + obstacle et ennemi + autre ennemi
                    if (validerCollisionAvecObstacles(ennemi1) && validerCollisonAvecEnnemis(ennemi1)) {
                        ennemi1.bouger(hero.getX(), hero.getY());
                    }
                    // Ennemi + héros
                    if (ennemi1.getBounds().intersects(hero.getBounds())) {
                        blesserEnnemi(ennemi1);
                        controleur.heroPerdUneVie(joueur.getNom());
                        listeObjetsAEffacer.add(ennemi1); // L'ennemi meurt, peu importe son nombre de vies
                    }
                }

                // Gestion des collisions projectiles
                for (Projectile projectile : listeProjectiles) {
                    projectile.voler();
                    for (Ennemi ennemi : listeEnnemis) {
                        // Projectile + ennemi
                        if (ennemi.getBounds().intersects(projectile.getBounds())) {
                            listeObjetsAEffacer.add(projectile);
                            blesserEnnemi(ennemi);
                        }
                    }
                    if (projectile.getX() < 0 || projectile.getX() > LARGEUR || projectile.getY() < 0 || projectile.getY() > HAUTEUR) {
                        // Projectile + limites de l'écran
                        listeObjetsAEffacer.add(projectile);
                    }
                    for (Obstacle obstacle : listeObstacles) {
                        // Projectile + obstacle
                        if (projectile.getBounds().intersects(obstacle.getBounds())) {
                            listeObjetsAEffacer.add(projectile);
                        }
                    }
                }

                // Si le bonus bombe est ramassé, tue tous les ennemis à l'écran
                if (tuerTousLesEnnemis) {
                    tuerTousLesEnnemis();
                }

                // Si le personnage n'a plus de vies, la partie se termine
                if (modele.getPartieTerminee()) {
                    terminerPartie(joueur);
                }

                // Si l'utilisateur choisit de commencer une nouvelle partie
                if (nouvellePartie) {
                    nouvellePartie();
                }

                // Gestion de la disparition des éléments graphiques de l'écran
                for (Object object : listeObjetsAEffacer) {
                    if (!(object instanceof Integer)) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                remove((Component) object);
                            }
                        });
                    }
                }
                listeKeyCodes.removeAll(listeObjetsAEffacer);
                listeEnnemis.removeAll(listeObjetsAEffacer);
                listeProjectiles.removeAll(listeObjetsAEffacer);
                listeBonus.removeAll(listeObjetsAEffacer);
                listeObjetsAEffacer.clear();

                revalidate();
                repaint();

                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                }
            }
        }
    };

    /**
     * Vérifie si un personnage entrerait en collission avec un obstacle s'il
     * bougeait dans une des quatre directions.
     *
     * @param personnage Le personnage dont on souhaite valider le mouvement
     * @return Si le mouvement est permis ou non
     */
    private boolean validerCollisionAvecObstacles(Personnage personnage) {
        boolean mouvementPermis = true;
        rectangleTemporaire.setBounds(personnage.getX(), personnage.getY(), personnage.getWidth(), personnage.getHeight());
        for (Obstacle obstacle : listeObstacles) {
            rectangleTemporaire.setLocation(personnage.getX(), personnage.getY() - personnage.getVitesse());
            if (rectangleTemporaire.intersects(obstacle.getBounds())) {
                mouvementPermis = false;
                personnage.setLocation(personnage.getX(), personnage.getY() + 1);
            }
            rectangleTemporaire.setLocation(personnage.getX(), personnage.getY() + personnage.getVitesse());
            if (rectangleTemporaire.intersects(obstacle.getBounds())) {
                mouvementPermis = false;
                personnage.setLocation(personnage.getX(), personnage.getY() - 1);
            }
            rectangleTemporaire.setLocation(personnage.getX() - personnage.getVitesse(), personnage.getY());
            if (rectangleTemporaire.intersects(obstacle.getBounds())) {
                mouvementPermis = false;
                personnage.setLocation(personnage.getX() + 1, personnage.getY());
            }
            rectangleTemporaire.setLocation(personnage.getX() + personnage.getVitesse(), personnage.getY());
            if (rectangleTemporaire.intersects(obstacle.getBounds())) {
                mouvementPermis = false;
                personnage.setLocation(personnage.getX() - 1, personnage.getY());
            }
        }
        return mouvementPermis;
    }

    /**
     * Gère les actions du héros. En particulier, séplace l'héros ou lui fait
     * tirer un projectile, selon les touches appuyées par l'utilisateur.
     */
    private void actionHero() {
        boolean bougeable = validerCollisionAvecObstacles(hero);
        if (listeKeyCodes.contains(VK_UP)) {
            hero.tourner(Bougeable.Direction.NORD);
            if (hero.getY() - hero.getVitesse() >= 0 && bougeable) {
                hero.bouger(Bougeable.Direction.NORD);
            }
        }
        if (listeKeyCodes.contains(VK_DOWN)) {
            hero.tourner(Bougeable.Direction.SUD);
            if ((hero.getY() + hero.getHeight()) + hero.getVitesse() <= HAUTEUR && bougeable) {
                hero.bouger(Bougeable.Direction.SUD);
            }
        }
        if (listeKeyCodes.contains(VK_RIGHT)) {
            hero.tourner(Bougeable.Direction.EST);
            if ((hero.getX() + hero.getWidth()) + hero.getVitesse() <= LARGEUR && bougeable) {
                hero.bouger(Bougeable.Direction.EST);
            }
        }
        if (listeKeyCodes.contains(VK_LEFT)) {
            hero.tourner(Bougeable.Direction.OUEST);
            if (hero.getX() - hero.getVitesse() >= 0 && bougeable) {
                hero.bouger(Bougeable.Direction.OUEST);
            }
        }
        if (listeKeyCodes.contains(VK_SPACE)) {
            tirerProjectile();
        }
    }

    /**
     * Crée les éléments du décor : buissons, roches, etc.
     */
    private void creerDecor() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if ((i == 0 && (j < 5 || j > 10)) || (i == 15 && (j < 5 || j > 10))
                        || (j == 0 && (i < 5 || i > 10)) || (j == 15 && (i < 5 || i > 10))) {
                    Buisson buisson = new Buisson();
                    add(buisson);
                    buisson.setLocation(i * 32, j * 32);
                    listeObstacles.add(buisson);
                }
                if ((i == 4 && j == 4) || (i == 4 && j == 11) || (i == 11 && j == 4) || (i == 11 && j == 11)) {
                    Roche roche = new Roche();
                    add(roche);
                    roche.setLocation(i * 32, j * 32);
                    listeObstacles.add(roche);
                }
            }
        }
    }

    /**
     * Crée un projectile de type laser ou shotgun (selon l'arme équipée) et lui
     * assigne la même direction que celle du héros.
     */
    private void tirerProjectile() {
        if (projectileTirable) {
            projectileTirable = false;
            switch (hero.getArmeEquipee()) {
                case SHOTGUN:
                    Balle balle1 = null;
                    Balle balle2 = null;
                    Balle balle3 = null;
                    switch (hero.getDirection()) {
                        case NORD:
                            balle1 = new Balle(Bougeable.Direction.NORD_OUEST);
                            balle2 = new Balle(Bougeable.Direction.NORD);
                            balle3 = new Balle(Bougeable.Direction.NORD_EST);
                            break;
                        case SUD:
                            balle1 = new Balle(Bougeable.Direction.SUD_OUEST);
                            balle2 = new Balle(Bougeable.Direction.SUD);
                            balle3 = new Balle(Bougeable.Direction.SUD_EST);
                            break;
                        case EST:
                            balle1 = new Balle(Bougeable.Direction.NORD_EST);
                            balle2 = new Balle(Bougeable.Direction.EST);
                            balle3 = new Balle(Bougeable.Direction.SUD_EST);
                            break;
                        case OUEST:
                            balle1 = new Balle(Bougeable.Direction.NORD_OUEST);
                            balle2 = new Balle(Bougeable.Direction.OUEST);
                            balle3 = new Balle(Bougeable.Direction.SUD_OUEST);
                            break;
                    }
                    listeProjectiles.add(balle1);
                    listeProjectiles.add(balle2);
                    listeProjectiles.add(balle3);
                    add(balle1);
                    add(balle2);
                    add(balle3);
                    balle1.setLocation(hero.getX() + hero.getWidth() / 2 - balle1.getWidth() / 2, hero.getY() + hero.getHeight() / 2 - balle1.getHeight() / 2);
                    balle2.setLocation(hero.getX() + hero.getWidth() / 2 - balle2.getWidth() / 2, hero.getY() + hero.getHeight() / 2 - balle2.getHeight() / 2);
                    balle3.setLocation(hero.getX() + hero.getWidth() / 2 - balle3.getWidth() / 2, hero.getY() + hero.getHeight() / 2 - balle3.getHeight() / 2);
                    break;
                case LASER:
                    Laser laser = new Laser(hero.getDirection());
                    add(laser);
                    laser.setLocation(hero.getX() + hero.getWidth() / 2 - laser.getWidth() / 2, hero.getY() + hero.getHeight() / 2 - laser.getHeight() / 2);
                    listeProjectiles.add(laser);
                    break;
            }
        }
    }

    /**
     * Crée des vagues d'ennemis toutes les 3 secondes. Plus le temps passe,
     * plus il y a d'ennemis, plus ils sont difficiles et plus ils apparaissent
     * sur de multiples côtés.
     */
    private void creerVagueEnnemis() {
        int nombreEnnemis = 3;
        ArrayList<Ennemi> listeEnnemisAPlacer = new ArrayList<>();
        int difficulteEnnemis = 1;

        // Incrémentation du nombre d'ennemis selon la vague actuelle
        for (int i = 0; i < vagueEnnemis / 8; i++) {
            nombreEnnemis++;
        }

        // Incrémentation de la difficulté des ennemis selon la vague actuelle
        for (int i = 0; i < vagueEnnemis / 5; i++) {
            if (difficulteEnnemis < 3) {
                difficulteEnnemis++;
            }
        }

        // Création des ennemis
        for (int i = 0; i < nombreEnnemis; i++) {
            Ennemi ennemi = null;
            switch (random.nextInt(difficulteEnnemis) + 1) {
                case 1:
                    ennemi = new Mechant();
                    break;
                case 2:
                    ennemi = new PasFin();
                    break;
                case 3:
                    ennemi = new TroubleFete();
                    break;
            }
            add(ennemi);
            listeEnnemisAPlacer.add(ennemi);
            listeEnnemis.add(ennemi);
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) {
            }
        }

        // Choix aléatoire des côtés sur lesquels les ennemis vont apparaître
        int cote = random.nextInt(4);
        for (int i = 0; i < listeEnnemisAPlacer.size(); i++) {
            int ancienCote = cote;
            if (i % 4 == 0) {
                cote = random.nextInt(4);
                while (cote == ancienCote) {
                    cote = random.nextInt(4);
                }
            }
            switch (cote) {
                case 0: // HAUT
                    switch (i % 5) {
                        case 0:
                            listeEnnemisAPlacer.get(i).setLocation(240, 0);
                            break;
                        case 1:
                            listeEnnemisAPlacer.get(i).setLocation(170, 0);
                            break;
                        case 2:
                            listeEnnemisAPlacer.get(i).setLocation(275, 0);
                            break;
                        case 3:
                            listeEnnemisAPlacer.get(i).setLocation(205, 0);
                            break;
                        default:
                            listeEnnemisAPlacer.get(i).setLocation(310, 0);
                            break;
                    }
                    break;
                case 1: // BAS
                    switch (i % 5) {
                        case 0:
                            listeEnnemisAPlacer.get(i).setLocation(240, HAUTEUR - listeEnnemisAPlacer.get(i).getHeight());
                            break;
                        case 1:
                            listeEnnemisAPlacer.get(i).setLocation(170, HAUTEUR - listeEnnemisAPlacer.get(i).getHeight());
                            break;
                        case 2:
                            listeEnnemisAPlacer.get(i).setLocation(275, HAUTEUR - listeEnnemisAPlacer.get(i).getHeight());
                            break;
                        case 3:
                            listeEnnemisAPlacer.get(i).setLocation(205, HAUTEUR - listeEnnemisAPlacer.get(i).getHeight());
                            break;
                        default:
                            listeEnnemisAPlacer.get(i).setLocation(310, HAUTEUR - listeEnnemisAPlacer.get(i).getHeight());
                            break;
                    }
                    break;
                case 2: // GAUCHE
                    switch (i % 4) {
                        case 0:
                            listeEnnemisAPlacer.get(i).setLocation(0, 214);
                            break;
                        case 1:
                            listeEnnemisAPlacer.get(i).setLocation(0, 256);
                            break;
                        case 2:
                            listeEnnemisAPlacer.get(i).setLocation(0, 172);
                            break;
                        default:
                            listeEnnemisAPlacer.get(i).setLocation(0, 298);
                            break;
                    }
                    break;
                case 3: // DROITE
                    switch (i % 4) {
                        case 0:
                            listeEnnemisAPlacer.get(i).setLocation(LARGEUR - listeEnnemisAPlacer.get(i).getWidth(), 214);
                            break;
                        case 1:
                            listeEnnemisAPlacer.get(i).setLocation(LARGEUR - listeEnnemisAPlacer.get(i).getWidth(), 256);
                            break;
                        case 2:
                            listeEnnemisAPlacer.get(i).setLocation(LARGEUR - listeEnnemisAPlacer.get(i).getWidth(), 172);
                            break;
                        default:
                            listeEnnemisAPlacer.get(i).setLocation(LARGEUR - listeEnnemisAPlacer.get(i).getWidth(), 298);
                            break;
                    }
                    break;
            }
        }
        vagueEnnemis++;
    }

    /**
     * Vérifie si un ennemi entrerait en collission avec un autre ennemi s'il
     * bougeait dans une des quatre directions.
     *
     * @param ennemi1 L'ennemi dont on souhaite valider le mouvement
     * @return Si le mouvement est permis ou non
     */
    private boolean validerCollisonAvecEnnemis(Ennemi ennemi1) {
        boolean mouvementPermis = true;
        rectangleTemporaire.setBounds(ennemi1.getX(), ennemi1.getY(), ennemi1.getWidth(), ennemi1.getHeight());
        for (Ennemi ennemi2 : listeEnnemis) {
            if (ennemi1 != ennemi2) {
                rectangleTemporaire.setLocation(ennemi1.getX(), ennemi1.getY() - ennemi1.getVitesse());
                if (rectangleTemporaire.intersects(ennemi2.getBounds())) {
                    mouvementPermis = false;
                    ennemi1.setLocation(ennemi1.getX(), ennemi1.getY() + 1);
                }
                rectangleTemporaire.setLocation(ennemi1.getX(), ennemi1.getY() + ennemi1.getVitesse());
                if (rectangleTemporaire.intersects(ennemi2.getBounds())) {
                    mouvementPermis = false;
                    ennemi1.setLocation(ennemi1.getX(), ennemi1.getY() - 1);
                }
                rectangleTemporaire.setLocation(ennemi1.getX() - ennemi1.getVitesse(), ennemi1.getY());
                if (rectangleTemporaire.intersects(ennemi2.getBounds())) {
                    mouvementPermis = false;
                    ennemi1.setLocation(ennemi1.getX() + 1, ennemi1.getY());
                }
                rectangleTemporaire.setLocation(ennemi1.getX() + ennemi1.getVitesse(), ennemi1.getY());
                if (rectangleTemporaire.intersects(ennemi2.getBounds())) {
                    mouvementPermis = false;
                    ennemi1.setLocation(ennemi1.getX() - 1, ennemi1.getY());
                }
            }
        }
        return mouvementPermis;
    }

    /**
     * Gère la perte de vie d'un ennemi. S'il n'a plus de vies, l'ennemi meurt.
     * S'il meurt, il y a des chances qu'un bonus apparaisse à l'endroit de son
     * cadavre.
     *
     * @param ennemi Ennemi qui perd une vie
     */
    private void blesserEnnemi(Ennemi ennemi) {
        ennemi.perdreUneVie();
        if (ennemi.getPointsVie() == 0) {
            controleur.ajusterScore(ennemi.getValeurScore());
            Bonus bonus = null;
            boolean bonusActif = true;
            switch (random.nextInt(50)) {
                case 5:
                    bonus = new Shotgun();
                    break;
                case 10:
                    bonus = new Potion();
                    break;
                case 25:
                    bonus = new Bombe();
                    break;
                default:
                    bonusActif = false;
                    break;
            }
            if (bonusActif) {
                listeBonus.add(bonus);
                bonus.setLocation(ennemi.getX(), ennemi.getY());
                add(bonus);
            }
            listeObjetsAEffacer.add(ennemi);
        }
    }

    /**
     * Dessine l'arrière-plan du jeu.
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                g.drawImage(imageSol, i * 32, j * 32, this);
            }
        }
    }

    /**
     * Lorsque le bonus bombe est collecté, tous les ennemis à l'écran sont
     * tués.
     */
    private void tuerTousLesEnnemis() {
        for (Ennemi ennemi : listeEnnemis) {
            while (ennemi.getPointsVie() > 0) {
                blesserEnnemi(ennemi);
            }
        }
        listeEnnemis.clear();
        tuerTousLesEnnemis = false;
    }

    /**
     * Gère le début d'une nouvelle partie : enlève tous les ennemis, les bonus
     * et les projectiles, replace l'héros, réinitialise les vagues d'ennemis,
     * recrée le décor, etc.
     */
    private void nouvellePartie() {
        // Enlève les ennemis à l'écran
        for (Ennemi ennemi : listeEnnemis) {
            listeObjetsAEffacer.add(ennemi);
        }

        // Enlève les projectiles à l'écran
        for (Projectile projectile : listeProjectiles) {
            listeObjetsAEffacer.add(projectile);
        }

        // Enlève les bonus à l'écran
        for (Bonus bonus : listeBonus) {
            listeObjetsAEffacer.add(bonus);
        }

        // Éfface la liste des boutons appuyés par l'utilisateur
        for (Integer key : listeKeyCodes) {
            listeObjetsAEffacer.add(key);
        }

        // Rééquipe l'héros du laser
        hero.setArmeEquipee(Hero.ArmeEquipee.LASER);

        // Appèle le Modèle qui gère le score, le chronomètre et les vies du héros
        modele.nouvellePartie();

        // Réinitialise les vagues et les paramètres liés au Thread
        projectileTirable = true;
        vagueEnnemis = 0;
        vagueDuShotgun = 0;
        recurrence = 0;

        // Place l'héros au centre de l'écran
        add(hero);
        hero.setLocation(LARGEUR / 2 - hero.getWidth() / 2, HAUTEUR / 2 - hero.getHeight() / 2);

        // Crée les décors
        creerDecor();
        repaint();
        revalidate();

        // Empêche d'être rappellée automatiquement par le Thread
        nouvellePartie = false;
    }

    /**
     * Gère la fin de partie : demande à l'utilisateur de recommencer une partie
     * ou de quitter.
     */
    private void terminerPartie(Joueur joueur) {
        modele.pauseLaPartie();
        Object[] options = {"Nouvelle partie", "Quitter"};
        int reponse = JOptionPane.showOptionDialog(this, "Vous êtes mort. La partie est terminée."
                + "\nVotre score est de " + modele.getScore() + " points."
                + "\nVoulez-vous commencer une nouvelle partie ou quitter ?", " Fin de la partie",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                options, null);
        if (reponse == JOptionPane.YES_OPTION) {
            nouvellePartie();
        } else {
            modele.enregistrerScoreboard();
            System.exit(0);
        }
    }

    public Hero getHero() {
        return hero;
    }

    public void setNouvellePartie(boolean nouvellePartie) {
        this.nouvellePartie = nouvellePartie;
    }

    public void setTuerTousLesEnnemis(boolean tuerTousLesEnnemis) {
        this.tuerTousLesEnnemis = tuerTousLesEnnemis;
    }

    public void setEnPause(boolean enPause) {
        this.enPause = enPause;
    }

    public void setPartieCommencee(boolean partieCommencee) {
        this.partieCommencee = partieCommencee;
    }

    public boolean getPartieCommencee() {
        return partieCommencee;
    }

}
