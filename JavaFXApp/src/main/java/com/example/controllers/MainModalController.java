package com.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainModalController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label textLabel;

    private Stage stage;

    public void initialize() {
        // Инициализация при загрузке
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void close() {
        // Закрытие окна
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    private void openModal() {
        // Открытие нового модального окна
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("path/to/CustomModal.fxml"));
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Политика конфиденциальности");

            MainModalController controller = loader.getController();
            controller.setStage(modalStage);

            Window currentWindow = titleLabel.getScene().getWindow();
            modalStage.initOwner(currentWindow);

            modalStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Другие методы и инициализация по необходимости

}