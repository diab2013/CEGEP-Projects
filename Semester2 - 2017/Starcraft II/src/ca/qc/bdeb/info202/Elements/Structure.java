package ca.qc.bdeb.info202.Elements;

import ca.qc.bdeb.info202.Gestion.Race;
import java.io.Serializable;

public class Structure extends Element implements Serializable {

    private int prixProdGas;
    private boolean allowAddOn;

    public Structure(String name, Race race, int hp, int def, int prixProdMIN, int prixProdGas, boolean allowAddOn) {
        super.setName(name);
        super.setRace(race);
        super.setHp(hp);
        super.setDef(def);
        super.setPrixProdMIN(prixProdMIN);

        this.prixProdGas = prixProdGas;
        this.allowAddOn = allowAddOn;
    }

    public int getPrixProdGas() {
        return prixProdGas;
    }

    public void setPrixProdGas(int prixProdGas) {
        this.prixProdGas = prixProdGas;
    }

    public boolean isAllowAddOn() {
        return allowAddOn;
    }

    public void setAllowAddOn(boolean allowAddOn) {
        this.allowAddOn = allowAddOn;
    }

    @Override
    public String toString() {
        return super.toString()
                + " / " + getPrixProdGas() + " GAS\n"
                + "ADD: \t" + isAllowAddOn() + "\n";

    }

}
