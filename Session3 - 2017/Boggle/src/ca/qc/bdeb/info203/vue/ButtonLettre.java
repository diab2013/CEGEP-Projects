package ca.qc.bdeb.info203.vue;

import ca.qc.bded.info203.modele.Modele;
import java.awt.Font;
import javax.swing.JButton;

public class ButtonLettre extends JButton {
//Modele

    private final Modele modele;

//Caractéristique unique d'un buttonLettre
    private boolean clicked = false;
    private final int posX;
    private final int posY;

    /**
     * Constructeur de la class TopPanel
     *
     * @param modele Le modele unique passé à la méthode
     * @param i Pos X dans le tableau
     * @param j Pos Y dans le tableau
     */
    public ButtonLettre(Modele modele, int i, int j) {
        this.modele = modele;

        posX = i;
        posY = j;

        setText(modele.lettreGen());
        setFont(new Font("Dialog", Font.PLAIN, 30));
    }

    /**
     * Setter pour savoir si le button à été cliqué
     *
     * @param clicked Paramètre si cliqué (true/false)
     */
    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    /**
     * Getter pour la variable "clicked"
     * @return La valeur de la variable "clicked" (true/false)
     */
    public boolean isClicked() {
        return clicked;
    }

    /**
     * Getter pour la variable "posX"
     * @return La valeur de la variable posX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Getter pour la variable "posY"
     * @return La valeur de la variable posY
     */
    public int getPosY() {
        return posY;
    }

}
