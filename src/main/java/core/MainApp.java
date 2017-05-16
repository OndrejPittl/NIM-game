package core;

/**
 * Created by OndrejPittl on 15.05.17.
 */

import config.Routes;
import controllers.BoardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    /**
     * Počet sirek na hromádkách.
     */
    private static int[] matchCounts;


    /**
     * View atributy.
     */
    private Stage primaryStage;
    private BorderPane rootLayout;
    private BoardController boardController;



    public static void main(String[] args) {
        MainApp.parseInputArgs(args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Nim Game");
        this.initRootLayout();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(Routes.VIEW_LAYOUT + "playboard.fxml"));
            this.rootLayout = (BorderPane) loader.load();

            this.boardController = loader.getController();
            this.boardController.setData(core.MainApp.matchCounts);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(Routes.VIEW_STYLE + "style.css");
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean parseInputArgs(String[] args){
        int heapCount;
        String command;
        String[] parts;

        if(args.length <= 0)
            return false;

        if((command = args[0]).indexOf(';') < 0)
            return false;

        parts = command.split(";");
        heapCount = parts.length;
        MainApp.matchCounts = new int[heapCount];

        int pos = 0;
        for(int i = 0; i < heapCount; i++) {

            int val = Integer.parseInt(parts[i]);

            if(val < 0) {
                return false;
            } else if(val == 0) {
                continue;
            }

            MainApp.matchCounts[pos++] = val;
        }

        return true;
    }

}