package com.example.models.shared;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Pattern regex = Pattern.compile("value: (\\w+)");
        Matcher matcher = regex.matcher(data.getValue());

        return matcher.find() ? matcher.group(1) : ""
                ;
    }

    public void setData(String newData) {
        data.setValue(newData);
    }
}
