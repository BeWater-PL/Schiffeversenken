import java.util.Scanner;
import java.util.Random;

public class SchiffeVersenken {
    static char[][] spielFeld = new char[10][10];
    static char leer = '~';
    static char schiff = 'S';
    static char treffer = 'X';
    static char wasser = 'O';

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static boolean superwaffeVerwendet = false;
    static int trefferInFolge = 0;
    static int rundenBisLuftangriff = -1; // -1 bedeutet Luftangriff nicht bereit
    static int rundenBisUntertauchen = 0; // Zähler für Untertauchen
    static boolean[] schiffeUntergetaucht = new boolean[4]; // Status der Schiffe
    static boolean[] versenkteSchiffe = new boolean[4]; // Status der versenkten Schiffe

    public static void main(String[] args) {
        starteSpiel();
        initialisiereFeld();
        platziereSchiffe(true);  // Spieler platziert seine Schiffe
        platziereSchiffe(false);  // Computer platziert seine Schiffe

        boolean spielAktiv = true;
        while (spielAktiv) {
            spielAktiv = spielZug(true); // Spieler Zug
            if (!spielAktiv) break;
            spielAktiv = spielZug(false); // Computer Zug
        }
        spielBeendet();
    }

    static void starteSpiel() {
        System.out.println(" __      __                     __                                                 ______             __                                __                      __                            __                  ");
        System.out.println("|  \\    /  \\                   |  \\                                               /      \\           |  \\                              |  \\                    |  \\                          |  \\                ");
        System.out.println(" \\$$\\  /  $$__    __   ______   \\$$ __    __   ______   __    __   ______        |  $$$$$$\\  _______ | $$____   __   __   __   ______   \\$$ _______    ______  | $$____    ______    _______ | $$   __   ______  ");
        System.out.println("  \\$$\\/  $$|  \\  |  \\ /      \\ |  \\|  \\  |  \\ |      \\ |  \\  |  \\ /      \\       | $$___\\$$ /       \\| $$    \\ |  \\ |  \\ |  \\ /      \\ |  \\|       \\  /      \\ | $$    \\  |      \\  /       \\| $$  /  \\ /      \\ ");
        System.out.println("   \\$$  $$ | $$  | $$|  $$$$$$\\| $$| $$  | $$  \\$$$$$$\\| $$  | $$|  $$$$$$\\       \\$$    \\ |  $$$$$$$| $$$$$$$\\| $$ | $$ | $$|  $$$$$$\\| $$| $$$$$$$\\|  $$$$$$\\| $$$$$$$\\  \\$$$$$$\\|  $$$$$$$| $$_/  $$|  $$$$$$\\ ");
        System.out.println("    \\$$$$  | $$  | $$| $$  | $$| $$| $$  | $$ /      $$| $$  | $$| $$    $$       _\\$$$$$$\\| $$      | $$  | $$| $$ | $$ | $$| $$    $$| $$| $$  | $$| $$    $$| $$  | $$ /      $$| $$      | $$   $$ | $$    $$ ");
        System.out.println("    | $$   | $$__/ $$| $$__/ $$| $$| $$__/ $$|  $$$$$$$| $$__/ $$| $$$$$$$$      |  \\__| $$| $$_____ | $$  | $$| $$_/ $$_/ $$| $$$$$$$$| $$| $$  | $$| $$$$$$$$| $$__/ $$|  $$$$$$$| $$_____ | $$$$$$\\ | $$$$$$$$ ");
        System.out.println("    | $$    \\$$    $$| $$    $$| $$ \\$$    $$ \\$$    $$ \\$$    $$ \\$$     \\       \\$$    $$ \\$$     \\| $$  | $$ \\$$   $$   $$ \\$$     \\| $$| $$  | $$ \\$$     \\| $$    $$ \\$$    $$ \\$$     \\| $$  \\$$\\ \\$$     \\ ");
        System.out.println("     \\$$     \\$$$$$$ | $$$$$$$  \\$$ _\\$$$$$$$  \\$$$$$$$ _\\$$$$$$$  \\$$$$$$$        \\$$$$$$   \\$$$$$$$ \\$$   \\$$  \\$$$$$\\$$$$   \\$$$$$$$ \\$$ \\$$   \\$$  \\$$$$$$$ \\$$$$$$$   \\$$$$$$$  \\$$$$$$$ \\$$   \\$$  \\$$$$$$$ ");
        System.out.println("                     | $$          |  \\__| $$          |  \\__| $$                                                                                                                                                 ");
        System.out.println("                     | $$           \\$$    $$           \\$$    $$                                                                                                                                                 ");
        System.out.println("                      \\$$            \\$$$$$$             \\$$$$$$                                                                                                                                                  ");
        System.out.println();
    }

    static void initialisiereFeld() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                spielFeld[i][j] = leer;
            }
        }
    }

    static void platziereSchiffe(boolean spieler) {
        int[] schiffLaengen = {2, 3, 4, 5};
        for (int laenge : schiffLaengen) {
            boolean platziert = false;
            while (!platziert) {
                if (spieler) {
                    System.out.println("Platziere dein Schiff der Länge " + laenge + ":");
                    System.out.print("Gebe Startposition (z.B. A5) ein: ");
                    String eingabe = scanner.nextLine();
                    int x = eingabe.charAt(1) - '0';
                    int y = eingabe.charAt(0) - 'A';
                    System.out.print("Horizontal (h) oder Vertikal (v)? ");
                    char ausrichtung = scanner.nextLine().charAt(0);
                    boolean horizontal = (ausrichtung == 'h');

                    if (kannSchiffPlatzieren(x, y, laenge, horizontal)) {
                        setzeSchiff(x, y, laenge, horizontal, schiff);
                        platziert = true;
                    } else {
                        System.out.println("Ungültige Position, bitte erneut versuchen.");
                    }
                } else {
                    // Computer platziert seine Schiffe
                    int x = random.nextInt(10);
                    int y = random.nextInt(10);
                    boolean horizontal = random.nextBoolean();
                    if (kannSchiffPlatzieren(x, y, laenge, horizontal)) {
                        setzeSchiff(x, y, laenge, horizontal, schiff);
                        platziert = true;
                    }
                }
            }
        }
    }

    static boolean kannSchiffPlatzieren(int x, int y, int laenge, boolean horizontal) {
        for (int i = 0; i < laenge; i++) {
            int newX = horizontal ? x : x + i;
            int newY = horizontal ? y + i : y;
            if (newX >= 10 || newY >= 10 || spielFeld[newX][newY] == schiff) {
                return false;
            }
        }
        return true;
    }

    static void setzeSchiff(int x, int y, int laenge, boolean horizontal, char schiffSymbol) {
        for (int i = 0; i < laenge; i++) {
            int newX = horizontal ? x : x + i;
            int newY = horizontal ? y + i : y;
            spielFeld[newX][newY] = schiffSymbol;
        }
    }

    static boolean spielZug(boolean spieler) {
        if (spieler) {
            System.out.println("Dein Zug! Gebe Koordinaten (z.B. A5) ein oder benutze die Superwaffe (SW) oder tauche unter (U):");
            String eingabe = scanner.nextLine();
            if (eingabe.equalsIgnoreCase("SW") && !superwaffeVerwendet) {
                superwaffeVerwendet = true;
                benutzeSuperwaffe();
            } else if (eingabe.equalsIgnoreCase("U") && rundenBisUntertauchen == 0) {
                untertauchen();
            } else {
                int x = eingabe.charAt(1) - '0';
                int y = eingabe.charAt(0) - 'A';
                if (schiessen(x, y)) {
                    System.out.println("BOOM! Treffer!");
                    trefferInFolge++;
                    if (checkVersenkt(x, y)) {
                        versenkteSchiffe[0] = true; // Markiere das Schiff als versenkt
                        System.out.println("BÄÄÄM! Schiff versenkt!");
                        schussAufZufallsfeld();
                    }
                } else {
                    System.out.println("BLUBB! Verfehlt.");
                    trefferInFolge = 0; // Reset bei einem Fehlschuss
                }


                zeigeSpielfeld();
                return true;
            }
        } else {
            // Computer-Zug
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            if (schiessen(x, y)) {
                System.out.println("CPU hat getroffen!");
                trefferInFolge++;
                if (checkVersenkt(x, y)) {
                    versenkteSchiffe[1] = true; // Markiere das Schiff als versenkt
                    System.out.println("BÄÄÄM! CPU hat ein Schiff versenkt!");
                    schussAufZufallsfeld();
                }
            } else {
                System.out.println("CPU hat verfehlt.");
                trefferInFolge = 0; // Reset bei einem Fehlschuss
            }
            zeigeSpielfeld();
            return true;
        }
        return false;
    }

    static void benutzeSuperwaffe() {
        System.out.println("Superwaffe wird eingesetzt! Alle Felder in einem 3x3 Bereich werden getroffen!");
        // Hier kann die Logik für die Superwaffe implementiert werden
    }

    static void untertauchen() {
        System.out.println("Welches Schiff möchtest du untertauchen? (1-4)");
        int schiffIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        if (!schiffeUntergetaucht[schiffIndex]) {
            schiffeUntergetaucht[schiffIndex] = true;
            rundenBisUntertauchen = 1; // Untertauchen dauert eine Runde
            System.out.println("Das Schiff ist jetzt untergetaucht!");
        } else {
            System.out.println("Dieses Schiff ist bereits untergetaucht.");
        }
    }

    static boolean schiessen(int x, int y) {
        if (spielFeld[x][y] == schiff) {
            spielFeld[x][y] = treffer;
            return true;
        } else {
            spielFeld[x][y] = wasser;
            return false;
        }
    }

    static boolean checkVersenkt(int x, int y) {
        // Hier kannst du die Logik implementieren, um zu prüfen, ob ein Schiff versenkt wurde.
        return true; // Placeholder für die Logik
    }

    static void schussAufZufallsfeld() {
        int x = random.nextInt(8);
        int y = random.nextInt(8);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (schiessen(x + i, y + j)) {
                    System.out.println("CPU hat zufällig ein 2x2-Feld getroffen!");
                }
            }
        }
    }

    static void zeigeSpielfeld() {
        System.out.println("Aktuelles Spielfeld:");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(spielFeld[i][j] + " ");
            }
            System.out.println();
        }
    }

    static boolean checkGameOver() {
        // Überprüfen, ob alle Schiffe versenkt sind.
        return false; // Placeholder für die Logik
    }

    static void spielBeendet() {
        System.out.println("Das Spiel ist beendet!");
        System.out.println(",---.,---.,-.-.,---.,---..    ,,---.,---.");
        System.out.println("|  _.|---|| | ||--- |   ||    ||--- |---'");
        System.out.println("|   ||   || | ||    |   | \\  / |    |  \\ ");
        System.out.println("`---'`   '` ' '`---'`---'  `'  `---'`   `");
    }
}
