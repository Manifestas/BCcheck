package dev.manifest.bccheck.table;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void getPluFromBarcodeStartWith1Zero() {
        String barcode = "0123456";
        String actual = Product.getPluFromBarcode(barcode);
        String expected = "12345";
        assertEquals(expected, actual);
    }

    @Test
    public void getPluFromBarcodeStartWith7Zero() {
        String barcode = "0000000123456";
        String actual = Product.getPluFromBarcode(barcode);
        String expected = "12345";
        assertEquals(expected, actual);
    }

    @Test
    public void getPluFromBarcodeStartWithZeroAndInTheMiddle() {
        String barcode = "01234000056";
        String actual = Product.getPluFromBarcode(barcode);
        String expected = "123400005";
        assertEquals(expected, actual);
    }

    @Test
    public void getPluFromBarcodeStartWithNoZeros() {
        String barcode = "1234000056";
        String actual = Product.getPluFromBarcode(barcode);
        String expected = "123400005";
        assertEquals(expected, actual);
    }

    @Test
    public void getPluFromBarcodeStartWithLetter() {
        String barcode = "a01234000056";
        String actual = Product.getPluFromBarcode(barcode);
        String expected = "a0123400005";
        assertEquals(expected, actual);
    }
}