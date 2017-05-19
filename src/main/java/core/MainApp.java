package core;

/**
 * Created by OndrejPittl on 15.05.17.
 */

import config.Routes;
import config.ViewConfig;
import controllers.BoardController;
import javafx.application.Application;
import javafx.application.Platform;
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


    /**
     * Hlavní funkce.
     */
    public static void main(String[] args) {

        // validace vstupních argumentů
        if(!MainApp.parseInputArgs(args)) {
            MainApp.printHelp();
            MainApp.exitApp();
        }

        // spuštění app
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(ViewConfig.MSG_NIM);
        this.initRootLayout();
        this.boardController.startGame();
    }


    /**
     * Inicializace okna aplikace.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(Routes.VIEW_LAYOUT + "playboard.fxml"));
            this.rootLayout = (BorderPane) loader.load();

            this.boardController = loader.getController();
            this.boardController.setData(core.MainApp.matchCounts);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(Routes.VIEW_STYLE + "style.css");

            this.primaryStage.setOnCloseRequest((e) -> MainApp.exitApp());
            this.primaryStage.setScene(scene);
            this.primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Zpracování vstupních argumentů aplikace.
     * Povoleny jsou 4 hromádky o 0 - 10 sirkách,
     * přičemž hromádka s 0 sirkami je ignorována.
     */
    private static boolean parseInputArgs(String[] args){
        int heapCount;
        String command;
        String[] parts;

        if(args.length <= 0)
            return false;

        command = args[0];
        if(command.indexOf(';') <= 0 || command.indexOf(';') >= command.length() - 1)
            return false;

        parts = command.split(";");
        heapCount = parts.length;


        if(heapCount > 4)
            return false;


        MainApp.matchCounts = new int[heapCount];

        int pos = 0;
        for(int i = 0; i < heapCount; i++) {

            int val = Integer.parseInt(parts[i]);

            if(val < 0 || val > 10) {
                return false;
            } else if(val == 0) {
                continue;
            }

            MainApp.matchCounts[pos++] = val;
        }

        return true;
    }

    /**
     *  Nápověda.
     */
    private static void printHelp() {
        System.out.println(
                "******************** " + ViewConfig.MSG_NIM + " ********************\n"
              + "*        !!! Invalid input arguments. !!!        *\n"
              + "**************************************************\n"
              + "* 2 - 4 heaps containing 0 - 10 matches allowed. *\n"
              + "*      example: java -jar nim.jar \"1;2;5;7\"      *\n"
              + "**************************************************"
        );
    }

    /**
     *  Ukončení aplikace.
     */
    public static void exitApp() {
        Platform.exit();
        System.exit(1);
    }

}