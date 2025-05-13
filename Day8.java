import java.io.FileNotFoundException;

/**
 * Purpose: starter code for AdventOfCode
 * Available objects BufferedReader read, Scanner scan that have access to the
 * file specified when running.
 */
public class Day8 extends AbstractAOC {

    public Day8(String filename) throws FileNotFoundException {
        super(filename);
    }

    public String part1() {

        return "";
    }

    public String part2() {

        return "";
    }

    public static void main(String[] args) {
        Class<? extends AbstractAOC> myClass = Day8.class;
        String inputFilename = "Day8.input";
        try {
            run(myClass, inputFilename); // don't ask for a filename
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
