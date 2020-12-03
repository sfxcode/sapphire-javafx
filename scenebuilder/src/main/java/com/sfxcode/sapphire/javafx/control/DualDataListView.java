package com.sfxcode.sapphire.javafx.control;

import com.sfxcode.sapphire.javafx.skin.DataListViewSkin;
import com.sfxcode.sapphire.javafx.skin.DualDataListViewSkin;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;


public class DualDataListView<S> extends Control {


    public DualDataListView() {
        getStyleClass().add("dual-data-list-view");
    }

    protected Skin<DualDataListView<S>> createDefaultSkin() {
        return new DualDataListViewSkin<S>(this);
    }

    @Override public String getUserAgentStylesheet() {
        return getClass().getResource("dualdatalistview.css").toExternalForm();
    }
}
