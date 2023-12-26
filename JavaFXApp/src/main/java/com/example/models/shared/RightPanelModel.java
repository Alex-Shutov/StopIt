package com.example.models.shared;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RightPanelModel implements Observable {

    private final StringProperty data = new SimpleStringProperty(this, "", "");
    @Override
    public void addListener(InvalidationListener invalidationListener) {
            data.addListener(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        data.removeListener(invalidationListener);
    }

    public String getData() {
        return data.getValueSafe();
    }

    public void setData(String newData) {
        data.setValue(newData);
    }
}
