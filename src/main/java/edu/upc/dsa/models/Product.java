package edu.upc.dsa.models;

public class Product {
    private int id;
    private String name;
    private int numSales;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.numSales = 0;
        this.price = price;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getNumSales() {
        return numSales;
    }
    public void setNumSales(int numSales) {
        this.numSales = numSales;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product [id="+id+", name=" + name + ", numSales=" + numSales +", price=" + price + "]";
    }
}