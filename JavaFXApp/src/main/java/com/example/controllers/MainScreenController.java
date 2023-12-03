package com.example.controllers;

import com.example.interfaces.IControlledScreen;
import com.example.interfaces.IModalController;
import com.example.services.ScreenLoaderService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;


public class MainScreenController implements IControlledScreen, IModalController {
    private Stage stage;
    private ScreenLoaderService screensController;


    @Override
    public void setScreenController(ScreenLoaderService screenController) {
        this.screensController = screenController;
    }

    @FXML
    @Override
    public void openModal() {
        screensController.openModal("/screens/CustomModal.fxml", "Политика конфиденциальности");
    }

    @FXML
    @Override
    public void closeModal(javafx.event.ActionEvent actionEvent) {
        screensController.closeModal(((Node) actionEvent.getSource()));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setScreenParent(ScreenLoaderService screenParent) {
        screensController = screenParent;
    }

    @FXML
    private void guestLogin() {
        screensController.goToScreen("menu");

    }

    @FXML
    private void login() {
        screensController.goToScreen("menu");
    }
    @FXML
    private void check(){
        System.out.println("123");
    }


//    @FXML
//    private void handleSwipe(SwipeEvent event) {
//        if (event.getGestureSource() == null) {
//            return;
//        }
//
//        if (event.getEventType().equals(SwipeEvent.SWIPE_DOWN) &&
//                event.getSwipeDirection().equals(SwipeEvent.SwipeDirection.DOWN)) {
//            primaryStage.setHeight(600);
//        } else if (event.getEventType().equals(SwipeEvent.SWIPE_UP) &&
//                event.getSwipeDirection().equals(SwipeEvent.SwipeDirection.UP)) {
//            primaryStage.setHeight(400);
//        }
//    }
}
