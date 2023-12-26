package com.example.components;

import java.io.IOException;

import com.example.App;
import com.example.controllers.MenuRightPaneController;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class MenuRightPaneList extends Pane {

    private Node view;
    private static  MenuRightPaneController controller;

    public MenuRightPaneList() {
        if (controller == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/components/MenuRightPaneList.fxml"));
            controller = new MenuRightPaneController(App.RightPanelModel);
            fxmlLoader.setController(controller);
            try {
                view = (Node) fxmlLoader.load();
                controller.setPane(view);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {
            view = controller.pane; // Возвратим view из ранее созданного контроллера
        }

        getChildren().add(view);
    }

    public void setWelcome(String str) {
//        controller.textField.setText(str);
    }

    public Node getView() {
        return view;
    }

    public String getWelcome() {
//        return controller.textField.getText();
        return null;
    }

    public StringProperty welcomeProperty() {
//        return controller.textField.textProperty();
        return null;
    }
}