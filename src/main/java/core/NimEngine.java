package core;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NimEngine {

    private int[] gameState;
    private KnowledgeBase knowledgeBase;
    private boolean winner;

    public NimEngine(int[] matchCounts) {
        this.gameState = matchCounts;
        this.knowledgeBase = new KnowledgeBase();
    }

    /**
     * Obsluhuje AI tah, vrací nový stav hry.
     */
    public int[] getAIMove() {
        this.processMove();
        return this.gameState;
    }

    /**
     *  Vykonává AI tah.
     */
    private void processMove() {
        System.out.println("I am in state: " + Arrays.toString(this.gameState));
        int[] targetSituation = knowledgeBase.getAccessibleSituation(this.gameState);

        if (targetSituation != null) {
            this.gameState = targetSituation;
        } else {
            int max = Integer.MIN_VALUE;
            int maxIndex = 0;

            for (int i = 0; i < this.gameState.length; i++) {
                if (this.gameState[i] > max) {
                    max = this.gameState[i];
                    maxIndex = i;
                }
            }
            this.gameState[maxIndex] -= 1;
        }
        System.out.println("Ended up in state: " + Arrays.toString(this.gameState));
    }

    /**
     * Počítá stav hry.
     */
    private int computeGameState() {
        int result = 0;

        for (int i = 0; i < this.gameState.length; i++) {
            result ^= this.gameState[i];
        }

        return result;
    }

    public void setGameState(int[] newState) {
        this.gameState = newState;
    }

    public boolean isWinner() {
        int sum = 0;

        for (int i = 0; i < this.gameState.length; i++) {
            sum += this.gameState[i];
        }

        return sum == 0;
    }

}
