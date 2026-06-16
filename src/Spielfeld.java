import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Spielfeld {
    public static final int GROESSE = 10;

    private final List<Schiff> schiffe = new ArrayList<>();
    private final Set<Koordinate> beschosseneFelder = new HashSet<>();

    public boolean kannPlatzieren(List<Koordinate> felder) {
        for (Koordinate k : felder) {
            if (!imFeld(k)) {
                return false;
            }
            for (Schiff schiff : schiffe) {
                if (schiff.belegt(k)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void platziere(Schiff schiff) {
        schiffe.add(schiff);
    }

    public Schussergebnis beschiesse(Koordinate k) {
        beschosseneFelder.add(k);
        for (Schiff schiff : schiffe) {
            if (schiff.beschiessen(k)) {
                return schiff.istVersenkt()
                        ? Schussergebnis.VERSENKT
                        : Schussergebnis.TREFFER;
            }
        }
        return Schussergebnis.DANEBEN;
    }

    public boolean alleSchiffeVersenkt() {
        for (Schiff schiff : schiffe) {
            if (!schiff.istVersenkt()) {
                return false;
            }
        }
        return true;
    }

    public boolean wurdeBeschossen(Koordinate k) {
        return beschosseneFelder.contains(k);
    }

    public boolean hatSchiff(Koordinate k) {
        for (Schiff schiff : schiffe) {
            if (schiff.belegt(k)) {
                return true;
            }
        }
        return false;
    }

    private boolean imFeld(Koordinate k) {
        return k.zeile() >= 0 && k.zeile() < GROESSE
                && k.spalte() >= 0 && k.spalte() < GROESSE;
    }
}