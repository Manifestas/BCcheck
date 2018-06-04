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
