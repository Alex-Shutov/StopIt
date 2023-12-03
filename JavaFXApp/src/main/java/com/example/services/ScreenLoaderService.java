package com.example.services;

import com.example.interfaces.IControlledScreen;
import com.example.interfaces.IModalController;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class ScreenLoaderService {
    private final HashMap<String, Parent> screens = new HashMap<>();
    private final HashMap<String, URL> resources = new HashMap<>();
    private final HashMap<String, IControlledScreen> controllers = new HashMap<>();
    private final Stage primaryStage;
    private IModalController activeModalController;

    public ScreenLoaderService(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void addScreen(String name, Parent screen, IControlledScreen controller) {
        screens.put(name, screen);
        controllers.put(name, controller);
    }

    public Parent getScreen(String name) {
        return screens.get(name);
    }

    public boolean loadScreen(String name, String resource) {
        try {
            addResource(name,resource);
            FXMLLoader loader = new FXMLLoader(resources.get(name));
            Parent loadScreen = loader.load();
            IControlledScreen controller = loader.getController();
            controller.setScreenParent(this);
            addScreen(name, loadScreen, controller);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loadScreen(String name) {
        try {
            System.out.println(resources.get(name));
            if(resources.containsKey(name)) {
                FXMLLoader loader = new FXMLLoader(resources.get(name));
                Parent loadScreen = loader.load();
                IControlledScreen controller = loader.getController();
                controller.setScreenParent(this);
                addScreen(name, loadScreen, controller);
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void setScreen(String name) {
        Parent newScreen = screens.get(name);

        if (newScreen != null) {
            Scene scene = new Scene(newScreen,800,600);
            primaryStage.setScene(scene);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), newScreen);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            // Если вы хотите использовать анимацию сдвига, можете раскомментировать следующий код
            // TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), newScreen);
            // translateTransition.setFromX(-scene.getWidth());
            // translateTransition.setToX(0);
            // translateTransition.play();
        } else {
            System.out.println("Screen hasn't been loaded!\n");
        }
    }

    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

    public IControlledScreen getController(String name) {
        return controllers.get(name);
    }

    private void addResource(String name, String resource){
        if(!this.resources.containsKey(name))
            this.resources.put(name,getClass().getResource(resource));
    }

    public void goToScreen(String name){
        if(loadScreen(name))
            setScreen(name);
    }

    public void openModal(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle(title);

            IModalController controller = loader.getController();
            controller.setScreenController(this);
            controller.setStage(modalStage);

            Window currentWindow = primaryStage;
            modalStage.initOwner(currentWindow);

            Scene scene = new Scene(root,400,300);
            modalStage.setScene(scene);

            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeModal(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    public void setModalController(IModalController modalController) {
        this.activeModalController = modalController;
    }
}
