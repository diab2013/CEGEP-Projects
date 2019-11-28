package ca.qc.bdeb.info203.vue;

import ca.qc.bded.info203.modele.Modele;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Boggle extends JFrame implements Observer {

//Modele
    private final Modele modele;

//Tab de buttons
    private ButtonLettre[][] tabLettre = new ButtonLettre[4][4];

//Panels
    private final JPanel pnlButtons = new JPanel(new GridLayout(4, 4));
    private final SidePanel pnlSidePanel = new SidePanel();

//Bar de menu
    private final MenuBarBoggle menuBarMain = new MenuBarBoggle();

    /**
     * Constructeur de la class Boggle
     *
     * @param modele Le modele unique passé à la méthode
     */
    public Boggle(Modele modele) {
        this.modele = modele;
        modele.addObserver(this);

        TopPanel pnlTimeScore = new TopPanel(modele);

        add(pnlSidePanel, BorderLayout.EAST);
        add(pnlButtons, BorderLayout.CENTER);
        add(pnlTimeScore, BorderLayout.NORTH);

        createButtons();
        createEvents(modele);

        setSize(800, 600);
        setTitle("Boggle");
        setIconImage(new ImageIcon("Icon.jpg").getImage());
        setJMenuBar(menuBarMain);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Création des buttons ainsi que leurs event
     */
    private void createButtons() {
        tabLettre = new ButtonLettre[modele.getRan()][modele.getCol()];
        for (int i = 0; i < tabLettre.length; i++) {
            for (int j = 0; j < tabLettre[i].length; j++) {
                ButtonLettre btnLettre = new ButtonLettre(modele, i, j);
                tabLettre[i][j] = btnLettre;
                pnlButtons.add(btnLettre);

                btnLettre.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        ButtonLettre source = (ButtonLettre) ae.getSource();
                        pnlSidePanel.getTxtMotEcrit().setText(pnlSidePanel.getTxtMotEcrit().getText() + source.getText());
                        btnLettre.setClicked(true);
                        source.setEnabled(false);
                        source.setBackground(Color.RED);
                        modele.checkAdj(btnLettre.getPosX(), btnLettre.getPosY());
                    }
                });
            }
        }
    }

    /**
     * Création des events pour les components qui ont en besoin
     *
     * @param modele Le modele unique passé à la méthode
     */
    private void createEvents(Modele modele) {
        pnlSidePanel.getBtnVerifier().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                modele.checkmot(pnlSidePanel.getTxtMotEcrit().getText());
                if (modele.isExiste() && !modele.isUsed()) {
                    pnlSidePanel.addMot();
                }
                resetButtons();
            }

        });
        pnlSidePanel.getBtnReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                resetButtons();
            }
        });
        menuBarMain.getMnuItemNouvellePartie().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (JOptionPane.showConfirmDialog(null, "Voulez-vous faire une nouvelle partie?", "New game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    modele.newGame();
                }
            }
        });
        menuBarMain.getMnuItem4x4().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modele.switchCase(4);
                modele.newGame();
            }
        });
        menuBarMain.getMnuItem5x5().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modele.switchCase(5);
                modele.newGame();
            }
        });
        menuBarMain.getMnuItem6x6().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modele.switchCase(6);
                modele.newGame();
            }
        });
        menuBarMain.getMnuItem7x7().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modele.switchCase(7);
                modele.newGame();
            }
        });
        menuBarMain.getMnuItemPropos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(Boggle.this, "Diab Khanafer || 20 septembre 2017 - 15 octobre 2017", "RIP Monster Killer", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuBarMain.getMnuItemQuitter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (JOptionPane.showConfirmDialog(Boggle.this, "voulez-vous vraiment quitter?", "Quitter", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Réinitialise le jeu à son état initial
     */
    private void newGame() {
        pnlSidePanel.getListModel().clear();
        pnlSidePanel.getTxtMotEcrit().setText("");
        pnlButtons.removeAll();
        pnlButtons.setLayout(new GridLayout(modele.getRan(), modele.getCol()));
        createButtons();
        pnlButtons.revalidate();
        pnlButtons.repaint();
    }

    /**
     * Disable tout les buttons et donne l'option de recommencer ou de quitter
     */
    public void endGame() {
        disableButtons();
        JOptionPane pane = new JOptionPane("Le temps est écoulé. Voulez-vous recommencer?", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog dialog = pane.createDialog(Boggle.this, "End Game");
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
        int answer = (int) pane.getValue();
        switch (answer) {
            case JOptionPane.YES_OPTION:
                modele.newGame();
                break;
            default:
                System.exit(0);
        }
    }

    /**
     * Disable tout les buttons
     */
    private void disableButtons() {
        for (int i = 0; i < tabLettre.length; i++) {
            for (int j = 0; j < tabLettre[i].length; j++) {
                tabLettre[i][j].setEnabled(false);
                tabLettre[i][j].setBackground(null);
                if (tabLettre[i][j].isClicked()) {
                    tabLettre[i][j].setBackground(Color.red);
                }
            }
        }
    }

    /**
     * Reset tout les buttons à leur état initial
     */
    private void resetButtons() {
        pnlSidePanel.getTxtMotEcrit().setText("");
        modele.resetClicked();
        modele.resetAdjacent();
        for (int i = 0; i < tabLettre.length; i++) {
            for (int j = 0; j < tabLettre[i].length; j++) {
                tabLettre[i][j].setEnabled(true);
                tabLettre[i][j].setBackground(null);
                tabLettre[i][j].setClicked(false);
            }
        }
    }

    /**
     * Mark tout les buttons adjacents au button cliqué
     */
    public void markAdjacent() {
        disableButtons();
        for (int i = 0; i < tabLettre.length; i++) {
            for (int j = 0; j < tabLettre[i].length; j++) {
                if (modele.getTabAdjacent(i, j) && !tabLettre[i][j].isClicked()) {
                    tabLettre[i][j].setEnabled(true);
                    tabLettre[i][j].setBackground(Color.GREEN);
                }
            }
        }
        modele.resetAdjacent();
    }

    /**
     * Dit à la class Observer de ce update lorsqu'elle est appelée
     *
     * @param o Object observable (ici, se sera la classe modele)
     * @param arg Aucune idée xD
     */
    @Override
    public void update(Observable o, Object arg) {
        switch (modele.getStatus()) {
            case NEW:
                newGame();
                break;
            case ADJCT:
                markAdjacent();
                break;
            case END:
                endGame();
                break;
        }
    }

}
