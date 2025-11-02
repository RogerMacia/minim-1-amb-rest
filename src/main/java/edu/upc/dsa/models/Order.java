package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.List;

public class Order {
    private String id;
    private User user;
    private List<Product> products;

    public Order() {
    }

    public Order(User user, List<Product> products) {
        this.id = RandomUtils.getId();
        this.user = user;
        this.products = products;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
