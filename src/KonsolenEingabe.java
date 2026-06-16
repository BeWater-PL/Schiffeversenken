import java.util.Scanner;

public class KonsolenEingabe implements Eingabe {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public Koordinate frageKoordinate(String aufforderung) {
        while (true) {
            System.out.print(aufforderung + " (z.B. A5): ");
            Koordinate k = parse(scanner.nextLine().trim().toUpperCase());
            if (k != null) {
                return k;
            }
            System.out.println("Ungültige Eingabe, bitte nochmal.");
        }
    }

    @Override
    public Ausrichtung frageAusrichtung() {
        while (true) {
            System.out.print("Ausrichtung – (h)orizontal oder (v)ertikal? ");
            String eingabe = scanner.nextLine().trim().toLowerCase();
            if (eingabe.startsWith("h")) {
                return Ausrichtung.HORIZONTAL;
            }
            if (eingabe.startsWith("v")) {
                return Ausrichtung.VERTIKAL;
            }
            System.out.println("Bitte h oder v eingeben.");
        }
    }

    private Koordinate parse(String eingabe) {
        if (eingabe.length() < 2) {
            return null;
        }
        int spalte = eingabe.charAt(0) - 'A';
        try {
            int zeile = Integer.parseInt(eingabe.substring(1)) - 1;
            if (imBereich(zeile) && imBereich(spalte)) {
                return new Koordinate(zeile, spalte);
            }
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean imBereich(int wert) {
        return wert >= 0 && wert < Spielfeld.GROESSE;
    }
}