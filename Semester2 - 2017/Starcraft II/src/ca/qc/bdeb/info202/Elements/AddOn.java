package ca.qc.bdeb.info202.Elements;

import ca.qc.bdeb.info202.Gestion.Race;
import java.io.Serializable;

public class AddOn extends Element implements Serializable {

    public AddOn(String name, Race race, int hp, int def, int prixProdMin) {
        super.setName(name);
        super.setRace(race);
        super.setHp(hp);
        super.setDef(def);
        super.setPrixProdMIN(prixProdMin);
    }

    @Override
    public String toString() {
        return super.toString() + "\n";
    }

}
