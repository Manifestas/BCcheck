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
     * Removes first zeros from the string.
     * @param barcode String from which need to remove leading zeros.
     * @return String with removed leading zeros.
     */
    private String removeLeadingZeros(String barcode) {
        // ^ anchors to the start of the string. The 0* means zero or more 0 characters.
        return barcode.replaceFirst("^0*", "");
    }
}
