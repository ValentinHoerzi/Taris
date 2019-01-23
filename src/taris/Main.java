/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taris;

import java.io.*;
import javafx.application.*;
import static javafx.application.Application.launch;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.stage.*;

/**
 * @author Valentin :: Display Name? :: Make programm user friendly :: BUG: If
 * User type : <logout> -> serverbug ::Test: Simuliere Client und Teste
 * User encryption
 * Ergebnisse
 */
public class Main extends Application {

    private static FXML_Controller controller;

    @Override
    public void start(Stage stage) throws IOException {
        //Loads essential parts of the Client
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);

        //Sets PLATO as Application Titel
        stage.setTitle("Taris");

        //Adds icon as Programmicon
        Image image = new Image("/Images/mars.png");
        stage.getIcons().add(image);

        //Exits Programm when Window closed
        stage.setOnCloseRequest((e) -> {
            Platform.exit();
            System.exit(0);
        });

        //Sets up  Stage
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static FXML_Controller getController() {
        return controller;
    }
}
