public class KonsoleUI {

    private final Spiel spiel;
    private final Spieler mensch;
    private final Spieler computer;

    public KonsoleUI(Spiel spiel, Spieler mensch, Spieler computer) {
        this.spiel = spiel;
        this.mensch = mensch;
        this.computer = computer;
    }

    public void spielen() {
        System.out.println("=== Schiffe versenken ===");
        System.out.println("Platziere zuerst deine Schiffe.\n");
        spiel.aufbauen();

        while (!spiel.istVorbei()) {
            zeigeFeld("Gegnerisches Feld (deine Schüsse):", computer.getEigenesFeld(), false);
            zeigeFeld("Dein Feld:", mensch.getEigenesFeld(), true);
            Zugergebnis z = spiel.fuehreZugAus();
            zeigeErgebnis(z);
        }

        if (spiel.getGewinner() == mensch) {
            System.out.println("\nGlückwunsch, du hast gewonnen!");
        } else {
            System.out.println("\nDer Computer hat gewonnen.");
        }
    }

    private void zeigeErgebnis(Zugergebnis z) {
        String wer = (z.schuetze() == mensch) ? "Du" : "Computer";
        String ergebnis = switch (z.ergebnis()) {
            case DANEBEN -> "daneben";
            case TREFFER -> "Treffer!";
            case VERSENKT -> "Treffer – Schiff versenkt!";
        };
        System.out.println(wer + " auf " + koordinateAlsText(z.ziel()) + ": " + ergebnis + "\n");
    }

    private void zeigeFeld(String titel, Spielfeld feld, boolean schiffeZeigen) {
        System.out.println(titel);
        System.out.print("   ");
        for (int s = 0; s < Spielfeld.GROESSE; s++) {
            System.out.print((char) ('A' + s) + " ");
        }
        System.out.println();
        for (int z = 0; z < Spielfeld.GROESSE; z++) {
            System.out.printf("%2d ", z + 1);
            for (int s = 0; s < Spielfeld.GROESSE; s++) {
                System.out.print(zeichenFuer(feld, new Koordinate(z, s), schiffeZeigen) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private char zeichenFuer(Spielfeld feld, Koordinate k, boolean schiffeZeigen) {
        if (feld.wurdeBeschossen(k)) {
            return feld.hatSchiff(k) ? 'X' : 'O';
        }
        if (schiffeZeigen && feld.hatSchiff(k)) {
            return 'S';
        }
        return '~';
    }

    private String koordinateAlsText(Koordinate k) {
        return "" + (char) ('A' + k.spalte()) + (k.zeile() + 1);
    }

    public static void main(String[] args) {
        Eingabe eingabe = new KonsolenEingabe();
        Spieler mensch = new MenschlicherSpieler(eingabe);
        Spieler computer = new ComputerSpieler();
        Spiel spiel = new Spiel(mensch, computer);
        new KonsoleUI(spiel, mensch, computer).spielen();
    }
}