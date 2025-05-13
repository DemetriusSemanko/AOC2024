import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *  Purpose: starter code for AdventOfCode
 *  Available objects BufferedReader read, Scanner scan that have access to the file specified when running.
 */
public class Day1 extends AbstractAOC {
    private ArrayList<Integer> left = new ArrayList<>();
    private ArrayList<Integer> right = new ArrayList<>();

    public Day1(String filename) throws FileNotFoundException {
	super(filename);
    }

    public String part1() {
        while(scan.hasNextLine()) {
            left.add(Integer.parseInt(scan.next()));
            right.add(Integer.parseInt(scan.next()));
            if (scan.hasNext()) {
                scan.nextLine();
            }
        }
        Collections.sort(left);
        Collections.sort(right);
        int total = 0;
        for (int i = 0; i < left.size(); ++i) {
            total += Math.abs(left.get(i) - right.get(i));
        }

        return "" + total;
    }

    public String part2() {
        HashMap<Integer, Integer> hm = new HashMap<>();
        for (Integer integer : right) {
            if (!hm.containsKey(integer)) {
                hm.put(integer, 1);
            } else {
                hm.put(integer, (hm.get(integer) + 1));
            }
        }
        int similarity = 0;

        for (Integer integer : left) {
            if (hm.containsKey(integer)) {
                similarity += integer * hm.get(integer);
            }
        }
	    return "" + similarity;
    }

    public static void main(String[] args) {
	Class<? extends AbstractAOC> myClass = Day1.class;
	String inputFilename = "Day1.input";
	try{
	    run(myClass,inputFilename); // don't ask for a filename
	} catch (RuntimeException e) {
	    run(myClass); // ask for filename
	}
    }
}
