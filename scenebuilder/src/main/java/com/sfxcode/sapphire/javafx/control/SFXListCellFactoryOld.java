package com.sfxcode.sapphire.javafx.control;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class SFXListCellFactoryOld<S> implements Callback<ListView<S>, ListCell<S>> {
    public String property = "";

    public SFXListCellFactoryOld() {

    }

    @Override
    public ListCell<S> call(ListView<S> param) {
        return null;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
