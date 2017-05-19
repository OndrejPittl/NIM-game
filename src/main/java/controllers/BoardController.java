package controllers;

import config.ViewConfig;
import core.MainApp;
import core.NimEngine;
import io.DataLoader;
import io.UIComponent;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class BoardController implements Initializable {


    /**
     *  Příznak viditelnosti překryvné vrstvy.
     */
    private boolean overlayVisible = true;

    /**
     *  Nastavení hry předané uživatelem.
     */
    private int[] matchCountsSettings;

    /**
     * Počet hromádek hry.
     */
    private int heapCount = -1;

    /**
     * Počet sirek na jednotlivých hromádkách.
     */
    private int[] matchCounts;

    /**
     *  Výherní stratehgie umělé inteligence hry.
     */
    private NimEngine nim;

    /**
     *  Controllery obsluhující zobrazení hromádek a interakci s nimi.
     */
    private HeapController[] heapControllers;


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

    @FXML
    private Button btnExit;

    @FXML
    private Button btnAgain;



    public void initialize(URL location, ResourceBundle resources) {
        this.btnExit.setOnAction((e) -> MainApp.exitApp());
        this.btnAgain.setOnAction((e) -> handlePlayAgain());
    }

    /**
     *  Inicializace.
     */
    private void init(){
        this.nim = new NimEngine(matchCounts);
        this.heapContainers = new VBox[heapCount];
        this.heapControllers = new HeapController[heapCount];
        this.initMatches();
    }

    /**
     *  Inicializace zápalek.
     */
    private void initMatches(){

        int heapWidth = 600 / this.heapCount;

        // hromádky
        for (int i = 0; i < this.heapCount; i++) {

            VBox box = new VBox();
            box.getStyleClass().add("heapContainer");
            box.setMinWidth(heapWidth);
            box.setAlignment(Pos.TOP_CENTER);

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

            hController.setData(controllers, this);

            this.heapContainers[i] = box;
            this.heapControllers[i] = hController;
        }

        this.heapWrapper.getChildren().addAll(this.heapContainers);
    }


    /**
     *  Spuštění kola fyzického hráče.
     */
    public void startPlayerTurn() {
        this.blinkTurnOverlay(false);
    }

    /**
     *  Spuštění kola PC.
     */
    public void startPCTurn() {
        this.blinkTurnOverlay(true);
    }

    /**
     *  Hláška uživateli.
     */
    private void blinkTurnOverlay(boolean isPCTurn) {
        int delay = 300;

        if(isPCTurn) {
            this.displayTurnStart(ViewConfig.MSG_PC_TURN);
        } else {
            delay = 600;
            this.displayTurnStart(ViewConfig.MSG_PLAYER_TURN);
        }

        if(this.overlayVisible) {
            this.overlayVisible = false;
        } else {
            this.showOverlay(delay);
        }

        FadeTransition fadeOut = new FadeTransition(Duration.millis(ViewConfig.TIMER_TURN_OVERLAY_FADE_DURATION), this.overlayWrapper);
        fadeOut.setFromValue(1.0); fadeOut.setToValue(0.0);
        fadeOut.setOnFinished((e) -> {
        if(isPCTurn) {
            // PC
            new Timer().schedule(new TimerTask() {
                public void run() {
                    Platform.runLater(() -> proceedPCTurn());
                }
            }, ViewConfig.TIMER_TURN_OVERLAY_SHOW_DURATION);
        } else {
            // Player
            this.heapWrapper.setDisable(false);
        }});

        fadeOut.setDelay(Duration.millis(delay + ViewConfig.TIMER_TURN_OVERLAY_FADE_DURATION + ViewConfig.TIMER_TURN_OVERLAY_SHOW_DURATION));
        fadeOut.play();
    }

    /**
     *  Obsluha tahu hráče, kontrola výhry, iniciace tahu PC.
     */
    public void proceedPlayerTurn() {
        int[] gameStatus = this.getGameStatus();
        this.nim.setGameState(gameStatus);

        if(this.nim.isWinner()) {
            this.proceedGameOver(ViewConfig.MSG_WINNER_PLAYER);
        } else {
            this.startPCTurn();
        }
    }

    /**
     *  Obluha tahu PC.
     */
    public void proceedPCTurn() {
        int[] gameStatus = this.nim.getAIMove();
        this.updateHeaps(gameStatus);

        if(this.nim.isWinner()) {
            this.proceedGameOver(ViewConfig.MSG_WINNER_PC);
        } else {
            this.startPlayerTurn();
        }
    }

    /**
     *  Obsluha konce hry, zobrazení hlášky a nabídky.
     */
    private void proceedGameOver(String msg) {
        this.displayGameOver(msg);
        this.showOverlay(300);
    }

    /**
     *  Obsluha opětovného spuštění hry.
     */
    private void handlePlayAgain() {
        this.matchCounts = this.matchCountsSettings.clone();
        this.nim.setGameState(this.matchCounts);
        this.updateHeaps(this.matchCounts);
        this.hideOverlay();

        new Timer().schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> startGame());
            }
        }, ViewConfig.TIMER_TURN_OVERLAY_SHOW_DURATION);
    }

    /**
     *  Získání stavu hry po hráčově tahu.
     */
    public int[] getGameStatus() {
        int[] gameStatus = new int[this.heapCount];

        for (int i = 0; i < this.heapCount; i++) {
            gameStatus[i] = this.heapControllers[i].computeHeapStatus();
        }

        return  gameStatus;
    }

    /**
     *  Aktualizace hromádek, tj. zobrazení aktuálního stavu.
     */
    private void updateHeaps(int[] gameStatus) {
        for (int i = 0; i < this.heapCount; i++) {
            this.heapControllers[i].updateMatches(gameStatus[i]);
        }
    }

    /**
     *  Setter.
     */
    public void setData(int[] matchCounts) {
        this.heapCount = matchCounts.length;
        this.matchCounts = matchCounts;
        this.matchCountsSettings = matchCounts.clone();
        this.init();
    }

    /**
     * Zobrazuje konec hry.
     */
    public void displayGameOver(String msg) {
        this.lblOverlayMsgTitle.setVisible(true);
        this.lblOverlayMsgMajor.setText(ViewConfig.MSG_GAME_OVER);
        this.lblOverlayMsgMinor.setText(msg);
        this.lblOverlayMsgMinor.setVisible(true);
        this.hboxOverlayControls.setVisible(true);
        this.overlayWrapper.setMouseTransparent(false);
    }


    public void displayTurnStart(String msg) {
        this.lblOverlayMsgTitle.setVisible(false);
        this.lblOverlayMsgMajor.setText(msg);
        this.lblOverlayMsgMinor.setVisible(false);
        this.hboxOverlayControls.setVisible(false);

        this.overlayWrapper.setMouseTransparent(true);
        this.heapWrapper.setDisable(true);
    }

    private void hideOverlay() {
        this.overlayWrapper.setOpacity(0);
    }

    private void showOverlay(int delay) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(ViewConfig.TIMER_TURN_OVERLAY_FADE_DURATION), this.overlayWrapper);
        fadeIn.setFromValue(0.0); fadeIn.setToValue(1.0);
        fadeIn.setDelay(Duration.millis(delay));
        fadeIn.play();
    }

    public void startGame() {
        if(Math.random() >= .5) {
            this.startPCTurn();
        } else {
            this.startPlayerTurn();
        }
    }

}
