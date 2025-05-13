import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

/**
 * Purpose: starter code for AdventOfCode
 * Available objects BufferedReader read, Scanner scan that have access to the
 * file specified when running.
 */
public class Day6 extends AbstractAOC {
    private char[][] grid;
    private final char[] GUARD_DIRECTION = { '^', '>', 'v', '<' }; // UP, RIGHT, DOWN, LEFT
    private final int[][] OFFSET_ARR = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    private int index;
    private final char OBSTACLE = '#';
    private final char EMPTY = '.';
    private final char VISITED = 'X';
    private HashMap<Character, int[]> dirOffset = new HashMap<>();
    private int[] startCoord;
    private ArrayList<int[]> validObstacleCoords = new ArrayList<>();
    private int rowGuardStart = -1;
    private int colGuardStart = -1;
    private char directionGuardStart = ' ';

    public Day6(String filename) throws FileNotFoundException {
        super(filename);

        for (int i = 0; i < GUARD_DIRECTION.length; ++i) {
            dirOffset.put(GUARD_DIRECTION[i], OFFSET_ARR[i]);
        }
    }

    public String part1() {
        // Get all the lines
        ArrayList<String> lines = new ArrayList<>();
        while (scan.hasNextLine()) {
            lines.add(scan.nextLine());
        }
        
        // Prepare a char grid with the input
        grid = new char[lines.size()][];
        int i = 0;
        for (String line : lines) {
            char[] charLine = new char[line.length()];
            for (int j = 0; j < line.length(); ++j) {
                charLine[j] = line.charAt(j);
            }
            grid[i] = charLine;

            ++i;
        }
        // Let's clone that charGrid
        char[][] newCharGrid = cloneGrid();

        // Let's find the initial row, col, and pos for the guard
        int rowPos = -1;
        int colPos = -1;
        char direction = '!';
        int row = 0;
        // For each line the in the character grid
        for (char[] line : newCharGrid) {
            int col = 0;
            // For each character in line
            for (char c : line) {
                // Let's check if that character matches one of the "guard" directional-characters
                for (int x = 0; x < GUARD_DIRECTION.length; ++x) {
                    if (c == GUARD_DIRECTION[x]) {
                        rowGuardStart = row;
                        colGuardStart = col;
                        rowPos = row;
                        colPos = col;

                        int[] coords = { row, col };

                        startCoord = coords;

                        directionGuardStart = c;
                        direction = c;

                        index = x;
                        newCharGrid[row][col] = EMPTY;
                    }
                }
                ++col;
            }
            // System.out.println();
            ++row;
        }

        int total = 1;
        boolean done = false;
        while (!done) {
            try {
                int[] offset = dirOffset.get(direction);
                newCharGrid[rowPos][colPos] = VISITED;
                char ahead = lookAhead(newCharGrid, rowPos, colPos, direction, offset);
                if (ahead == EMPTY || ahead == VISITED) {
                    if (ahead == EMPTY) {
                        ++total;
                        int[] coords = {rowPos, colPos};
                        if (!Arrays.equals(coords, startCoord)) {
                            for (int[] coord : validObstacleCoords) {
                                if (!Arrays.equals(coords, coord)) {
                                    validObstacleCoords.add(coords);
                                }
                            }
                        }
                    }
                    rowPos += offset[0];
                    colPos += offset[1];
                } else { // Must not be empty, must be OBSTACLE
                    if (index == 3) {
                        index = 0;
                    } else {
                        ++index;
                    }
                    direction = GUARD_DIRECTION[index];
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                done = true;
            }
        }

        return "" + total;
    }

    private char[][] cloneGrid() {
        char[][] newCharGrid = new char[grid.length][];
        for (int k = 0; k < newCharGrid.length; ++k) {
            char[] innerArr = new char[grid[k].length];
            for (int m = 0; m < grid[k].length; ++m) {
                innerArr[m] = grid[k][m];
            }
            newCharGrid[k] = innerArr;
        }

        return newCharGrid;
    }

    private char lookAhead(char[][] grid, int row, int col, char direction, int[] offset) {
        char x;
        try {
            x = grid[row + offset[0]][col + offset[1]];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return x;
    }

    private void printGrid(char[][] grid) {
        System.out.println();
        for (char[] line : grid) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
    }


    public String part2() {
        // Total number of new obstacles that would lead to a loop, starting with 0
        int total = 0;
        // The grid was already made before, so let's just clone it
        char[][] grid = cloneGrid();

        for (int[] obstacleCoord : validObstacleCoords) {
            // Determing the new obstacle's position
            int rowObs = obstacleCoord[0];
            int colObs = obstacleCoord[1];
            // Place the obstacle onto the grid
            grid[rowObs][colObs] = OBSTACLE;

            int rowGuard = rowGuardStart;
            int colGuard = colGuardStart;
            grid[rowGuard][colGuard] = EMPTY;
            
            char direction = directionGuardStart;

            ArrayList<String> moves = new ArrayList<String>();

            boolean done = false;
            while (!done) {
                try {
                    int[] offset = dirOffset.get(direction);
                    // grid[rowGuard][colGuard] = VISITED;
                    char ahead = lookAhead(grid, rowGuard, colGuard, direction, offset);
                    if (ahead == EMPTY) {
                        String moveStr = "" + direction + colGuard + rowGuard;
                        // While trying to solve the grid, check if we are in a loop
                        if (moves.contains(moveStr)) {
                            // If we are in a loop, iterate the counter
                            ++total;

                            printGrid(grid);
                            done = true;
                        } else {
                            moves.add(moveStr);
                        }

                        rowGuard += offset[0];
                        colGuard += offset[1];
                    } else { // Must not be empty, must be OBSTACLE
                        if (index == 3) {
                            index = 0;
                        } else {
                            ++index;
                        }
                        direction = GUARD_DIRECTION[index];
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // Must have found an exit
                    done = true;
                }
            }
            // At the end, to save processing time, let's set that x and y char back to its original empty space
            grid[rowObs][colObs] = EMPTY;
        }
        // 1263 -> too low
        // 2526 -> too high

        return "" + total;
    }



    public static void main(String[] args) {
        Class<? extends AbstractAOC> myClass = Day6.class;
        String inputFilename = "Day6_1.input";
        try {
            run(myClass, inputFilename); // don't ask for a filename
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
