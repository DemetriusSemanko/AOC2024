import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Purpose: starter code for AdventOfCode
 * Available objects BufferedReader read, Scanner scan that have access to the
 * file specified when running.
 */
public class Day5 extends AbstractAOC {
    private static ArrayList<String[]> rules = new ArrayList<>();
    ArrayList<String[]> updates = new ArrayList<>();
    ArrayList<String[]> goodUpdates = new ArrayList<>();
    ArrayList<String[]> badUpdates = new ArrayList<>();

    public Day5(String filename) throws FileNotFoundException {
        super(filename);
    }

    public String part1() {
        /*
         * if an update includes both page number 47 and page number 53,
         * then page number 47 must be printed at some point before page number 53
         */
        boolean inRules = true;
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.length() == 0) {
                inRules = false;
            } else {
                if (inRules) {
                    rules.add(line.split("\\|"));
                } else {
                    updates.add(line.split(","));
                }
            }
        }

        int total = 0;
        for (String[] update : updates) { // look at every update
            if (isGood(update)) {
                goodUpdates.add(update);
                String middlePage = update[(update.length / 2)];
                total += Integer.parseInt(middlePage);
            } else {
                badUpdates.add(update);
            }
        }

        return "" + total;
    }

    public static boolean isGood(String[] update) {
        boolean goodUpdate = true;
        for (String[] rule : rules) { // for each update, let's look at the rules we have to follow
            int[] indices = { -1, -1 };
            for (int i = 0; i < update.length; ++i) { // let's index through each page in the update
                if (update[i].equals(rule[0])) { // i is the index of the left side of the rule
                    indices[0] = i; // indices[0] will always have the index where the rule's left page was found
                } else if (update[i].equals(rule[1])) { // is is the index of the right side of the rule
                    indices[1] = i; // indices[1] will always have the right rule
                }
            }
            if (indices[0] != -1 && indices[1] != -1) { // only true if we found both pages from a rule in the
                                                        // update
                if (indices[0] > indices[1]) {
                    goodUpdate = false;
                }
            }
        }
        return goodUpdate;
    }

    public String part2() {
        int total = 0;
        for (String[] badUpdate : badUpdates) {
            while (!isGood(badUpdate)) {
                for (String[] rule : rules) {
                    if (containsRule(badUpdate, rule) && !isRuleSafe(badUpdate, rule)) {
                        swapOnRule(badUpdate, rule);
                    }
                }
            }
            String middlePage = badUpdate[(badUpdate.length / 2)];
            total += Integer.parseInt(middlePage);
        }

        return "" + total;
    }

    private static boolean containsRule(String[] update, String[] rule) {
        return (Arrays.asList(update).contains(rule[0]) && Arrays.asList(update).contains(rule[1]));
    }

    private static boolean isRuleSafe(String[] update, String[] rule) {
        int[] indices = new int[2];
        int i = 0;
        for (String x : update) {
            if (x.equals(rule[0])) {
                indices[0] = i;
            } else if (x.equals(rule[1])) {
                indices[1] = i;
            }
            ++i;
        }
        return indices[0] < indices[1];
    }

    private static void swapOnRule(String[] update, String[] rule) {
        int[] indices = new int[2];
        int i = 0;
        for (String x : update) {
            if (x.equals(rule[0])) {
                indices[0] = i;
            } else if (x.equals(rule[1])) {
                indices[1] = i;
            }
            ++i;
        }
        String temp = new String(update[indices[0]]);
        update[indices[0]] = new String(update[indices[1]]);
        update[indices[1]] = temp;
    }

    public static void main(String[] args) {
        Class<? extends AbstractAOC> myClass = Day5.class;
        String inputFilename = "Day5.input";
        try {
            run(myClass, inputFilename); // don't ask for a filename
        } catch (RuntimeException e) {
            run(myClass); // ask for filename
        }
    }
}
