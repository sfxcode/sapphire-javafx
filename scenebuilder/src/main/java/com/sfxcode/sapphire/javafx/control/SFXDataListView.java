package com.sfxcode.sapphire.javafx.control;

import com.sfxcode.sapphire.javafx.skin.SFXDataListViewSkin;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;


public class SFXDataListView<S> extends Control {


    public SFXDataListView() {
        getStyleClass().add("data-list-view");
    }

    protected Skin<SFXDataListView<S>> createDefaultSkin() {
        return new SFXDataListViewSkin<S>(this);
    }

    @Override public String getUserAgentStylesheet() {
        return getClass().getResource("datalistview.css").toExternalForm();
    }
}
