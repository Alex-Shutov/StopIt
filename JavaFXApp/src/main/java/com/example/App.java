package com.example;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import com.example.controllers.MainScreenController;
import com.example.controllers.MenuScreenController;
import com.example.models.shared.RightPanelModel;
import com.example.services.ScreenCreatorService;
import com.example.services.ScreenLoaderService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;


public class App extends Application {

    public static RightPanelModel RightPanelModel = new RightPanelModel();

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.initStyle(StageStyle.UNDECORATED);

            Runtime.getRuntime().addShutdownHook(new Thread(ScreenCreatorService::stopScreenshotCreation));

            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/screens/MainScreen.fxml"));
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/screens/MenuScreen.fxml"));
            menuLoader.load();
            Parent root = mainLoader.load();


            ScreenLoaderService mainContainer = new ScreenLoaderService(primaryStage);

            initialize(mainContainer);

            FXTrayIcon trayIcon = new FXTrayIcon(primaryStage, Objects.requireNonNull(getClass().getResource("/images/logo_tray.png")));
            trayIcon.addExitItem("Exit",e->{
                ScreenCreatorService.stopScreenshotCreation();
                Platform.exit();
            });
            trayIcon.show();


            primaryStage.setTitle("JavaFX App");
            primaryStage.setScene(new Scene(root, 700, 500));
            primaryStage.setResizable(false);



            mainContainer.setScreen("main");

            MainScreenController mainScreenController = mainLoader.getController();
            mainScreenController.setStage(primaryStage);


            MenuScreenController controller = menuLoader.getController();
            controller.setPrimaryStage(primaryStage);

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
