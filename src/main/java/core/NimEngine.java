package core;


public class NimEngine {

    private int[] gameState;
    private boolean winner;

    public NimEngine(int[] matchCounts) {
        this.gameState = matchCounts;
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
        int state = this.computeGameState();

        if (state != 0) {

            // strategický tah

            for (int i = 0; i < this.gameState.length; i++) {
                if ((this.gameState[i] ^ state) <= this.gameState[i]) {
                    this.gameState[i] = this.gameState[i] ^ state;
                    return;
                }
            }
        } else {

            // cesta nejmenšího odporu (oddálení konce) odebráním jediné
            // sirpy z balíčku s nejvyšším počtem sirek

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
