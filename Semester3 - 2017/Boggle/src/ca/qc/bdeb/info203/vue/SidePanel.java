package ca.qc.bdeb.info203.vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SidePanel extends JPanel{
//Pour le fun et la beauté
    private final Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    
//Panel ainsi que les 2 buttons, vérification et reinitialisation
    private final JPanel pnlAction = new JPanel();
    private final JButton btnVerifier = new JButton("Check");
    private final JButton btnReset = new JButton("Reset");

//TextArea où le mot que le joueur fait va apparaître
    private final JTextField txtMotEcrit = new JTextField();

//List de mots utilisés
    private final DefaultListModel listModel = new DefaultListModel();
    private final JList list = new JList(listModel);
    private final JScrollPane srcPane = new JScrollPane(list);
    
    /**
     * Constructeur de la class SidePanel
     */
    public SidePanel() {
        setLayout(new BorderLayout());
        setBorder(loweredbevel);

        txtMotEcrit.setPreferredSize(new Dimension(10, 30));
        txtMotEcrit.setEditable(false);
        txtMotEcrit.setFont(new Font("Dialog", Font.PLAIN, 24));
        
        srcPane.setPreferredSize(new Dimension(100, 100));
        list.setFont(new Font("Dialog", Font.BOLD, 18));

        pnlAction.add(btnVerifier);
        pnlAction.add(btnReset);

        add(txtMotEcrit, BorderLayout.NORTH);
        add(srcPane, BorderLayout.CENTER);
        add(pnlAction, BorderLayout.SOUTH);
    }
    
    /**
     * Ajoute le mod du TextField à la List de mot utilisé
     */
    public void addMot(){
        listModel.addElement(txtMotEcrit.getText());
    }

    /**
     * Getter pour le button vérifier
     * @return Le button vérifier
     */
    public JButton getBtnVerifier() {
        return btnVerifier;
    }

    /**
     * Getter pour le button reset
     * @return Le button reset
     */
    public JButton getBtnReset() {
        return btnReset;
    }

    /**
     * Getter pour le text field
     * @return Le text field
     */
    public JTextField getTxtMotEcrit() {
        return txtMotEcrit;
    }

    /**
     * Getter pour le model de la liste
     * @return Le model de la liste
     */
    public DefaultListModel getListModel() {
        return listModel;
    }

}
