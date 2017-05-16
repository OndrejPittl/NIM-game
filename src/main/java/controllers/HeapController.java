package controllers;

import javafx.scene.layout.VBox;
import model.MatchState;

public class HeapController {


    private VBox container;

    private int matchCount;

    private MatchController[] matches;

    private BoardController boardController;


    //private int[] gameStatus;


    public HeapController() {
        this.matchCount = 0;
    }

    public void setData(VBox container, MatchController[] matches, BoardController boardController) {
        this.container = container;
        this.matches = matches;
        this.matchCount = matches.length;
        this.boardController = boardController;
    }

    public void proceedMatchSelect(int index) {
        for (int i = 0; i <= index; i++) {
            this.matches[i].updateState(MatchState.HIDDEN);
        }

        this.boardController.proceedPlayerTurn();
    }

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

    public int computeHeapStatus() {
        int mCount = 0;

        for (int i = 0; i < this.matchCount; i++) {
            if(this.matches[i].getState() == MatchState.VISIBLE)
                mCount++;
        }

        return mCount;
    }

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
