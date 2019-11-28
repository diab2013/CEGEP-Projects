package ca.qc.bdeb.info3.tp2.vue;

/**
 * Interface des composantes bougeables.
 */
public interface Bougeable {

    /**
     * Enum des directions possibles pour toutes les composantes "bougeables".
     */
    public enum Direction {
        NORD, SUD, OUEST, EST, NORD_EST, NORD_OUEST, SUD_EST, SUD_OUEST;
    }

}
