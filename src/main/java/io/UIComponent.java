package io;


import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class UIComponent {

    /**
     *  Komponenta.
     */
    private Parent root;

    /**
     *  Kontroller komponenty.
     */
    private Initializable controller;

    /**
     *  Kontruktor.
     */
    public UIComponent(Parent root, Initializable controller) {
        this.root = root;
        this.controller = controller;
    }

    /**
     *  Getter.
     */
    public Parent getRoot() {
        return root;
    }

    /**
     *  Getter.
     */
    public Initializable getController() {
        return controller;
    }
}
