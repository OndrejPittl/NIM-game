package core;

import config.Routes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by justme on 17/05/2017.
 */
public class KnowledgeBase {

    int[][] twoHeapedWinningPositions;

    int[][] threeHeapedWinningPositions;

    int[][] fourHeapedWinningPositions;

    private final String WP_FILENAME = "winning_positions.txt";

    public KnowledgeBase() {
        try {
            loadWinningPositions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getAccessibleSituation(int[] currentSituation) {
        int heapCount = currentSituation.length;
        int[][] situations = null;

        switch (heapCount) {
            case 2: situations = twoHeapedWinningPositions; break;
            case 3: situations = threeHeapedWinningPositions; break;
            case 4: situations = fourHeapedWinningPositions; break;
        }

        for (int[] sit : situations) {
            if (isAccessible(currentSituation, sit)) {
                return sit;
            }
        }

        return null;
    }

    private boolean isAccessible(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }

        List<Integer> aa = new ArrayList<Integer>(a.length);
        for (int ai : a) { aa.add(ai); };

        List<Integer> bb = new ArrayList<Integer>(b.length);
        for (int bi : b) { bb.add(bi); };

        for (Object o : aa) {
            bb.remove(o);
        }

        return bb.size() == 1;
    }

    public int[][] getTwoHeapedWinningPositions() {
        return twoHeapedWinningPositions;
    }

    public int[][] getThreeHeapedWinningPositions() {
        return threeHeapedWinningPositions;
    }

    public int[][] getFourHeapedWinningPositions() {
        return fourHeapedWinningPositions;
    }

    private void loadWinningPositions() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File winningPositionsFile = new File(classLoader.getResource(Routes.KNOWLEDGE + WP_FILENAME).getFile());

        BufferedReader br = new BufferedReader(new FileReader(winningPositionsFile));

        String line;
        List<String> lines = new LinkedList<String>();
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }

        processLines(lines);
    }

    private void processLines(List<String> lines) {
        int twoIndex = 0;
        twoHeapedWinningPositions   = new int[getCount(2, lines)][2];
        int threeIndex = 0;
        threeHeapedWinningPositions = new int[getCount(3, lines)][3];
        int fourIndex = 0;
        fourHeapedWinningPositions  = new int[getCount(4, lines)][4];

        for (String line : lines) {
            String[] numbers = line.split(";");

            switch(numbers.length) {
                case 2: {
                    for (int i = 0; i < 2; i++) {
                        twoHeapedWinningPositions[twoIndex][i] = Integer.parseInt(numbers[i]);
                    }
                    twoIndex++;
                    break;
                }
                case 3: {
                    for (int i = 0; i < 3; i++) {
                        threeHeapedWinningPositions[threeIndex][i] = Integer.parseInt(numbers[i]);
                    }
                    threeIndex++;
                    break;
                }
                case 4: {
                    for (int i = 0; i < 4; i++) {
                        fourHeapedWinningPositions[fourIndex][i] = Integer.parseInt(numbers[i]);
                    }
                    fourIndex++;
                    break;
                }
            }
        }
    }

    private int getCount(int count, List<String> lines) {
        int total = 0;

        for (String line : lines) {
            if (line.split(";").length == count) {
                total++;
            }
        }

        return total;
    }

}
