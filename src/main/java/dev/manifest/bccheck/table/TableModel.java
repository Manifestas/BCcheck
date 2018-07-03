package dev.manifest.bccheck.table;

import dev.manifest.bccheck.util.MidiPlayer;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TableModel extends AbstractTableModel {

    private final static Logger log = Logger.getLogger(TableModel.class.getName());

    private static TableModel instance;
    private TableModel(){

    }

    public static TableModel getInstance() {
        if (instance == null) {
            instance = new TableModel();
        }
        return instance;
    }

    /** total number of columns in the table. */
    private static final int COLUMN_COUNT = 4;
    /** value of the "quantity" column for easy import to TradeX. */
    private static final int LAST_COLUMN_VALUE = 1;

    private final String[] columnNames = {"Модель", "Цвет", "Размер", "Кол-во"};

    /** List for storing table records. */
    private List<Product> productList = new ArrayList<>();

    /**
     * Returns the number of rows in the model. A JTable uses this method to determine how many rows it should display.
     * This method should be quick, as it is called frequently during rendering.
     * @return the number of rows in the model.
     */
    @Override
    public synchronized int getRowCount() {
        return productList.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    /**
     * Returns the value for the cell at columnIndex and rowIndex.
     * @param rowIndex the row whose value is to be queried.
     * @param columnIndex the column whose value is to be queried.
     * @return the value Object at the specified cell.
     */
    @Override
    public synchronized Object getValueAt(int rowIndex, int columnIndex) {
        Product product = productList.get(rowIndex);
        switch (columnIndex) {
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

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    /**
     * Checks if TableModel contains product argument without taking it size
     * @param product that we check for the presence in the TableModel
     * @return false if product argument is null or TableModel does not contains this product
     * or contains this article with any other size.
     */
    public synchronized boolean containsArticle(Product product) {
        if (product == null) {
            return false;
        }
        for (Product p : productList) {
            if (p.articleEquals(product)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds product to TableModel list, plays MIDI sound and notifies about rows changed.
     * @param product that needs to be added.
     */
    public synchronized void addProduct(Product product) {
        // Play MIDI sound.
        MidiPlayer.playNewProduct();
        productList.add(product);
        // Notify all listeners that all cell values in the table's rows may have changed.
        fireTableDataChanged();

        log.info("Adding " + product + " to TableModel");
    }
}
