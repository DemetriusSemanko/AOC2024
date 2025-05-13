import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Purpose: starter code for AdventOfCode
 * Available objects BufferedReader read, Scanner scan that have access to the
 * file specified when running.
 */
public class Day10 extends AbstractAOC {
    private ArrayList<String> topoMap = new ArrayList<>();
    private ArrayList<int[]> trailheads = new ArrayList<>();
    private ArrayList<String> nines = new ArrayList<>();
    private ArrayList<Trailhead> trailheads_O = new ArrayList<>();

    private class Trailhead {
        public int score = 0;

        public Trailhead(int size) {
            score = size;
        }

        public int getScore() {
            return score;
        }
    }

    public Day10(String filename) throws FileNotFoundException {
        super(filename);
    }

    public String part1() {
        // System.out.println();

        while (scan.hasNextLine()) {
            topoMap.add(scan.nextLine());
        }

        int y = 0;
        for (String line : topoMap) {
            int x = 0;
            // System.out.println(line);
            for (int i = 0; i < line.length(); ++i) {
                if (line.substring(i, i + 1).equals("0")) {
                    int[] coords = {x, y};
                    trailheads.add(coords);
                }
                ++x;
            }
            ++y;
        }

        for (int[] trailhead : trailheads) {
            findPath(trailhead[0], trailhead[1], "-1");
            trailheads_O.add(new Trailhead(nines.size()));
            nines.clear();
        }

        int total = 0;
        for (Trailhead trailhead : trailheads_O) {
            total += trailhead.getScore();
        }
        
        // 1186 -> too high -> WAS ACTUALLY THE ANSWER TO PART2() :-)
        // 535 -> CORRECT
        return "" + total;
    }
    /*
     * Paths on the trail can only go North, East, South, and West.
     * Each step in the trail can only increase by 1 topographic value at a time.
     * Must find the score of each trailhead, then add those scores.
     */
    private boolean findPath(int x, int y, String previousValue) {
        String nextValue = null;
        try {
            nextValue = topoMap.get(y).substring(x, x + 1);
            //System.out.println(nextValue);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        // System.out.println("PREV: " + previousValue + ", CURRENT: " + nextValue + " at X: " + x + " Y: " + y);

        if (Integer.parseInt(previousValue) - Integer.parseInt(nextValue) != -1) {
            return false;
        }
        
        if (nextValue.equals("9")) {
            // Add the coordinates
            String nine = x + "" + y;
            if (!nines.contains(nine)) {
                nines.add(nine);
            }
            return true;
        }

        // Check North
        findPath(x, y - 1, nextValue);

        // Check East
        findPath(x + 1, y, nextValue);

        // Check South
        findPath(x, y + 1, nextValue);

        // Check West
        findPath(x - 1, y, nextValue);

        return false;
    }

    public String part2() {

        return "";
    }

    public static void main(String[] args) {
        Class<? extends AbstractAOC> myClass = Day10.class;
        String inputFilename = "Day10.input";
        try {
            run(myClass, inputFilename); // don't ask for a filename
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
