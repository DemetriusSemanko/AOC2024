import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *  Purpose: starter code for AdventOfCode
 *  Available objects BufferedReader read, Scanner scan that have access to the file specified when running.
 */
public class Day4 extends AbstractAOC {
    private ArrayList<String> puzzle = new ArrayList<>();
    private String[][] puzzleArr;

    int maxHeight;
    int maxLength;

    private final String X = "X";
    private final String M = "M";
    private final String A = "A";
    private final String S = "S";

    public Day4(String filename) throws FileNotFoundException {
	    super(filename);
    }

    public String part1() {
        int total = 0;

        // Make an ArrayList of Strings, each String is a line
        while(scan.hasNextLine()) {
            puzzle.add(scan.nextLine());
        }

        // Turn those lines into a 2-D array
        maxHeight = puzzle.size();
        maxLength = 0;
        for (String line : puzzle) {
            if (line.length() > maxLength) { maxLength = line.length(); }
        }
        puzzleArr = new String[maxHeight][maxLength];
        for (int i = 0; i < puzzleArr.length; ++i) {
            puzzleArr[i] = puzzle.get(i).split("");
        }

        for (int row = 0; row < puzzleArr.length; ++row) {
            for (int col = 0; col < puzzleArr[row].length; ++col) {
                if (puzzleArr[row][col].equals(X)) {
                    total += isXMAS(row, col);
                }
            }
        }

	    return "" + total;
    }

    public int isXMAS(int row, int col) {
        int total = 0;
        // Check N
        if (row - 3 >= 0) {
            if (puzzleArr[row - 1][col].equals(M)) {
                if (puzzleArr[row - 2][col].equals(A)) {
                    if (puzzleArr[row - 3][col].equals(S)) {
                        ++total;
                    }
                }
            }
        }
        // Check E
        if (col + 3 <= puzzleArr.length) {
            if (puzzleArr[row][col + 1].equals(M)) {
                if (puzzleArr[row][col + 2].equals(A)) {
                    if (puzzleArr[row][col + 3].equals(S)) {
                        ++total;
                    }
                }
            }
        }
        // Check S
        if (row + 3 <= maxHeight) {
            if (puzzleArr[row + 1][col].equals(M)) {
                if (puzzleArr[row + 2][col].equals(A)) {
                    if (puzzleArr[row + 3][col].equals(S)) {
                        ++total;
                    }
                }
            }
        }
        // Check W
        if (col - 3 >= 0) {
            if (puzzleArr[row][col - 1].equals(M)) {
                if (puzzleArr[row][col - 2].equals(A)) {
                    if (puzzleArr[row][col - 3].equals(S)) {
                        ++total;
                    }
                }
            }
        }
        // Check NE
        if (row - 3 >= 0 && col + 3 < maxLength) {
            if (puzzleArr[row - 1][col + 1].equals(M)) {
                if (puzzleArr[row - 2][col + 2].equals(A)) {
                    if (puzzleArr[row - 3][col + 3].equals(S)) {
                        ++total;
                    }
                }
            }
        }
        // Check SE row + 3, col + 3
        if (row + 3 < maxHeight && col + 3 < maxLength) {
            if (puzzleArr[row + 1][col + 1].equals(M)) {
                if (puzzleArr[row + 2][col + 2].equals(A)) {
                    if (puzzleArr[row + 3][col + 3].equals(S)) {
                        ++total;
                    }
                }
            }
        }
        // Check SW row + 3, col - 3
        if (row + 3 < maxHeight && col - 3 >= 0) {
            if (puzzleArr[row + 1][col - 1].equals(M)) {
                if (puzzleArr[row + 2][col - 2].equals(A)) {
                    if (puzzleArr[row + 3][col - 3].equals(S)) {
                        ++total;
                    }
                }
            }
        }
        // Check NW row - 3, col - 3
        if (row - 3 >= 0 && col - 3 >= 0) {
            if (puzzleArr[row - 1][col - 1].equals(M)) {
                if (puzzleArr[row - 2][col - 2].equals(A)) {
                    if (puzzleArr[row - 3][col - 3].equals(S)) {
                        ++total;
                    }
                }
            }
        }

        return total;
    }

    public String part2() {
	    int total = 0;

        for (int row = 0; row < puzzleArr.length; ++row) {
            for (int col = 0; col < puzzleArr[row].length; ++col) {
                if (puzzleArr[row][col].equals(A)) {
                    total += isX_MAS(row, col);
                }
            }
        }

	    return "" + total;
    }

    public int isX_MAS(int row, int col) {
        if (row - 1 >= 0 && col - 1 >= 0 && row + 1 < puzzleArr.length && col + 1 < puzzleArr[0].length) {
            for (int i = 1; i <= 4; ++i) {
                String topL = M, topR = M;
                String botL = S, botR = S;
                if (i == 2) { // Ss on top, Ms on bottom
                    topL = S;
                    topR = S;
                    botL = M;
                    botR = M;
                }
                if (i == 3) { // Ms on left, Ss on right
                    topL = M;
                    botL = M;
                    topR = S;
                    botR = S;
                }
                if (i == 4) { // Ms on right, Ss on left
                    topR = M;
                    botR = M;
                    topL = S;
                    botL = S;
                }
                if (puzzleArr[row - 1][col - 1].equals(topL)) {
                    if (puzzleArr[row - 1][col + 1].equals(topR)) {
                        if (puzzleArr[row + 1][col - 1].equals(botL)) {
                            if (puzzleArr[row + 1][col + 1].equals(botR)) {
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Class<? extends AbstractAOC> myClass = Day4.class;
        String inputFilename = "Day4.input";
        try{
            run(myClass,inputFilename); // don't ask for a filename
        } catch (RuntimeException e) {
            run(myClass); // ask for filename
        }
    }
}
