import java.util.HashSet;
import java.util.Set;

public class Schiff {
    private int[][] positions;
    private Set<String> hits;

    public Schiff(int length, int startX, int startY, boolean horizontal) {
        positions = new int[length][2];
        hits = new HashSet<>();
        for (int i = 0; i < length; i++) {
            positions[i][0] = horizontal ? startX + i : startX; // x
            positions[i][1] = horizontal ? startY : startY + i; // y
        }
    }

    public boolean isHit(int guessX, int guessY) {
        for (int[] pos : positions) {
            if (pos[0] == guessX && pos[1] == guessY) {
                hits.add(guessX + "," + guessY);
                return true;
            }
        }
        return false;
    }

    public boolean isSunk() {
        for (int[] pos : positions) {
            if (!hits.contains(pos[0] + "," + pos[1])) {
                return false; // Ein Feld ist noch nicht getroffen
            }
        }
        return true; // Alle Felder sind getroffen
    }

    public int[][] getPositions() {
        return positions;
    }
}

