import java.util.List;

public abstract class Spieler {
    protected static final int[] SCHIFFSLAENGEN = {2, 3, 4, 5};

    protected final Spielfeld eigenesFeld = new Spielfeld();

    public void platziereSchiffe() {
        for (int laenge : SCHIFFSLAENGEN) {
            boolean platziert = false;
            while (!platziert) {
                List<Koordinate> felder = waehlePlatzierung(laenge);
                if (eigenesFeld.kannPlatzieren(felder)) {
                    eigenesFeld.platziere(new Schiff(felder));
                    platziert = true;
                }
            }
        }
    }

    protected abstract List<Koordinate> waehlePlatzierung(int laenge);

    public abstract Koordinate naechsterSchuss();

    public Spielfeld getEigenesFeld() {
        return eigenesFeld;
    }
}