import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.math.BigInteger;

/**
 * Purpose: starter code for AdventOfCode
 * Available objects BufferedReader read, Scanner scan that have access to the
 * file specified when running.
 */
public class Day11 extends AbstractAOC {
    private ArrayList<Stone> stones = new ArrayList<>();
    private final int BLINKS = 25;
    private final int BLINKS2 = 75;
    private final long CONST_2024 = 2024;
    private int p1_answer;
    private int p2_answer;

    private class Stone {
        public BigInteger value;
        public int digitCount;
        public Stone(BigInteger value) {
            this.value = value;
        }

        public BigInteger getValue() {
            return value;
        }

        public int getDigitCount() {
            digitCount = value.toString().length();
            return digitCount;
        }


        public String toString() {
            return "" + value;
        }
    }

    public Day11(String filename) throws FileNotFoundException {
        super(filename);
    }

    public String part1() {

        while (scan.hasNext()) {
            stones.add(new Stone(new BigInteger(scan.next())));
        }

        for (int i = 1; i <= BLINKS; ++i) {
            for (int j = 0; j < stones.size(); ++j) {
                Stone stone = stones.get(j);
                if (stone.getValue().equals(BigInteger.ZERO)) { // Rule 1: Check if the value on the stone is 0
                    // Replace the 0 with a 1
                    stone.value = BigInteger.ONE;
                } else if (stone.getDigitCount() % 2 == 0) { // Rule 2: Check if the number of digits written on the stone is even
                    // Split the stone
                    String digits = stone.getValue().toString();
                    int terminator = digits.length() / 2;

                    BigInteger valueLeft = new BigInteger(digits.substring(0, terminator));
                    BigInteger valueRight = new BigInteger(digits.substring(terminator));

                    stones.remove(j);

                    stones.add(j, new Stone(valueRight));
                    stones.add(j++, new Stone(valueLeft));
                } else { // Rule 3: If none of the other rules apply
                    // Multiply the stone's value by 2024
                    stones.set(j, new Stone(stone.getValue().multiply(BigInteger.valueOf(CONST_2024))));
                }
            }
        }

        // 86 -> WRONG
        // 199982 -> RIGHT

        return "" + stones.size();
    }

    public String part2() {
        stones.clear();

        while (scan.hasNext()) {
            stones.add(new Stone(new BigInteger(scan.next())));
        }

        for (int i = 1; i <= BLINKS2; ++i) {
            for (int j = 0; j < stones.size(); ++j) {
                Stone stone = stones.get(j);
                if (stone.getValue().equals(BigInteger.ZERO)) { // Rule 1: Check if the value on the stone is 0
                    // Replace the 0 with a 1
                    stone.value = BigInteger.ONE;
                } else if (stone.getDigitCount() % 2 == 0) { // Rule 2: Check if the number of digits written on the stone is even
                    // Split the stone
                    String digits = stone.getValue().toString();
                    int terminator = digits.length() / 2;

                    BigInteger valueLeft = new BigInteger(digits.substring(0, terminator));
                    BigInteger valueRight = new BigInteger(digits.substring(terminator));

                    stones.remove(j);

                    stones.add(j, new Stone(valueRight));
                    stones.add(j++, new Stone(valueLeft));
                } else { // Rule 3: If none of the other rules apply
                    // Multiply the stone's value by 2024
                    stones.set(j, new Stone(stone.getValue().multiply(BigInteger.valueOf(CONST_2024))));
                }
            }
        }

        return "" + stones.size();
    }

    public static void main(String[] args) {
        Class<? extends AbstractAOC> myClass = Day11.class;
        String inputFilename = "Day11.input";
        try {
            run(myClass, inputFilename); // don't ask for a filename
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
