package edu.upc.dsa.data;

import java.util.List;
import edu.upc.dsa.models.*;

public interface ProductManager {
    public User getUser(String id);

    public void addProduct(Product p);
    public void addProduct(String name, double price);

    public void addUser(User user);

    public List<Product> getProductsByPrice();

    public void makeOrder(List<String> idProducts, String idUser);

    public Order serveOrder();

    public List<Order> getDoneOrdersFromUser(String id);

    public List<Product> getProductsBySales();
}