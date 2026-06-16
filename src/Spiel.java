public class Spiel {
    private final Spieler spieler1;
    private final Spieler spieler2;
    private Spieler amZug;

    public Spiel(Spieler spieler1, Spieler spieler2) {
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
        this.amZug = spieler1;
    }

    public void aufbauen() {
        spieler1.platziereSchiffe();
        spieler2.platziereSchiffe();
    }

    public Zugergebnis fuehreZugAus() {
        Spieler schuetze = amZug;
        Spieler gegner = gegnerVon(amZug);
        Koordinate ziel = schuetze.naechsterSchuss();
        Schussergebnis ergebnis = gegner.getEigenesFeld().beschiesse(ziel);
        amZug = gegner;
        return new Zugergebnis(schuetze, ziel, ergebnis);
    }

    public boolean istVorbei() {
        return getGewinner() != null;
    }

    public Spieler getGewinner() {
        if (spieler2.getEigenesFeld().alleSchiffeVersenkt()) {
            return spieler1;
        }
        if (spieler1.getEigenesFeld().alleSchiffeVersenkt()) {
            return spieler2;
        }
        return null;
    }

    private Spieler gegnerVon(Spieler spieler) {
        return (spieler == spieler1) ? spieler2 : spieler1;
    }
}