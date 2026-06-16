import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Schiff {
    private final List<Koordinate> felder;
    private final Set<Koordinate> treffer = new HashSet<>();

    public Schiff(List<Koordinate> felder) {
        this.felder = felder;
    }

    public boolean belegt(Koordinate k) {
        return felder.contains(k);
    }

    public boolean beschiessen(Koordinate k) {
        if (belegt(k)) {
            treffer.add(k);
            return true;
        }
        return false;
    }

    public boolean istVersenkt() {
        return treffer.size() == felder.size();
    }
}