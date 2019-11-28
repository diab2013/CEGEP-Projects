package ca.qc.bdeb.info202.Elements;

import ca.qc.bdeb.info202.Gestion.Race;
import java.io.Serializable;

public class Energy extends Element implements Serializable {

    private int prixProdNRG;
    private int dmg;
    private int nrg;
    private int shield;
    private boolean air;
    private boolean ground;

    public Energy(String name, Race race, int hp, int def, int dmg, int nrg, int shield,
            int prixProdMin, int prixProdNRG, boolean air, boolean ground) {
        super.setName(name);
        super.setRace(race);
        super.setHp(hp);
        super.setDef(def);
        this.dmg = dmg;
        this.nrg = nrg;
        this.shield = shield;
        super.setPrixProdMIN(prixProdMin);
        this.prixProdNRG = prixProdNRG;
        this.air = air;
        this.ground = ground;
    }

    public int getPrixProdNRG() {
        return prixProdNRG;
    }

    public void setPrixProdNRG(int prixProdNRG) {
        this.prixProdNRG = prixProdNRG;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getNrg() {
        return nrg;
    }

    public void setNrg(int nrg) {
        this.nrg = nrg;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public boolean isAir() {
        return air;
    }

    public void setAir(boolean air) {
        this.air = air;
    }

    public boolean isGround() {
        return ground;
    }

    public void setGround(boolean ground) {
        this.ground = ground;
    }

    @Override
    public String toString() {
        return super.toString()
                +" / " +getPrixProdNRG() + " NRG\n"
                + "Shield: " + getShield() + "\n"
                + "DMG: \t" + getDmg() + "\tNRG: \t" + getNrg() + "\n"
                + "Air: \t" + isAir() + "\tGRND: \t" + isGround() + "\n";
    }
}
