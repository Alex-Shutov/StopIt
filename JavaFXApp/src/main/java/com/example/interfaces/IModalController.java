package com.example.interfaces;

import com.example.services.ScreenLoaderService;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public interface IModalController {

    void setScreenController(ScreenLoaderService screenController);
    void openModal();
    void closeModal(ActionEvent event);
    void setStage(Stage stage);
}
