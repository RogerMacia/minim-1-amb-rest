package edu.upc.dsa.data;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Comparator;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import edu.upc.dsa.models.*;
import edu.upc.dsa.util.RandomUtils;

public class ProductManagerImpl implements ProductManager {
    private static ProductManagerImpl pm;

    private ProductManagerImpl() {}

    private List<Product> products;
    private QueueImpl<Order> orders;
    private HashMap<String, User> users;

    private static final Logger logger = Logger.getLogger(ProductManagerImpl.class);

    public static ProductManagerImpl getInstance() {
        if (pm == null) {
            pm = new ProductManagerImpl();

            pm.products = new ArrayList<>();
            pm.orders = new QueueImpl<Order>(64);
            pm.users = new HashMap<>();
        }
        return pm;
    }

    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public QueueImpl<Order> getOrders() {
        return orders;
    }
    public void setOrders(QueueImpl<Order> orders) {
        this.orders = orders;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }
    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    public Product getProduct(String id) {
        for (Product p :  this.products)
            if (p.getId().equals(id))
                return p;
        return null;
    }

    @Override
    public User getUser(String id) {
        return users.get(id);
    }

    @Override
    public void addProduct(Product product) {
        this.products.add(product);
        logger.info("Added product");
    }

    @Override
    public void addProduct(String name, double price) {
        String id = RandomUtils.getId();

        this.products.add(new Product(id, name, price));
        logger.info("Added product");
    }

    @Override
    public void addUser(User user) {
        this.users.put(user.getId(), user);
        logger.info("Added user");
    }

    @Override
    public List<Product> getProductsByPrice() {
        products.sort(Comparator.comparing(Product::getPrice));
        logger.info("Products sorted");
        return products;
    }

    @Override
    public void makeOrder(List<String> idProducts, String idUser) {
        User user = users.get(idUser);
        List<Product> products = new ArrayList<>();

        for (String idProduct : idProducts) {
            products.add(getProduct(idProduct));
        }

        Order o = new Order(user, products);
        orders.push(o);
        logger.info("Ordre creada");

        if (user.getPendingOrders() == null) {
            LinkedList<Order> newOrder = new LinkedList<>();
            newOrder.add(o);
            user.setPendingOrders(newOrder);
            logger.info("Llista d'ordres creada i assignada");
        }
        else if (user.getPendingOrders().add(o)) {
            logger.info("Ordre assignada");
        }
        else {
            logger.warn("Ordre no assignada");
            orders.pop();
            return;
        }

        for  (Product product : products) {
            product.setNumSales(product.getNumSales() + 1);
        }
        logger.info("Order done");
    }

    @Override
    public void serveOrder() {
        Order o = orders.pop();
        logger.info("Ordre treta de la cua");

        User user = o.getUser();

        if (user.getPendingOrders().remove(o)) {
            logger.info("Ordre eliminada de l'usuari");

            if (user.getServedOrders() == null) {
                LinkedList<Order> newServedOrder = new LinkedList<>();
                newServedOrder.add(o);
                user.setServedOrders(newServedOrder);
                logger.info("Ordre servida creada i afegida a l'usuari");
            }
            else if (user.getServedOrders().add(o)) {
                logger.info("Ordre servida afegida a l'usuari");
            }
            else {
                logger.error("Ordre servida no afegida a l'usuari", new RuntimeException("Ordre servida no afegida a l'usuari"));
            }
        }
        else {
            logger.error("Ordre no eliminada de l'usuari", new RuntimeException("Ordre no eliminada de l'usuari"));
        }
    }

    @Override
    public List<Order> getDoneOrdersFromUser(String id) {
        User user = pm.getUser(id);
        logger.info("Getting orders from user");
        return user.getServedOrders();
    }

    @Override
    public List<Product> getProductsBySales() {
        products.sort(Comparator.comparing(Product::getNumSales).reversed());
        logger.info("Products sorted");
        return products;
    }
}