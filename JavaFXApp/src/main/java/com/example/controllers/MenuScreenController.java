package com.example.controllers;

import com.example.App;
import com.example.interfaces.IControlledScreen;
import com.example.models.shared.RightPanelModel;
import com.example.services.ScreenCreatorService;
import com.example.services.ScreenLoaderService;
import com.example.services.ScreenOverlayService;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.controlsfx.control.GridView;

import java.awt.*;

import org.controlsfx.control.ToggleSwitch;
//import com.sun.jna.platform.win32.WinUser.HW;
//import com.sun.jna.platform.win32.WinUser.HWND_BOTTOM;

public class MenuScreenController implements IControlledScreen {


    public HBox profileButton;
    public HBox settingsButton;
    public HBox statsButton;
    public HBox premiumButton;
    public javafx.scene.control.Label blockButtonToogleLabel;
    public StackPane stackPane;
    private ScreenOverlayService screenOverlay = new ScreenOverlayService();

    @FXML

    private Node activeButton;

    @FXML
    private HBox blockButton;

    @FXML
    private FlowPane flowPane;


    @FXML
    private AnchorPane gridPane;


    private final RightPanelModel rightPanelModel = App.RightPanelModel;

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
//        gridPane.toFront();
//        gridPane.set
//        gridPane.setMaxHeight(gridPane.getHeight());
//        gridPane.setMaxWidth(300);
        stackPane.setAlignment(Pos.CENTER_LEFT);

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
        Platform.runLater(()-> {
            if (event.getSource() != activeButton) {
                _stylingHBoxButton(event);
                _activateRightPane(((HBox) event.getSource()).idProperty());
            }
        });
    }

    private void _stylingHBoxButton(MouseEvent event){

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

    private void _activateRightPane(StringProperty id){
        String currentPane = id.toString().toLowerCase().replace("button","");
        rightPanelModel.setData(currentPane);

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
