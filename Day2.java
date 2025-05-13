import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *  Purpose: starter code for AdventOfCode
 *  Available objects BufferedReader read, Scanner scan that have access to the file specified when running.
 */
public class Day2 extends AbstractAOC {
    private ArrayList<int[]> unsafeReports = new ArrayList<>();
    private int totalSafe = 0;

    public Day2(String filename) throws FileNotFoundException {
	    super(filename);
    }

    public String part1() {
        int total = 0;
        while(scan.hasNextLine()) {
            String[] levelStr = scan.nextLine().split(" ");
            int[] levelInt = new int[levelStr.length];

            for(int i = 0; i < levelStr.length; ++i) {
                levelInt[i] = Integer.parseInt(levelStr[i]);
            }

            // Good up until this point

            if (isSafe(levelInt)) {
                ++total;
            } else {
                unsafeReports.add(levelInt);
            }
        }
        totalSafe = total;

	    return "" + totalSafe;
    }

    public String part2() {
        int total = 0;

        for (int[] report : unsafeReports) {
            int[] newReport = new int[report.length - 1];
            for (int index = 0; index < report.length; ++index) {
                int offset = 0;
                for (int j = 0; j < newReport.length; ++j) {
                    if (j == index) {
                        offset = 1;
                    }
                    if (j < newReport.length) {
                        newReport[j] = report[j + offset];
                    }
                }
                if (isSafe(newReport)) {
                    ++total;
                    break;
                }
            }
        }

        totalSafe += total;
	    return "" + totalSafe;
    }

    private boolean isSafe(int[] levelInt) {
        int diffOverall = levelInt[levelInt.length - 1] - levelInt[0]; // (last - first); positive if increase/negative if decrease
        if (diffOverall != 0 && (diffOverall > 0 || diffOverall < 0)) {
            boolean isIncreasing = diffOverall > 0;
            for (int i = 0; i < levelInt.length - 1; ++i) {
                int diffAbs = Math.abs(levelInt[i + 1] - levelInt[i]); // measures absolute difference
                if (diffAbs >= 1 && diffAbs <= 3) {
                    int diffActual = levelInt[i + 1] - levelInt[i]; // (next - current); positive if increase/negative if decrease
                    if (isIncreasing) {
                        if (diffActual < 0) {
                            return false;
                        }
                    } else {
                        if (diffActual > 0) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
	    Class<? extends AbstractAOC> myClass = Day2.class;
	    String inputFilename = "Day2.input";
        try{
            run(myClass,inputFilename); // don't ask for a filename
        } catch (RuntimeException e) {
            run(myClass); // ask for filename
        }
    }
}
