package ca.qc.bdeb.inf202.h17tp1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

public class FermeTest {

    private Ferme ferme;

    public FermeTest() {
    }
    
    @Before
    public void setUp() {
        ferme = new Ferme();
    }
  
    @Test
    public void uneNouvelleFermeNAPasDePlants() {
        String[][] attendu = {{" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "}};

        matricesEgales(attendu, ferme.getPhoto());
    }

    @Test
    public void uneNouvelleFermeAUneEncaisseDeDepart() {
        assertEquals("La ferme n'a pas 100$ en encaisse de d�part.", 100.0, ferme.getEncaisse(), 0.1);
    }

    @Test
    public void uneNouvelleFermeNEstPasIrriguee() {
        assertEquals("L'irrigation initiale est incorrecte.", 0.0, ferme.getHumiditeMoyenne(), 0.1);
    }

    @Test
    public void uneNouvelleFermeNEstPasFertilisee() {
        assertEquals("La fertilit� initiale est incorrecte.", 0.0, ferme.getFertiliteMoyenne(), 0.1);
    }

    @Test
    public void uneNouvelleFermeNAPasDeSemences() {
        assertEquals("Le stock initial de semences de tomate est incorrect.", 0.0, ferme.getStockSemencesTomate(), 0.1);
        assertEquals("Le stock initial de semences de concombre est incorrect.", 0.0, ferme.getStockSemencesConcombre(), 0.1);
    }

    @Test
    public void leCoutDesSemencesEstDeduitDeLEncaisse() {
        double argentAvant = ferme.getEncaisse();
        double coutSemences = ferme.acheterSemences(Semences.TOMATE, 1);
        double argentApres = ferme.getEncaisse();

        assertTrue("Cout des semences non cumul�", coutSemences > 0);
        assertEquals("Cout des semences mal d�duit de l'encaisse.", argentAvant - coutSemences, argentApres, 0.1);
    }

    @Test
    public void planterUneSemenceDonneUnPlant() {
        ferme.acheterSemences(Semences.TOMATE, 5);
        ferme.planter(Semences.TOMATE, 1);
        String[][] attendu = {{"t", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "}};

        matricesEgales(attendu, ferme.getPhoto());
    }

    @Test
    public void unPlantEnCroissanceDevientMur() {
        ferme.acheterSemences(Semences.TOMATE, 1);
        ferme.planter(Semences.TOMATE, 1);

        for (int dureeCroissance = 1; dureeCroissance <= 5; dureeCroissance++) {
            ferme.prochaineJournee();
        }

        String[][] attendu = {{"T", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "}};

        matricesEgales(attendu, ferme.getPhoto());
    }

    @Test
    public void unPlantMurNonRecoltePourrit() {
        ferme.acheterSemences(Semences.TOMATE, 1);
        ferme.planter(Semences.TOMATE, 1);

        for (int dureeCroissance = 1; dureeCroissance <= 7; dureeCroissance++) {
            ferme.prochaineJournee();
        }

        String[][] attendu = {{"~", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "}};

        matricesEgales(attendu, ferme.getPhoto());
    }

    @Test
    public void unPlantPourriSeDecompose() {
        ferme.acheterSemences(Semences.TOMATE, 1);
        ferme.planter(Semences.TOMATE, 1);

        for (int dureeCroissance = 1; dureeCroissance <= 8; dureeCroissance++) {
            ferme.prochaineJournee();
        }

        String[][] attendu = {{" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "},
                              {" ", " ", " ", " ", " "}};

        matricesEgales(attendu, ferme.getPhoto());
    }

    @Test
    public void irriguerUnChampIrrigueToutesSesParcelles() {
        double humiditeAvant = ferme.getHumiditeMoyenne();
        ferme.arroser();

        assertEquals("Probl�me d'irrigation des parcelles.", humiditeAvant + 1, ferme.getHumiditeMoyenne(), 0.1);
    }

    @Test
    public void fertiliserUnChampFertiliseToutesSesParcelles() {
        double fertiliteAvant = ferme.getFertiliteMoyenne();
        ferme.fertiliser();

        assertEquals("Probl�me de fertilisation des parcelles.", fertiliteAvant + 1, ferme.getFertiliteMoyenne(), 0.1);
    }

    private void matricesEgales(String[][] attendu, String[][] obtenu) {
        String contenuAttendu = "";
        for (String[] rangee : attendu) {
            contenuAttendu += "\n" + Arrays.toString(rangee);
        }

        String contenuObtenu = "";
        for (String[] rangee : obtenu) {
            contenuObtenu += "\n" + Arrays.toString(rangee);
        }

        assertEquals(contenuAttendu + "\n", contenuObtenu + "\n");
    }
}
