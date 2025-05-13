import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.math.BigInteger;

/**
 * Purpose: starter code for AdventOfCode
 * Available objects BufferedReader read, Scanner scan that have access to the
 * file specified when running.
 */
public class Day9 extends AbstractAOC {
    private ArrayList<Block> blocks = new ArrayList<>();
    private final String EMPTY = ".";
    private ArrayList<Block2> blocks2 = new ArrayList<>();

    private class Block {
        public int id;

        public Block(int id) {
            this.id = id;
        }

        public String toString() {
            if (id == -1) {
                return ".";
            } else {
                return "" + id;
            }
        }
    }

    private class Block2 {
        public int id;
        public int length;
        public boolean isEmpty;

        public Block2(int id, int length, boolean isEmpty) {
            this.id = id;
            this.length = length;
            this.isEmpty = isEmpty;
        }

        public String toString() {
            String out = "";
            for (int i = 1; i <= length; ++i) {
                if (id == -1) {
                    out += ".";
                } else {
                    out += "" + id;
                }
            }

            return out + ",";
        }
    }

    public Day9(String filename) throws FileNotFoundException {
        super(filename);
    }

    public String part1() {
        String line = "";
        while (scan.hasNextLine()) {
            line = scan.nextLine();
        }
        String disk = "";
        int id = 0;
        for (int i = 0; i < line.length(); ++i) {
            int count = Integer.parseInt(line.substring(i, i + 1));
            int value;
            Block b = null;
            if (i % 2 == 0) {
                value = id;
                ++id;
            } else {
                value = -1;
            }

            for (int j = 1; j <= count; ++j) {
                blocks.add(new Block(value));
            }
        }
        
        for (int i = 0; i < blocks.size(); ++i) { // i has the empty block to be filled
            if (blocks.get(i).id == -1) { // Empty block, must be filled
                boolean found = false;
                int j = blocks.size() - 1;
                Block b = null; // Block to be moved
                while (!found && j >= 0) {
                    if (blocks.get(j).id > -1) { // We found a non-empty block
                        b = blocks.remove(j);
                        --j;
                        found = true;
                    } else if (blocks.get(j).id == -1) {
                        blocks.remove(j);
                        --j;
                    } else {
                        --j;
                    }
                }
                // At this point, we should have found the block that needs to be moved
                blocks.set(i, b);
            }
        }
        BigInteger total = BigInteger.valueOf(0);
        for (int i = 0; i < blocks.size(); ++i) {
            total = total.add(BigInteger.valueOf(i * blocks.get(i).id));
        }

        return "" + total;
    }

    public String part2() {
        String line = "";
        line = scan.nextLine();

        String disk = "";
        int idCounter = -1;
        for (int i = 0; i < line.length(); ++i) {
            int id = -1;
            int length = Integer.parseInt(line.substring(i, i + 1));
            boolean isEmpty = true;
            if (i % 2 == 0) {
                ++idCounter;
                id = idCounter;
                isEmpty = false;
            }
            if (length > 0) {
                blocks2.add(new Block2(id, length, isEmpty));
            }
        }

        for (int i = idCounter; i > 0; --i) { // i has the idCounter that we want to move
            for (int j = blocks2.size() - 1; j > 0; --j) {
                if (blocks2.get(j).id == i) { // j must have the index of the i'th file block
                    for (int k = 1; k < j; ++k) {
                        if (blocks2.get(k).isEmpty) { // k must have the index of an empty block
                            if (blocks2.get(k).length >= blocks2.get(j).length) { // true if the empty block at k is big enough to hold our file
                                Block2 b = blocks2.remove(j); // Getting the file we're removing
                                int fileLength = b.length;
                                // I need to consider four possibilities
                                // 3344455
                                // ..4455
                                // ..44..
                                // 3344..

                                boolean leftEmpty = false;
                                boolean rightEmpty = false;
                                int leftLength = 0;
                                int rightLength = 0;
                                leftEmpty = blocks2.get(j - 1).isEmpty; // true if the left block is empty space
                                if (leftEmpty) { leftLength = blocks2.get(j - 1).length; }
                                if (j < blocks2.size()) { rightEmpty = blocks2.get(j).isEmpty; } // true if the right block is empty space
                                if (rightEmpty) { rightLength = blocks2.get(j).length; }

                                if (leftEmpty && rightEmpty) {
                                    blocks2.remove(j);
                                    blocks2.set(j - 1, new Block2(-1, leftLength + fileLength + rightLength, true));
                                } else if (leftEmpty) {
                                    blocks2.set(j - 1, new Block2(-1, leftLength + fileLength, true));
                                } else if (rightEmpty) {
                                    blocks2.set(j, new Block2(-1, fileLength + rightLength, true));
                                } else {
                                    blocks2.add(j, new Block2(-1, fileLength, true));
                                }
                                

                                int emptyReceiveLength = blocks2.remove(k).length; // Remove empty from new location
                                blocks2.add(k, b); // Add file to new location
                                if (emptyReceiveLength > b.length) { // Add what empty space would remain after the file in its new location
                                    blocks2.add(k + 1, new Block2(-1, emptyReceiveLength - b.length, true));
                                } else {
                                    --j;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Calculate total
        int position = 0;
        BigInteger total = BigInteger.valueOf(0);
        for (Block2 b : blocks2) {
            // if b is empty, simply add the length of it to position
            // otherwise, enter block b
            if (b.isEmpty) {
                position += b.length;
            } else {
                for (int i = 1; i <= b.length; ++i) {
                    total = total.add(BigInteger.valueOf(position * b.id));
                    ++position;
                }
            }
        }
        // 9759091069198
        // 6681388589250 -> too high
        // 6366992962376 -> too high
        // 6362722604045 -> CORRECT

        return "" + total;
    }

    public static void main(String[] args) {
        Class<? extends AbstractAOC> myClass = Day9.class;
        String inputFilename = "Day9.input";
        try {
            run(myClass, inputFilename); // don't ask for a filename
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
