package io;


import config.Routes;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.io.InputStream;

public class DataLoader {

    /**
     *  Načítá FXML dílčí soubor.
     */
    public static UIComponent loadPartialLayout(String path) {
        InputStream in = core.MainApp.class.getResourceAsStream(Routes.VIEW_LAYOUT_PARTIAL + path);
        return DataLoader.loadFXML(in);
    }

    /**
     *  Načítá FXML soubor.
     */
    private static UIComponent loadFXML(InputStream in) {
        FXMLLoader loader = new FXMLLoader();

        try {
            BorderPane root = (BorderPane) loader.load(in);
            in.close();
            return new UIComponent(root, (Initializable) loader.getController());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
