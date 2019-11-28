package ca.qc.bdeb.info203.vue;

import ca.qc.bded.info203.modele.Modele;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

class TopPanel extends JPanel implements Observer {
//Modele
    private final Modele modele;

//Labels et border pour être fancy
    private final Border loweredbevel = BorderFactory.createLineBorder(Color.BLACK, 2);
    private final JLabel lblTime = new JLabel("3 : 00", JLabel.CENTER);
    private final JLabel lblScore = new JLabel("Score = 0", JLabel.CENTER);

    /**
     * Constructeur de la class TopPanel
     * @param modele Le modele unique passé à la méthode
     */
    public TopPanel(Modele modele) {
        this.modele = modele;
        modele.addObserver(this);

        setBorder(loweredbevel);
        setLayout(new GridLayout(1, 2));
        add(lblTime);
        add(lblScore);
        lblScore.setFont(new Font("Dialog", Font.PLAIN, 24));
        lblTime.setFont(new Font("Dialog", Font.PLAIN, 24));
    }

    /**
     * Dit à la class Observer de ce update lorsqu'elle est appelée
     * @param o Object observable (ici, se sera la classe modele)
     * @param arg Aucune idée xD
     */
    @Override
    public void update(Observable o, Object o1) {
        switch (modele.getStatus()) {
            case NEW:
                lblTime.setText("3 : 00");
                lblScore.setText("Score = " + modele.getPoints());
                break;
            case TIMER:
                if (modele.getTimeSecondes() <= 9) {
                    lblTime.setText(modele.getTimeMinutes() + " : 0" + modele.getTimeSecondes());
                } else {
                    lblTime.setText(modele.getTimeMinutes() + " : " + modele.getTimeSecondes());
                }
                lblScore.setText("Score = " + modele.getPoints());
        }
    }

}
