package com.example.controllers;

import com.example.models.shared.RightPanelModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuRightPaneController implements Initializable, InvalidationListener {

//    int i = 0;
    @FXML
    public String paneType;
    public AnchorPane pane;
    public VBox tabBox;

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
        System.out.println(32);
        tabBox.toFront();
//        textField.setText("Just click the button!");
        tabBox.setSpacing(200);
        tabBox.setPadding(new Insets(200));
        AnchorPane.setLeftAnchor(tabBox,200.0);

    }

    public void setPane(Node pane){
        this.pane = (AnchorPane) pane;

    }


    @Override
    public void invalidated(Observable observable) {

        System.out.println(rightPanelModel.getData());
    }

    public void check(MouseEvent event) {
        System.out.println(event);
    }
}