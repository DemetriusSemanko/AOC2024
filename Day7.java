import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.math.BigInteger;

/**
 * Purpose: starter code for AdventOfCode
 * Available objects BufferedReader read, Scanner scan that have access to the
 * file specified when running.
 */
public class Day7 extends AbstractAOC {
    private ArrayList<Equation> equations = new ArrayList<>();
    private final char PLUS = '0';
    private final char MULT = '1';

    private class Equation {
        public BigInteger testValue;
        public BigInteger[] operands;
        public boolean isPossible;

        public Equation(String testValue, String[] operands) {
            this.testValue = BigInteger.valueOf(Long.parseUnsignedLong(testValue));
            this.operands = new BigInteger[operands.length];
            for (int i = 0; i < operands.length; ++i) {
                this.operands[i] = new BigInteger(operands[i]);
            }
        }

        public boolean isPossible() {
            /* For loop that runs 2 ^ (operands.length - 1) times, or
             * the number of permutations required 
             */
            for (int i = ((int) Math.pow(2, operands.length - 1)) - 1; i >= 0; --i) {
                String binary = Integer.toBinaryString(i);
                while (binary.length() < operands.length - 1) {
                    binary = "0" + binary;
                }
                BigInteger total = operands[0];
                for (int j = 1; j < operands.length; ++j) {
                    if (binary.charAt(j - 1) == MULT) {
                        total = total.multiply(operands[j]);
                    } else {
                        total = total.add(operands[j]);
                    }
                }
                isPossible = (total.equals(testValue));
                if (isPossible) {
                    break;
                }
            }

            return isPossible;
        }

        public boolean isPossible2() {
            int operators = operands.length - 1;
            int combinations = (int) Math.pow(3, operators);

            for (int i = combinations - 1; i >= 0; --i)  {
                String base3 = convertToBase3(i);
                while (base3.length() < operators) {
                    base3 = "0" + base3;
                }
                BigInteger total = operands[0];
                for (int j = 1; j < operands.length; ++j) {
                    char c = base3.charAt(j - 1);
                    if (c == PLUS) {
                        total = total.add(operands[j]);
                    } else if (c == MULT) {
                        total = total.multiply(operands[j]);
                    } else { // Must be a concatenation
                        total = new BigInteger(total.toString() + operands[j].toString());
                    }
                }

                isPossible = (total.equals(testValue));
                if (isPossible) {
                    break;
                }
            }

            return isPossible;
        }

        public static String convertToBase3(int n) {
            if (n == 0) { return "0"; }
            String out = "";
            
            while (n > 0) {
                int remainder = n % 3;
                n /= 3;
                out = remainder + out;
            }
            
            return out;
        }

        public String toString() {
            String out = "";
            for (BigInteger operand : operands) {
                out += operand + " ";
            }

            return testValue + ": " + out;
        }
    }

    public Day7(String filename) throws FileNotFoundException {
        super(filename);
    }

    public String part1() {
        while (scan.hasNextLine()) {
            String[] colonSplit = scan.nextLine().split(":");
            equations.add(new Equation(colonSplit[0], colonSplit[1].trim().split(" ")));
        }
        BigInteger total = BigInteger.valueOf(0);

        for (Equation e : equations) {
            if (e.isPossible()) {
                total = total.add(e.testValue);
            }
        }        
        
        // 103605153843 too low
        return "" + total;
    }

    public String part2() {
        BigInteger total = BigInteger.valueOf(0);
        for (Equation e : equations) {
            if (e.isPossible2()) {
                total = total.add(e.testValue);
            }
        }
        return "" + total;
    }

    public static void main(String[] args) {
        Class<? extends AbstractAOC> myClass = Day7.class;
        String inputFilename = "Day7.input";
        try {
            run(myClass, inputFilename); // don't ask for a filename
        } catch (RuntimeException e) {
            e.printStackTrace();
            run(myClass); // ask for filename
        }
    }
}
