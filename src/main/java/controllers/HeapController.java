package controllers;

import javafx.scene.layout.VBox;
import model.MatchState;

public class HeapController {

    /**
     *  Počet sirek na hromádce.
     */
    private int matchCount;

    /**
     *  Controllery sirek.
     */
    private MatchController[] matches;

    /**
     *  Controller hrací desky.
     */
    private BoardController boardController;


    /**
     *  Konstruktor.
     */
    public HeapController() {
        this.matchCount = 0;
    }

    /**
     *  Setter.
     */
    public void setData(MatchController[] matches, BoardController boardController) {
        this.matches = matches;
        this.matchCount = matches.length;
        this.boardController = boardController;
    }

    /**
     *  Obsluha tahu.
     */
    public void proceedMatchSelect(int index) {
        for (int i = 0; i <= index; i++) {
            this.matches[i].updateState(MatchState.HIDDEN);
        }

        this.boardController.proceedPlayerTurn();
    }

    /**
     *  Obarvování zvolených sirek.
     */
    public void proceedMatchHover() {
        int index = MatchController.getHoveringIndex();

        for (int i = 0; i < this.matchCount; i++) {

            if(this.matches[i].getState() == MatchState.HIDDEN)
                continue;

            if(i <= index) {
                this.matches[i].updateState(MatchState.HOVERED);
            } else {
                this.matches[i].updateState(MatchState.VISIBLE);
            }
        }
    }

    /**
     *  Getter stavu hromádky.
     */
    public int computeHeapStatus() {
        int mCount = 0;

        for (int i = 0; i < this.matchCount; i++) {
            if(this.matches[i].getState() == MatchState.VISIBLE)
                mCount++;
        }

        return mCount;
    }

    /**
     *  Aktualizace stavu hromádky. Znázornění tahu.
     */
    public void updateMatches(int matchStatus) {

        int edge = this.matchCount - matchStatus;

        for (int i = 0; i < this.matchCount; i++) {
            if(i < edge) {
                this.matches[i].updateState(MatchState.HIDDEN);
            } else {
                this.matches[i].updateState(MatchState.VISIBLE);
            }

        }
    }
}
