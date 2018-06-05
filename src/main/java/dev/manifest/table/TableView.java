package dev.manifest.table;

import javax.swing.*;

public class TableView extends JTable {


    public TableView() {
        super();
        setModel(TableModel.getInstance());
    }

}
