import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ComputerSpieler extends Spieler {
    private final Random random = new Random();
    private final Set<Koordinate> bereitsBeschossen = new HashSet<>();

    @Override
    protected List<Koordinate> waehlePlatzierung(int laenge) {
        Koordinate start = new Koordinate(
                random.nextInt(Spielfeld.GROESSE),
                random.nextInt(Spielfeld.GROESSE));
        Ausrichtung ausrichtung = random.nextBoolean()
                ? Ausrichtung.HORIZONTAL
                : Ausrichtung.VERTIKAL;
        return ausrichtung.felder(start, laenge);
    }

    @Override
    public Koordinate naechsterSchuss() {
        Koordinate ziel;
        do {
            int zeile = random.nextInt(Spielfeld.GROESSE);
            int spalte = random.nextInt(Spielfeld.GROESSE);
            ziel = new Koordinate(zeile, spalte);
        } while (bereitsBeschossen.contains(ziel));
        bereitsBeschossen.add(ziel);
        return ziel;
    }
}