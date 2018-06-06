package dev.manifest.table;

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
