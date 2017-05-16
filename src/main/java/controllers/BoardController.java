package controllers;

import config.ViewConfig;
import core.NimEngine;
import io.DataLoader;
import io.UIComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    /**
     * Počet hromádek.
     */
    private int heapCount = -1;

    /**
     * Počet sirek na hromádkách.
     */
    private int[] matchCounts;

    private NimEngine nim;


    private HeapController[] heapControllers;
    private MatchController[] matchControllers;

    private OverlayController overlayController;


    /**
     * View komponenty.
     */
    @FXML
    private HBox heapWrapper;

    private VBox[] heapContainers;

    @FXML
    private BorderPane overlayWrapper;

    @FXML
    private Label lblOverlayMsgMajor;

    @FXML
    private Label lblOverlayMsgMinor;

    @FXML
    private Label lblOverlayMsgTitle;

    @FXML
    private HBox hboxOverlayControls;



    public void initialize(URL location, ResourceBundle resources) {
        this.overlayController = new OverlayController(this.overlayWrapper, this.lblMsg);
    }

    private void initMatches(){

        int heapWidth = 600 / this.heapCount - 30;

        // hromádky
        for (int i = 0; i < this.heapCount; i++) {

            VBox box = new VBox();
            box.getStyleClass().add("heapContainer");

            HeapController hController  = new HeapController();

            int matchCount = this.matchCounts[i];
            MatchController[] controllers = new MatchController[matchCount];

            // zápalky
            for (int j = 0; j < matchCount; j++) {
                UIComponent component = DataLoader.loadPartialLayout("match.fxml");
                BorderPane m = (BorderPane) component.getRoot();
                controllers[j] = (MatchController) component.getController();
                controllers[j].updateComponent(heapWidth);
                controllers[j].setData(j, hController);
                box.getChildren().add(m);
            }

            hController.setData(this.heapContainers[i], controllers, this);

            this.heapContainers[i] = box;
            this.heapControllers[i] = hController;
        }

        this.heapWrapper.getChildren().addAll(this.heapContainers);
    }


    public void proceedPlayerTurn() {
        int[] gameStatus = this.getGameStatus();
        this.nim.setGameState(gameStatus);

        if(this.nim.isWinner()) {
            System.out.println("Player is a winner!!!!");
            this.proceedGameOver(ViewConfig.MSG_WINNER_PLAYER);
        } else {
            this.proceedPCTurn();
        }
    }

    public void proceedPCTurn() {
        int[] gameStatus = this.nim.getAIMove();
        this.updateHeaps(gameStatus);

        if(this.nim.isWinner()) {
            this.proceedGameOver(ViewConfig.MSG_WINNER_PC);
        }
    }

    private void proceedGameOver(String msg) {
        this.overlayController.displayGameOver(msg);
    }




    public int[] getGameStatus() {
        int[] gameStatus = new int[this.heapCount];

        for (int i = 0; i < this.heapCount; i++) {
            gameStatus[i] = this.heapControllers[i].computeHeapStatus();
        }

        return  gameStatus;
    }

    private void updateHeaps(int[] gameStatus) {
        for (int i = 0; i < this.heapCount; i++) {
            this.heapControllers[i].updateMatches(gameStatus[i]);
        }
    }

    public void setData(int[] matchCounts) {
        this.matchCounts = matchCounts;
        this.heapCount = matchCounts.length;
        this.nim = new NimEngine(matchCounts);
        this.heapContainers = new VBox[heapCount];
        this.heapControllers = new HeapController[heapCount];
        this.initMatches();
    }
}
