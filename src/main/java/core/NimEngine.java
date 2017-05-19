package core;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NimEngine {

    /**
     * Aktuální stav hry.
     */
    private int[] gameState;

    /**
     * Záloha stavu hry před vykonáním nového tahu.
     */
    private int[] origGameState;

    /**
     * Znalostní báze výherních stavů.
     */
    private KnowledgeBase knowledgeBase;


    //private boolean winner;


    public NimEngine(int[] matchCounts) {
        this.gameState = matchCounts;
        this.knowledgeBase = new KnowledgeBase();
    }

    /**
     * Obsluhuje AI tah, vrací nový stav hry.
     */
    public int[] getAIMove() {
        this.origGameState = this.gameState.clone();
        this.processMove();
        return this.getOrderedGameState();
        //return this.gameState;
    }

    /**
     *  Vykonává AI tah:
     *  Ve znalostní bázi vyhledá dosažitelný výherní stav hry, existuje-li takový.
     *  Neexistuje-li, odebere jednu sirku z hromádky s největším počtem sirek,
     *  tj. cesta nejmenšího odporu (oddaluje konec hry).
     */
    private void processMove() {
        System.out.println("I am in state: " + Arrays.toString(this.gameState));
        int[] targetSituation = knowledgeBase.getAccessibleSituation(this.gameState);

        if (targetSituation != null) {
            // dosažitelná pozice nalezena a vykonána
            this.gameState = targetSituation;
        } else {
            // dosažitelná pozice NEnalezena, sirka odebrána z největší hromádky
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

//    (původní implementace)
//
//    /**
//     * Počítá stav hry.
//     */
//    private int computeGameState() {
//        int result = 0;
//
//        for (int i = 0; i < this.gameState.length; i++) {
//            result ^= this.gameState[i];
//        }
//
//        return result;
//    }

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


    public int[] getOrderedGameState() {
        // origGameState | gameState

        int origHeapCount = this.origGameState.length,
            newHeapCount = this.gameState.length,
            numberLeft = -1;

        // pocty sirek na hromadkach ve spravnem poradi
        int[] newState = new int[newHeapCount];
        for (int i = 0; i < newHeapCount; i++) {
            newState[i] = -1;
        }

        System.out.println("original: " + Arrays.toString(this.origGameState));
        System.out.println("     new: " + Arrays.toString(this.gameState));


        for (int i = 0; i < newHeapCount; i++) {
            int searched = this.gameState[i],
                index = -1;

            for (int j = 0; j < origHeapCount; j++) {
                if (this.origGameState[j] == searched) {
                    index = j;
                    break;
                }
            }

            if(index == -1) {
                numberLeft = searched;
            } else {
                newState[index] = searched;
                this.origGameState[index] = -1;
            }
        }

        for (int i = 0; i < newHeapCount; i++) {
            if(newState[i] == -1) {
                newState[i] = numberLeft;
                break;
            }
        }

        System.out.println(" ordered: " + Arrays.toString(newState));
        return newState;
    }
}