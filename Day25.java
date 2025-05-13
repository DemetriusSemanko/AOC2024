import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Purpose: starter code for AdventOfCode
 * Available objects BufferedReader read, Scanner scan that have access to the
 * file specified when running.
 */
public class Day25 extends AbstractAOC {
    private final int SCHEM_HEIGHT = 7;
    private ArrayList<Schematic> keys = new ArrayList<>();
    private ArrayList<Schematic> locks = new ArrayList<>();
    private final String EMPTY = ".";
    private final String FILL = "#";

    private class Schematic {
        // locks top row filled
        // keys bottom row filled
        public int[] heights = new int[5];
        public boolean isLock;

        public Schematic(int[] heights, boolean isLock) {
            this.heights = heights;
            this.isLock = isLock;
        }

        public String toString() {
            String isLock = "KEY ";
            if (this.isLock) {
                isLock = "LOCK ";
            }

            for (int i = 0; i < heights.length; ++i) {
                isLock += heights[i];
                if (i < heights.length - 1) {
                    isLock += ",";
                }
            }

            return isLock;
        }
    }

    public Day25(String filename) throws FileNotFoundException {
        super(filename);
    }

    public String part1() {
        int i = 0;
        String[] schem = new String[7];
        while (scan.hasNextLine()) {
            // Grab the line
            String line = scan.nextLine();

            // Here, we are "adding" the lines to our String[]; this is our unweildy Schematic
            schem[i] = line;

            ++i;
            if (i == SCHEM_HEIGHT) { // if we have read the last line of the schematic
                // Turn the String[] into a Schematic
                
                // First, let's determine if the schematic is a lock or a key
                // Declare our isLock to false, and we only check if it IS a lock
                boolean isLock = false;
                // We check if it IS a lock, the schematic for a lock must have NO '.' in the first line

                if (schem[0].contains(EMPTY)) {
                    isLock = true;
                }


                int[] heights = new int[5];

                for (String schemLine : schem) {
                    for (int j = 0; j < schemLine.length(); ++j) {
                        if (schemLine.substring(j, j + 1).equals(FILL)) {
                            heights[j]++;
                        }
                    }
                }
                for (int j = 0; j < heights.length; ++j) {
                    if (heights[j] > 0) {
                        heights[j]--;
                    }
                }


                // Add that schematic
                Schematic schematic = new Schematic(heights, isLock);
                if (isLock) {
                    locks.add(schematic);
                } else {
                    keys.add(schematic);
                }
                // Reset our counter to 0
                i = 0;
            }
        }

        int total = 0;
        int j = 0;
        System.out.println();
        while (j < locks.size()) {
            int k = 0;
            while (k < keys.size()) {
                boolean innerDone = false;
                int m = 0;
                while (m < keys.get(k).heights.length && !innerDone) {
                    if (locks.get(j).heights[m] + keys.get(k).heights[m] > 5) {
                        innerDone = true;
                    }
                    ++m;
                }
                if (!innerDone) {
                    System.out.println(locks.get(j) + " and " + keys.get(k));
                    ++total;
                }
                ++k;
            }
            ++j;
        }

        // 412 -> TOO LOW
        return "" + total;
    }

    public String part2() {

        return "";
    }

    public static void main(String[] args) {
        Class<? extends AbstractAOC> myClass = Day25.class;
        String inputFilename = "Day25_1.input";
        try {
            run(myClass, inputFilename); // don't ask for a filename
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
