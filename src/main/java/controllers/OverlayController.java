package controllers;


import config.ViewConfig;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import static config.ViewConfig.TIMER_TURN_OVERLAY_FADE_DURATION;
import static config.ViewConfig.TIMER_TURN_OVERLAY_SHOW_DURATION;

public class OverlayController {

//    private boolean visible = true;
//
//
//    @FXML
//    private BorderPane overlayWrapper;
//
//    private Label lblOverlayMsgMajor;
//
//    private Label lblOverlayMsgMinor;
//
//    private Label lblOverlayMsgTitle;
//
//    private HBox hboxOverlayControls;
//
//
//
//    public OverlayController (
//            BorderPane overlayWrapper,
//            Label lblOverlayMsgMajor,
//            Label lblOverlayMsgMinor,
//            Label lblOverlayMsgTitle,
//            HBox hboxOverlayControls) {
//
//        this.overlayWrapper = overlayWrapper;
//        this.lblOverlayMsgMajor = lblOverlayMsgMajor;
//        this.lblOverlayMsgMinor = lblOverlayMsgMinor;
//        this.lblOverlayMsgTitle = lblOverlayMsgTitle;
//        this.hboxOverlayControls = hboxOverlayControls;
//
//        this.overlayWrapper.setMouseTransparent(true);
//        this.toggleOverlayVisibility(false);
//    }
//
//    public void displayGameOver(String msg) {
//        this.lblOverlayMsgTitle.setVisible(true);
//        this.lblOverlayMsgMajor.setText(ViewConfig.MSG_GAME_OVER);
//        this.lblOverlayMsgMinor.setText(msg);
//        this.lblOverlayMsgMinor.setVisible(true);
//        this.hboxOverlayControls.setVisible(true);
//        this.toggleOverlayVisibility(true);
//    }
//
//    private void toggleOverlayVisibility(boolean visible) {
//        this.overlayWrapper.setManaged(visible);
//        this.overlayWrapper.setVisible(visible);
//    }
//
//    public void displayTurnStart(String msg) {
//        this.lblOverlayMsgTitle.setVisible(false);
//        this.lblOverlayMsgMajor.setText(msg);
//        this.lblOverlayMsgMinor.setVisible(false);
//        this.hboxOverlayControls.setVisible(false);
//        this.toggleOverlayVisibility(true);
//
//        this.blinkOverlay(0);
//
//
//    }
//
//    private void hideOverlay() {
//        this.toggleOverlayVisibility(false);
//    }
//
//    private void blinkOverlay(int delay) {
//        if(!this.visible)
//            this.showOverlay(delay);
//        this.hideOverlay(delay);
//
//
////        fadeIn.setOnFinished((e) -> {
////            new Timer().schedule(new TimerTask() {
////                public void run() {
////
////                }
////            }, ViewConfig.TIMER_TURN_OVERLAY_SHOW_DURATION);
////        });
//
//    }
//
//    private void showOverlay(int delay) {
//        FadeTransition fadeIn = new FadeTransition(Duration.millis(ViewConfig.TIMER_TURN_OVERLAY_FADE_DURATION), this.overlayWrapper);
//        fadeIn.setFromValue(0.0); fadeIn.setToValue(1.0);
//        fadeIn.setDelay(Duration.millis(delay));
//        fadeIn.play();
//    }
//
//    private void hideOverlay(int delay) {
//        FadeTransition fadeOut = new FadeTransition(Duration.millis(ViewConfig.TIMER_TURN_OVERLAY_FADE_DURATION), this.overlayWrapper);
//        fadeOut.setFromValue(1.0); fadeOut.setToValue(0.0);
//        fadeOut.setOnFinished((e) -> {
//            this.overlayWrapper.setVisible(false);
//            this.visible = false;
//        });
//        fadeOut.setDelay(Duration.millis(delay + ViewConfig.TIMER_TURN_OVERLAY_FADE_DURATION + ViewConfig.TIMER_TURN_OVERLAY_SHOW_DURATION));
//        fadeOut.play();
//    }


}

