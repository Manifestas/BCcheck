package dev.manifest.table;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableModel extends AbstractTableModel {

    private static final int COLUMN_COUNT = 4;
    private static final int LAST_COLUMN_VALUE = 1;

    private List<Product> productList = new ArrayList<>();
    @Override
    public int getRowCount() {
        return productList.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Product product = productList.get(row);
        switch (column) {
            case 0:
                return product.getName();
            case 1:
                return product.getColor();
            case 2:
                return product.getSize();
            case 3:
                return LAST_COLUMN_VALUE;
            default:
                throw new IndexOutOfBoundsException("Column index value must be in 0-" + (COLUMN_COUNT - 1) + " range.");
        }
    }
}
