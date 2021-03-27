package com.sfxcode.sapphire.javafx.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Created by tom on 11.10.15.
 */
public class SFXValueFactoryOld<S, T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    public String property = "";
    public String format = "";

    public SFXValueFactoryOld() {
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        return (ObservableValue<T>) new SimpleStringProperty("Test");
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
