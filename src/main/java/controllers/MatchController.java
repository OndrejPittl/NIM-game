package controllers;

import config.Routes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import model.MatchState;

import java.net.URL;
import java.util.ResourceBundle;

public class MatchController implements Initializable {

    private static int hoveringIndex;

    private int index;

    private MatchState state;


    private HeapController heapController;

    @FXML
    private ImageView imgView;

//    @FXML
//    private Label lbl;

    @FXML
    private BorderPane matchContainer;


    public void initialize(URL location, ResourceBundle resources) {
        this.updateState(MatchState.VISIBLE);
    }

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

    public void updateComponent(int heapWidth) {
        int paddings = 30;
        this.imgView.setFitWidth(heapWidth  - paddings);
    }


    public void setData(int index, HeapController heapController){
        this.index = index;
        this.heapController = heapController;
//        this.lbl.setText(String.valueOf(index));
    }

    public void handleMouseEntered() {
        //System.out.println("entered: " + this.index);
        MatchController.hoveringIndex = this.index;
        this.heapController.proceedMatchHover();
    }

    public void handleMouseExited() {
        //System.out.println("left: " + this.index);
        MatchController.hoveringIndex = -1;
        this.heapController.proceedMatchHover();
    }

    public void handleMouseClick() {
        this.handleMouseExited();
        this.heapController.proceedMatchSelect(this.index);
    }


    public MatchState getState() {
        return state;
    }

    public static int getHoveringIndex() {
        return hoveringIndex;
    }
}
