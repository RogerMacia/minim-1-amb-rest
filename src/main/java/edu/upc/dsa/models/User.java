package edu.upc.dsa.models;

import java.util.LinkedList;

public class User {
    private String id;
    private String name;
    private LinkedList<Order> pendingOrders;
    private LinkedList<Order> servedOrders;

    public User() {}
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Order> getPendingOrders() {
        return pendingOrders;
    }
    public void setPendingOrders(LinkedList<Order> pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public LinkedList<Order> getServedOrders() {
        return servedOrders;
    }
    public void setServedOrders(LinkedList<Order> servedOrders) {
        this.servedOrders = servedOrders;
    }
}
