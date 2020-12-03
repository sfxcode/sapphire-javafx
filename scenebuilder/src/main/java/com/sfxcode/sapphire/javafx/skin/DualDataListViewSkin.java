package com.sfxcode.sapphire.javafx.skin;

import com.sfxcode.sapphire.javafx.control.DataListView;
import com.sfxcode.sapphire.javafx.control.DualDataListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;


public class DualDataListViewSkin<S> extends SkinBase<DualDataListView<S>>{

    public DataListView<S> leftDataListView = new DataListView<S>();
    public DataListView<S> rightDataListView = new DataListView<S>();

    public GridPane gridPane = new GridPane();

    public Button buttonMoveToTarget = new Button("->");
    public Button buttonMoveToTargetAll = new Button("=>");
    public Button buttonMoveToSource = new Button("<-");
    public Button buttonMoveToSourceAll = new Button("<=");


    public VBox buttonBox = new VBox(5);

    private List<String> list = new ArrayList<String>();
    private ObservableList<String> observableList = FXCollections.observableList(list);
    public ListView<String> listView = new ListView<String>(observableList);

    /**
     * Constructor for all SkinBase instances.
     *
     * @param control The control for which this Skin should attach to.
     */
    public DualDataListViewSkin(DualDataListView<S> control) {
        super(control);
        observableList.add("Item 1");
        observableList.add("Item 2");
        observableList.add("Item 3");
        updateView();
    }

    public void updateView() {
        RowConstraints row = new RowConstraints();
        row.setFillHeight(true);
        row.setVgrow(Priority.NEVER);
        gridPane.getRowConstraints().add(row);

        ColumnConstraints col1 = new ColumnConstraints();

        col1.setFillWidth(true);
        col1.setHgrow(Priority.ALWAYS);
        col1.setMaxWidth(Double.MAX_VALUE);
        col1.setPrefWidth(200);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setFillWidth(true);
        col2.setHgrow(Priority.NEVER);
        col2.setMaxWidth(50);
        col2.setMinWidth(50);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setFillWidth(true);
        col3.setHgrow(Priority.ALWAYS);
        col3.setMaxWidth(Double.MAX_VALUE);
        col3.setPrefWidth(200);

        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setFillWidth(true);
        buttonBox.getChildren().addAll(buttonMoveToTarget, buttonMoveToTargetAll, buttonMoveToSource, buttonMoveToSourceAll);
        buttonBox.setMinHeight(100);

        gridPane.add(leftDataListView, 0, 0);
        gridPane.add(buttonBox, 1, 0);
        gridPane.add(rightDataListView, 2, 0);

        getChildren().add(gridPane);

    }

}
