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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product)) {
            return false;
        }
        Product product = (Product) obj;
        return product.name.equals(this.name) && product.color.equals(this.color) && product.size.equals(this.size);
    }

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
}
