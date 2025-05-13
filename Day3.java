import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;



/**
 *  Purpose: starter code for AdventOfCode
 *  Available objects BufferedReader read, Scanner scan that have access to the file specified when running.
 */
public class Day3 extends AbstractAOC {
    private class Mul {
        private int left;
        private int right;
        private int index;

        private Mul(int left, int right, int index) {
            this.left = left;
            this.right = right;
            this.index = index;
        }

        private int eval() {
            return left * right;
        }
    }
    
    private ArrayList<Mul> muls = new ArrayList<>();

    public Day3(String filename) throws FileNotFoundException {
	    super(filename);
    }

    public String part1() {
        int total = 0;


        String input = "";
        while(scan.hasNextLine()) {
            input += scan.nextLine();
        }

        Pattern regex = Pattern.compile("mul\\(\\d+,\\d+\\)");

        boolean done = false;
        int size = input.length();
        while(!done) {
            Matcher matcher = regex.matcher(input);
            if (matcher.find()) {
                String match = matcher.group();


                String valuesRaw = match.substring(match.indexOf("(") + 1, match.indexOf(")"));
                String[] values = valuesRaw.split(",");

                int left = Integer.parseInt(values[0]);
                int right = Integer.parseInt(values[1]);

                input = input.substring(input.indexOf(match) + match.length());
                int index = size - input.length() - match.length();
                Mul newM = new Mul(left, right, index);
                muls.add(newM);

                total += newM.eval();
            } else {
                done = true;
            }
        }

	    return "" + total;
    }

    public String part2() {
	    int total = 0;


        String input = "";
        while(scan.hasNextLine()) {
            input += scan.nextLine();
        }

        String doStr = "do()";
        String dontStr = "don't()";

        boolean isDo = true;

        for (int i = 0; i <= input.length() - dontStr.length(); ++i) {
            if (input.substring(i, i + doStr.length()).equals(doStr)) {
                isDo = true;
            } else if (input.substring(i, i + dontStr.length()).equals(dontStr)) {
                isDo = false;
            }
            if (isDo) {
                for (Mul mul : muls) {
                    if (mul.index == i) {
                        total += mul.eval();
                    }
                }
            }
        }
      

	    return "" + total;
    }

    public static void main(String[] args) {
        Class<? extends AbstractAOC> myClass = Day3.class;
        String inputFilename = "Day3.input";
        try{
            run(myClass,inputFilename); // don't ask for a filename
        } catch (RuntimeException e) {
            run(myClass); // ask for filename
        }
    }
}
