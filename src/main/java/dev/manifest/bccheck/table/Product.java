package dev.manifest.bccheck.table;

import java.util.Objects;

public class Product {

    private String name;
    private String color;
    private String size;

    public Product(String name, String color, String size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }

    public Product(String name, String color) {
        this(name, color, "");
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product)) {
            return false;
        }
        Product product = (Product) obj;
        return product.name.equals(this.name) && product.color.equals(this.color) && product.size.equals(this.size);
    }

    /**
     * Checks for equality two products without taking their size.
     * @param product the reference product with which to compare.
     * @return true if this objects is the same as the product argument without taking size,
     * false otherwise.
     */
    public boolean articleEquals(Product product) {
        if (product == null) {
            return false;
        }
        if (this.equals(product)) {
            return true;
        }
        return product.name.equals(this.name) && product.color.equals(this.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color, size);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    /**
     * Removes leading zeros and last character(check sum number) from the string.
     * @param barcode String from which need to get PLU.
     * @return String PLU.
     */
    public static String getPluFromBarcode(String barcode) {
        // ^ anchors to the start of the string. The 0* means zero or more 0 characters.
        barcode = barcode.replaceFirst("^0*", "");
        return barcode.substring(0, barcode.length() - 1);
    }

    /**
     *  Check if scanned code is similar to serial number.
     * @param scannable scanned code.
     * @return true if scannable is a serial number
     */
    public static boolean isItSerialNumber(String scannable) {
        return scannable.length() >= 39; //minimum length 39 characters
    }

    /**
     * AI 01 – 2
     * Product code – 14
     * AI 21 – 2
     * Serial number – 13+1
     * AI 91 – 2
     * Security code id -  4+1
     * AI 92 – 2
     * Security code – 88
     * @param dataMatrixCode scanned code
     * @return Serial number
     */
    public static String getSerialFromDataMatrixCode(String dataMatrixCode) {
        String serialNumber = dataMatrixCode.substring(18, 31);
        serialNumber = serialNumber.replace("\'", "\'\'");
        return serialNumber;

    }
}
