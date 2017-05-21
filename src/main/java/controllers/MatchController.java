package controllers;

import config.Routes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import model.MatchState;

import java.net.URL;
import java.util.ResourceBundle;

public class MatchController implements Initializable {

    /**
     *  Index sirky, nad níž je myš.
     */
    private static int hoveringIndex;

    /**
     *  Index aktuální sirky.
     */
    private int index;

    /**
     *  Stav aktuální sirky.
     */
    private MatchState state;

    /**
     *  Controller hromádky.
     */
    private HeapController heapController;

    /**
     *  View komponenty.
     */
    @FXML
    private ImageView imgView;

    @FXML
    private BorderPane matchContainer;


    public void initialize(URL location, ResourceBundle resources) {
        this.updateState(MatchState.VISIBLE);
    }

    /**
     *  Aktualizace stavu sirky.
     */
    public void updateState(MatchState state) {
        this.state = state;

        switch (this.state) {
            case HIDDEN:
                this.matchContainer.setVisible(false);
                this.matchContainer.setManaged(false);
                break;
            case HOVERED:
                this.imgView.setImage(new Image(Routes.VIEW_IMG + "match_hovered.png"));
                break;
            default:
            case VISIBLE:
                this.imgView.setImage(new Image(Routes.VIEW_IMG + "match.png"));
                this.matchContainer.setVisible(true);
                this.matchContainer.setManaged(true);
                break;
        }
    }

    /**
     *  Přizpůsobení velikosti.
     */
    public void updateComponent(int heapWidth) {
        int paddings = 30;
        this.imgView.setFitWidth(heapWidth  - paddings);
    }

    /**
     *  Setter.
     */
    public void setData(int index, HeapController heapController){
        this.index = index;
        this.heapController = heapController;
    }

    /**
     *  Obsluha vstupu myši na sirku.
     */
    public void handleMouseEntered() {
        MatchController.hoveringIndex = this.index;
        this.heapController.proceedMatchHover();
    }

    /**
     *  Obluha opuštění sirky myší.
     */
    public void handleMouseExited() {
        MatchController.hoveringIndex = -1;
        this.heapController.proceedMatchHover();
    }

    /**
     *  Obsluha kliknutí myši.
     */
    public void handleMouseClick() {
        this.handleMouseExited();
        this.heapController.proceedMatchSelect(this.index);
    }

    /**
     *  Getter.
     */
    public MatchState getState() {
        return state;
    }

    /**
     *  Getter.
     */
    public static int getHoveringIndex() {
        return hoveringIndex;
    }
}
