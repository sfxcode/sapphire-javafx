package com.sfxcode.sapphire.javafx.control;

import com.sfxcode.sapphire.javafx.skin.SFXDualDataListViewSkin;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;


public class SFXDualDataListView<S> extends Control {


    public SFXDualDataListView() {
        getStyleClass().add("dual-data-list-view");
    }

    protected Skin<SFXDualDataListView<S>> createDefaultSkin() {
        return new SFXDualDataListViewSkin<S>(this);
    }

    @Override public String getUserAgentStylesheet() {
        return getClass().getResource("dualdatalistview.css").toExternalForm();
    }
}
