import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Rulez {
    private List<Schiff> cpuShips;
    private int size;

    public Rulez(int size) {
        this.size = size;
        cpuShips = new ArrayList<>();
        placeCpuShips();
    }

    private void placeCpuShips() {
        int[] shipSizes = {2, 3, 4, 5};
        Random rand = new Random();

        for (int size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                boolean horizontal = rand.nextBoolean();
                int startX = horizontal ? rand.nextInt(this.size - size + 1) : rand.nextInt(this.size);
                int startY = horizontal ? rand.nextInt(this.size) : rand.nextInt(this.size - size + 1);

                Schiff schiff = new Schiff(size, startX, startY, horizontal);
                if (canPlaceShip(schiff)) {
                    cpuShips.add(schiff);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(Schiff schiff) {
        for (int[] pos : schiff.getPositions()) {
            for (Schiff existingShip : cpuShips) {
                for (int[] existingPos : existingShip.getPositions()) {
                    if (pos[0] == existingPos[0] && pos[1] == existingPos[1]) {
                        return false; // Überlappt mit einem anderen Schiff
                    }
                }
            }
        }
        return true;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean allSunk = false;

        System.out.println("Willkommen zu Schiffe Versenken! Versuchen Sie, alle Schiffe der CPU zu versenken.");

        while (!allSunk) {
            System.out.print("Geben Sie Ihre Vermutung ein (x y): ");
            int guessX = scanner.nextInt();
            int guessY = scanner.nextInt();

            if (guessX < 0 || guessX >= size || guessY < 0 || guessY >= size) {
                System.out.println("Ungültige Koordinaten. Versuchen Sie es erneut.");
                continue;
            }

            boolean hit = false;
            for (Schiff schiff : cpuShips) {
                if (schiff.isHit(guessX, guessY)) {
                    hit = true;
                    System.out.println("Treffer!");
                    if (schiff.isSunk()) {
                        System.out.println("Sie haben ein Schiff versenkt!");
                    }
                    break;
                }
            }

            if (!hit) {
                System.out.println("Leider kein Treffer.");
            }

            allSunk = cpuShips.stream().allMatch(Schiff::isSunk);
        }

        System.out.println("Herzlichen Glückwunsch! Sie haben alle Schiffe der CPU versenkt.");
        scanner.close();
    }
}

