package com.example.controllers;

import com.example.interfaces.IControlledScreen;
import com.example.services.ScreenCreatorService;
import com.example.services.ScreenLoaderService;
import com.example.services.ScreenOverlayService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.controlsfx.control.GridView;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import org.controlsfx.control.ToggleSwitch;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;
//import com.sun.jna.platform.win32.WinUser.HW;
//import com.sun.jna.platform.win32.WinUser.HWND_BOTTOM;

public class MenuScreenController implements IControlledScreen {


    public HBox profileButton;
    public HBox settingsButton;
    public HBox statsButton;
    public HBox premiumButton;
    public javafx.scene.control.Label blockButtonToogleLabel;
    private ScreenOverlayService screenOverlay = new ScreenOverlayService();
    //private ScreenCreatorService screenCreator = new ScreenCreatorService();
    @FXML
    private GridView<Label> gridView; // Замените String на тип вашего элемента

    @FXML
    private GridView<Label> mainContentView;

    @FXML
    private VBox menuVBox;

    private Node activeButton;

    @FXML
    private HBox blockButton;

    @FXML
    private FlowPane flowPane;

    @FXML
    private BorderPane borderLeft;

    @FXML
    private AnchorPane gridPane;
    @FXML
    private VBox test;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private ToggleSwitch screenshotSwitch;


    private ScreenLoaderService screensController;
    private Stage primaryStage;

    private double xOffset = 0;
    private double yOffset = 0;


    @Override
    public void setScreenParent(ScreenLoaderService screenParent) {
        screensController = screenParent;
    }

    @FXML
    private void handleButton1(MouseEvent event) {
        // TODO: Add logic for button 1
//        screensController.goToScreen("main");
        VBox vbox = (VBox) event.getSource();
        vbox.setStyle("-fx-background-color:#FF4C00;");
//        resetButtons();
    }

    @FXML
    private void handleButton2(ActionEvent event) {
        // TODO: Add logic for button 2
    }

    @FXML
    private void handleButton3(ActionEvent event) {
        // TODO: Add logic for button 3

    }

    @FXML
    private void handleButton4(ActionEvent event) {
        // TODO: Add logic for button 4

    }

    @FXML
    private void handleButton5(ActionEvent event) {
        // TODO: Add logic for button 5

    }

    @FXML
    private void handleButton6(ActionEvent event) {
        // TODO: Add logic for button 6
        resetButtons();
        button6.setStyle("-fx-background-color: orange;");
    }

    private void resetButtons() {
//        menuVBox.getChildren().forEach(node -> {
//            if (node instanceof Button) {
//                ((Button) node).setStyle("-fx-background-color: grey;");
//            }
//        });
    }

    public void initialize() {
//        blockButton.requestFocus();
//        screenOverlay = new ScreenOverlayService();
////        menuVBox.setPadding(new Insets(0));
////        menuVBox.setSpacing(0);
        gridPane.toFront();
//        gridPane.set

        blockButton.setPickOnBounds(false);
//        blockButton.setBorder(null);
//
//        test.setMaxWidth(Double.MAX_VALUE);
//        test.setMaxHeight(Double.MAX_VALUE);
//        GridPane.setHgrow(test, Priority.ALWAYS);
//        GridPane.setVgrow(test, Priority.ALWAYS);
        screenshotSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(()->{
                this.screenshotByTimer(oldValue,newValue);
            });
        });
//
//        blockButton.setBorder(new Border(new));
        gridPane.setPadding(new Insets(0,0,0,0));
        flowPane.setMinHeight(gridPane.getHeight());
        flowPane.setPadding(new Insets(0,0,200,0));
//        screenshotSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
//            Platform.runLater(()->{
//                this.screenshotByTimer(oldValue,newValue);
//            });
//        });


        gridPane.setOnMousePressed(event -> {
            if (event.getY() < 50) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        gridPane.setOnMouseDragged(event -> {
            if (event.getY() < 50) {
                gridPane.getScene().getWindow().setX(event.getScreenX() - xOffset);
                gridPane.getScene().getWindow().setY(event.getScreenY() - yOffset);
            }
        });

    }

    public void setPrimaryStage(Stage primaryStage){

       this.primaryStage = primaryStage;

//        gridPane.add(closeButton, 1, 0);
//        gridPane.add(minimizeButton, 0, 0);
    }

    // Обработчик нажатия
    public void onHBoxPressed(MouseEvent event) {
        if(activeButton!=null){
            activeButton.setStyle("-fx-padding: 10px 10px 10px 12px;");
            HBox hBoxactive = (HBox) activeButton;
            HBox inner = (HBox) hBoxactive.getChildren().getFirst();
            inner.getChildren().remove(0);
        }

        HBox activeBar = new HBox();
        activeBar.setStyle("-fx-background-color: #FF4C00; -fx-max-width: 5px; -fx-pref-width: 5px; -fx-max-height: 18px; -fx-pref-height: 18px;-fx-background-insets: 0, 1, 2;-fx-border-radius: 1.0em;");
        HBox hbox = (HBox) event.getSource();
        HBox hboxInner =(HBox) hbox.getChildren().getFirst();
        hboxInner.getChildren().addFirst(activeBar);
        Node label = hboxInner.getChildren().getLast();
        label.setLayoutX(10);

        hbox.setStyle("-fx-padding: 10px 10px 10px 10px;");
//        hbox.setStyle("-fx-background-color: #FF4C00;");

        activeButton = hbox;
    }

    public void blurScreen() {
        screenOverlay.blurScreen();
    }


    public void screenshotByTimer(boolean oldValue, boolean newValue) {

        if(!newValue){
            ScreenCreatorService.stopScreenshotCreation();
            blockButtonToogleLabel.setText("Откл.");
        }
        else {

            ScreenCreatorService.startScreenshotCreation();
            blockButtonToogleLabel.setText("Вкл.");

        }
    }

    @FXML
    private void closeButtonAction() {

        Platform.runLater(()->{
            Stage t =(Stage)gridPane.getScene().getWindow();
            t.close();
        });
//        primaryStage.close();
    }

    @FXML
    private void minimizeButtonAction() {
//        Platform.runLater(()->primaryStage.setIconified(true););
//        primaryStage.setIconified(true);
    }
}
