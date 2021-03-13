package com.sfxcode.sapphire.javafx.skin;

import com.sfxcode.sapphire.javafx.control.SFXDataListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


public class SFXDataListViewSkin<S> extends SkinBase<SFXDataListView<S>>{

    public VBox box = new VBox(5);
    public TextField textField = new TextField("Search Field");
    public Label footerLabel = new Label("DataListView Footer");

    private List<String> list = new ArrayList<String>();
    private ObservableList<String> observableList = FXCollections.observableList(list);
    public ListView<String> listView = new ListView<String>(observableList);

    /**
     * Constructor for all SkinBase instances.
     *
     * @param control The control for which this Skin should attach to.
     */
    public SFXDataListViewSkin(SFXDataListView<S> control) {
        super(control);
        observableList.add("Item 1");
        observableList.add("Item 2");
        observableList.add("Item 3");
        updateView();
    }

    public void updateView() {
        getChildren().add(box);
        box.getChildren().add(textField);
        box.getChildren().add(listView);
        box.getChildren().add(footerLabel);
    }

}
