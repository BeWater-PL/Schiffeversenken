import java.util.List;

public class MenschlicherSpieler extends Spieler {
    private final Eingabe eingabe;

    public MenschlicherSpieler(Eingabe eingabe) {
        this.eingabe = eingabe;
    }

    @Override
    protected List<Koordinate> waehlePlatzierung(int laenge) {
        Koordinate start = eingabe.frageKoordinate(
                "Startposition für Schiff der Länge " + laenge);
        Ausrichtung ausrichtung = eingabe.frageAusrichtung();
        return ausrichtung.felder(start, laenge);
    }

    @Override
    public Koordinate naechsterSchuss() {
        return eingabe.frageKoordinate("Wohin schießt du?");
    }
}