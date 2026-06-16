import java.util.ArrayList;
import java.util.List;

public enum Ausrichtung {
    HORIZONTAL,
    VERTIKAL;

    public List<Koordinate> felder(Koordinate start, int laenge) {
        List<Koordinate> felder = new ArrayList<>();
        for (int i = 0; i < laenge; i++) {
            int zeile = this == VERTIKAL ? start.zeile() + i : start.zeile();
            int spalte = this == HORIZONTAL ? start.spalte() + i : start.spalte();
            felder.add(new Koordinate(zeile, spalte));
        }
        return felder;
    }
}