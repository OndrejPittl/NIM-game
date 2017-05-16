package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class OverlayController {


    @FXML
    private BorderPane overlayWrapper;

    @FXML
    private Label lblMsg;



    public OverlayController(BorderPane overlayWrapper, Label lblMsg) {
        this.overlayWrapper = overlayWrapper;
        this.lblMsg = lblMsg;
        this.toggleOverlayVisibility(false);
    }

    public void displayGameOver(String msg) {
        this.lblMsg.setText(msg);
        this.toggleOverlayVisibility(true);
    }

    private void toggleOverlayVisibility(boolean visible) {
        this.overlayWrapper.setManaged(visible);
        this.overlayWrapper.setVisible(visible);
    }

    public void displayTurnStart() {

    }

}

