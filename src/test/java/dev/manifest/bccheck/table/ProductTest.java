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

    @Test
    public void articleEqualsSameProductAnotherSize() {
        Product product1 = new Product("foo", "bar", "39");
        Product product2 = new Product("foo", "bar", "38");
        assertTrue(product1.articleEquals(product2));
    }

    @Test
    public void articleEqualsProductIsNull() {
        Product product1 = new Product("foo", "bar", "39");
        Product product2 = null;
        assertFalse(product1.articleEquals(product2));
    }

    @Test
    public void articleEqualsProductsDifferent() {
        Product product1 = new Product("foo", "bar", "39");
        Product product2 = new Product("bar", "foo", "39");
        assertFalse(product1.articleEquals(product2));
    }

    @Test
    public void articleEqualsProductsEquals() {
        Product product1 = new Product("foo", "bar", "39");
        Product product2 = new Product("foo", "bar", "39");
        assertTrue(product1.articleEquals(product2));
    }

    @Test
    public void isItSerialNumberIsSerial() {
        String scannable = "010406047769798721QkmvHY.+O5crD<GS>918" +
                "093<GS>92Za6ZlkdG3zynfnllYpMQSrbZ7Gu+OKqJ9fCRZu" +
                "+X5A7V7D7Th7ROcrRPbmLHpqV2BLI0YWuUTPYKadnk40Zjqw==";
        assertTrue(Product.isItSerialNumber(scannable));
    }

    @Test
    public void isItSerialNumberIsBarcode() {
        String scannable = "00000987234";
        assertFalse(Product.isItSerialNumber(scannable));
    }

}