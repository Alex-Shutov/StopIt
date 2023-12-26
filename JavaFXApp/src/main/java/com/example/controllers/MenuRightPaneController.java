package com.example.controllers;

import com.example.models.shared.RightPanelModel;
import com.example.services.ScreenClassifierService;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuRightPaneController implements Initializable, InvalidationListener {

//    int i = 0;
    @FXML
    public String paneType;
    public TextField settingsText;
    public AnchorPane pane;
    public VBox tabBox;

    public VBox prevBox;

    public RightPanelModel rightPanelModel;

    @FXML
    protected void doSomething() {
//        textField.setText("The button was clicked #" + ++i);
    }

    public MenuRightPaneController(RightPanelModel sharedDataModel) {
        this.rightPanelModel = sharedDataModel;
        sharedDataModel.addListener(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFormatter<Integer> formatter = new TextFormatter<>(
                change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("\\d*") && newText.length() <= 3) {
                        try {
                            int value = newText.isEmpty() ? 0 : Integer.parseInt(newText);
                            return change;
                        } catch (NumberFormatException e) {
                            // Введено не число
                            return null;
                        }
                    } else {
                        // Введен недопустимый символ
                        return null;
                    }
                }
        );

        settingsText.setTextFormatter(formatter);
        settingsText.textProperty().addListener((observable, oldValue, newValue) -> {
            // Вызываем ваш сервис с новым значением
            if(newValue!=null) {
                ScreenClassifierService.updateThreshold(Double.parseDouble(observable.getValue()));
            }
        });

        tabBox.toFront();
        tabBox.setPadding(new Insets(80,0,0,0));
        tabBox.getChildren().forEach((element)->{

        });
        clearPreviousContainers();
//        textField.setText("Just click the button!");
//        tabBox.setSpacing(200);

        AnchorPane.setLeftAnchor(tabBox,200.0);

    }

    public void setPane(Node pane){
        this.pane = (AnchorPane) pane;

    }

    private boolean isValidInput(String input) {
        try {
            int value = Integer.parseInt(input);
            return value >= 0 && value <= 100;
        } catch (NumberFormatException e) {
            // Введено не число
            return false;
        }
    }


    @Override
    public void invalidated(Observable observable) {

//        System.out.println(rightPanelModel.getData());
        Node currentPane = chooseCurrentPane(rightPanelModel.getData());
        displayContainer(currentPane);
    }

    private void setNodeVisability(Node node,boolean visible){
        node.setManaged(visible);
        node.setVisible(visible);
    }
    private void displayContainer(Node currentPane){
        clearPreviousContainers();
        setNodeVisability(currentPane,true);
    }
    private void clearPreviousContainers(){
        tabBox.getChildren().forEach((element)->{
            setNodeVisability(element,false);
        });
    }

    private Node chooseCurrentPane(String id){
        System.out.println(tabBox.getChildren().filtered((x)-> x.getId() != null && x.getId().contains(id)).getFirst());
        return tabBox.getChildren().filtered((x)-> x.getId() != null && x.getId().contains(id)).getFirst();
    }


}