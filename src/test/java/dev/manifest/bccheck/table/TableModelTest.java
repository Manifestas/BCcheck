package dev.manifest.bccheck.table;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TableModelTest {

    private Product bar;
    private Product foo;

    private TableModel tableModel;

    @Before
    public void setup() {
        tableModel = TableModel.getInstance();
        bar = new Product("bar", "white", "40");
        foo = new Product("foo", "black", "41");
    }

    @Test
    public void getRowCountWithFilledTable() {
        tableModel.clearData();
        fillProductList();
        assertEquals(3, tableModel.getRowCount());
    }

    @Test
    public void getRowCountEmptyProductList() {
        tableModel.clearData();
        assertEquals(1, tableModel.getRowCount());
    }

    @Test
    public void getColumnCount4() {
        assertEquals(4, tableModel.getColumnCount());
    }

    @Test
    public void getValueAtWhenRowIndex0ColumnIndex0() {
        String actualValue = (String) tableModel.getValueAt(0, 0);
        assertEquals("Модель", actualValue);
    }

    @Test
    public void getValueAtWhenRowIndex0ColumnIndex2() {
        String actualValue = (String) tableModel.getValueAt(0, 2);
        assertEquals("Размер", actualValue);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getValueAtWhenRowIndex0ColumnIndex4() {
        String actualValue = (String) tableModel.getValueAt(0, 4);
    }

    @Test
    public void getValueAtWhenRowIndex1ColumnIndex0() {
        fillProductList();
        String actualValue = (String) tableModel.getValueAt(1, 0);
        assertEquals("foo", actualValue);
    }

    @Test
    public void getValueAtWhenRowIndex1ColumnIndex1() {
        fillProductList();
        String actualValue = (String) tableModel.getValueAt(1, 1);
        assertEquals("black", actualValue);
    }

    @Test
    public void getValueAtWhenRowIndex1ColumnIndex2() {
        fillProductList();
        String actualValue = (String) tableModel.getValueAt(1, 2);
        assertEquals("41", actualValue);
    }

    @Test
    public void getValueAtWhenRowIndex1ColumnIndex3() {
        fillProductList();
        Integer actualValue = (Integer) tableModel.getValueAt(1, 3);
        assertEquals("1", actualValue.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getValueAtWhenRowIndex1ColumnIndex4() {
        fillProductList();
        String actualValue = (String) tableModel.getValueAt(1, 4);
    }

    @Test
    public void containsArticleProductIsNull() {
        assertFalse(tableModel.containsArticle(null));
    }

    @Test
    public void containsArticleProductNotInList() {
        fillProductList();
        Product product = new Product("kek", "white", "40");
        assertFalse(tableModel.containsArticle(product));
    }

    @Test
    public void containsArticleProductInList() {
        fillProductList();
        assertTrue(tableModel.containsArticle(bar));
    }

    @Test
    public void addProductNull() {
        tableModel.clearData();
        tableModel.addProduct(null);
        assertEquals(1, tableModel.getRowCount());
    }

    @Test
    public void addProductActual() {
        tableModel.clearData();
        Product product = new Product("any", "color", "30");
        tableModel.addProduct(product);
        assertTrue(tableModel.containsArticle(product));
    }

    @Test
    public void addProductFireTableDataChangedInvoked() {
        TableModel model = spy(tableModel);
        model.addProduct(bar);
        verify(model).fireTableDataChanged();
    }

    @Test
    public void clearDataRowCountReturn1() {
        tableModel.clearData();
        assertEquals(1, tableModel.getRowCount());
    }

    @Test
    public void clearDataiFireTableDataChangedInvoked() {
        TableModel model = spy(tableModel);
        model.clearData();
        verify(model).fireTableDataChanged();
    }

    private void fillProductList() {
        tableModel.addProduct(foo);
        tableModel.addProduct(bar);
    }
}