package core;

import config.Routes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        for (int i = 0; i < threeHeapedWinningPositions.length; i++) {
            System.out.println(Arrays.toString(threeHeapedWinningPositions[i]));
        }
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
