package dev.manifest.bccheck.table;

import javax.swing.*;

public class Table extends JTable {


    public Table() {
        super();
        setModel(TableModel.getInstance());
        // you can select one cell, rather than the entire row
        setCellSelectionEnabled(true);
    }

}
