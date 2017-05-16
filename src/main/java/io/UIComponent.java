package io;


import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class UIComponent {

    private Parent root;

    private Initializable controller;

    public UIComponent(Parent root, Initializable controller) {
        this.root = root;
        this.controller = controller;
    }

    public Parent getRoot() {
        return root;
    }

    public Initializable getController() {
        return controller;
    }

    public void setController(Initializable controller) {
        this.controller = controller;
    }
}
