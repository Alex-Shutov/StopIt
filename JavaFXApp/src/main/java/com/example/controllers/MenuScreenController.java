package com.example.controllers;

import com.example.interfaces.IControlledScreen;
import com.example.services.ScreenCreatorService;
import com.example.services.ScreenLoaderService;
import com.example.services.ScreenOverlayService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.GridView;

import java.awt.*;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.controlsfx.control.ToggleSwitch;
//import com.sun.jna.platform.win32.WinUser.HW;
//import com.sun.jna.platform.win32.WinUser.HWND_BOTTOM;

public class MenuScreenController implements IControlledScreen {


    private ScreenOverlayService screenOverlay = new ScreenOverlayService();
    private ScreenCreatorService screenCreator = new ScreenCreatorService();
    @FXML
    private GridView<Label> gridView; // Замените String на тип вашего элемента

    @FXML
    private GridView<Label> mainContentView;

    @FXML
    private VBox menuVBox;

    private Node activeButton;

    @FXML
    private VBox blockButton;

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
        menuVBox.getChildren().forEach(node -> {
            if (node instanceof Button) {
                ((Button) node).setStyle("-fx-background-color: grey;");
            }
        });
    }

    public void initialize() {
        blockButton.requestFocus();
        screenOverlay = new ScreenOverlayService();
        screenshotSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(()->{
                this.screenshotByTimer(oldValue,newValue);
            });
        });
    }

    // Обработчик нажатия
    public void onVBoxPressed(MouseEvent event) {
        if(activeButton!=null){
            activeButton.setStyle("");
        }
        VBox vbox = (VBox) event.getSource();

        vbox.setStyle("-fx-background-color: #FF4C00;");

        activeButton = vbox;
    }

    public void blurScreen() {
        screenOverlay.blurScreen();
    }


    public void screenshotByTimer(boolean oldValue, boolean newValue) {

        if(!newValue){
            ScreenCreatorService.stopScreenshotCreation();

        }
        else {

            ScreenCreatorService.startScreenshotCreation();

        }
    }
}
