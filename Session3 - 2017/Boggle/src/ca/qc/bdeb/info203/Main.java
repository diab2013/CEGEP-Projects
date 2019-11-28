package ca.qc.bdeb.info203;

import ca.qc.bdeb.info203.vue.Boggle;
import ca.qc.bded.info203.modele.Modele;

public class Main {

    public static void main(String[] args) {
        Modele modele = new Modele();
        Boggle game = new Boggle(modele);
    }

}
