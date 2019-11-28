package ca.qc.bdeb.info202.Elements;

import ca.qc.bdeb.info202.Gestion.Race;
import java.io.Serializable;

public class Normal extends Element implements Serializable{

    private int dmg;
    private boolean air;
    private boolean ground;

    public Normal(String name, Race race, int hp, int def, int dmg, int prixProd, boolean air, boolean ground) {
        super.setName(name);
        super.setRace(race);
        super.setHp(hp);
        super.setDef(def);
        this.dmg = dmg;
        super.setPrixProdMIN(prixProd);
        this.air = air;
        this.ground = ground;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
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
                +"\nDMG: \t" + getDmg() + "\n"
                + "Air: \t" + isAir() +"\tGRND: \t" + isGround() + "\n";
    }
}
