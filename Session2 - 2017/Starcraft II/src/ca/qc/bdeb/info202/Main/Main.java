package ca.qc.bdeb.info202.Main;
import ca.qc.bdeb.info202.Gestion.GestionStarcraft;

/**
 * @author Radu Baltac & Diab Khanafer
 */

public class Main {
    
    public static void main(String [] args){
        GestionStarcraft gestion = new GestionStarcraft();
        gestion.brain();
    }
}