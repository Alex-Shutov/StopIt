package com.example;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import com.example.controllers.MainScreenController;
import com.example.services.ScreenCreatorService;
import com.example.services.ScreenLoaderService;
import dorkbox.systemTray.SystemTray;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;


public class App extends Application {


    @Override
    public void start(Stage primaryStage) {
        try {

            Runtime.getRuntime().addShutdownHook(new Thread(ScreenCreatorService::stopScreenshotCreation));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/MainScreen.fxml"));
            Parent root = loader.load();

            ScreenLoaderService mainContainer = new ScreenLoaderService(primaryStage);

            initialize(mainContainer);

            FXTrayIcon trayIcon = new FXTrayIcon(primaryStage, Objects.requireNonNull(getClass().getResource("/images/logo_tray.png")));
            trayIcon.addExitItem("Exit",e->{
                ScreenCreatorService.stopScreenshotCreation();
                Platform.exit();
            });
            trayIcon.show();


            primaryStage.setTitle("JavaFX App");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.setResizable(false);



            mainContainer.setScreen("main");

            MainScreenController mainScreenController = loader.getController();
            mainScreenController.setStage(primaryStage);




            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void initialize(ScreenLoaderService screenController){
        screenController.loadScreen("main", "/screens/MainScreen.fxml");
        screenController.loadScreen("menu", "/screens/MenuScreen.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
