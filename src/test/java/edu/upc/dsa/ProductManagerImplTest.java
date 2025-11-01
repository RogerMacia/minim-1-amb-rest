package edu.upc.dsa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.upc.dsa.models.*;
import edu.upc.dsa.data.*;

public class ProductManagerImplTest {
    ProductManagerImpl pm;
    Product p1;
    Product p2;
    Product p3;
    Product p4;
    Product p5;
    User u1;
    User u2;
    User u3;
    User u4;
    User u5;

    List<Product> products = new ArrayList<>();
    HashMap<String, User> users = new HashMap<>();

    List<Product> productUser11 =  new ArrayList<>();
    List<Product> productUser12 =  new ArrayList<>();
    List<Product> productUser2 =  new ArrayList<>();
    List<Product> productUser3 =  new ArrayList<>();
    List<Product> productUser4 =  new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        pm = pm.getInstance();
        p1 = new Product(1, "Hamburguesa", 7.5);
        p2 = new Product(2, "Patates", 3);
        p3 = new Product(3, "Pasta", 5);
        p4 = new Product(4, "Entrepà", 4.5);
        p5 = new Product(5, "Aigua", 0.75);

        u1 = new User(1, "Josep");
        u2 = new User(2, "Toni");
        u3 = new User(3, "Albert");
        u4 = new User(4, "Antonia");
        u5 = new User(5, "Carla");

        products.add(p1);
        Assert.assertEquals(p1, products.get(0));
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);
        Assert.assertEquals(5, products.toArray().length);

        users.put(String.valueOf(u1.getId()), u1);
        Assert.assertEquals(u1, users.get("1"));
        users.put(String.valueOf(u2.getId()), u2);
        users.put(String.valueOf(u3.getId()), u3);
        users.put(String.valueOf(u4.getId()), u4);
        users.put(String.valueOf(u5.getId()), u5);
        Assert.assertEquals(5, users.size());

        productUser11.add(p5);
        productUser11.add(p5);
        productUser11.add(p2);
        productUser11.add(p2);
        productUser11.add(p5);
        productUser12.add(p1);
        productUser12.add(p1);
        productUser12.add(p5);
        productUser2.add(p5);
        productUser2.add(p2);
        productUser2.add(p4);


        productUser3.add(p5);
        productUser3.add(p3);
        productUser3.add(p1);
        productUser3.add(p2);
        productUser3.add(p2);
        productUser4.add(p4);
    }

    @After
    public void tearDown() {
        this.pm = null;
        this.p1 = null;
        this.p2 = null;
        this.p3 = null;
        this.p4 = null;
        this.p5 = null;
        this.u1 = null;
        this.u2 = null;
        this.u3 = null;
        this.u4 = null;
        this.u5 = null;
        this.products.clear();;
        this.users.clear();
        this.productUser11.clear();
        this.productUser12.clear();
        this.productUser2.clear();
        this.productUser3.clear();
        this.productUser4.clear();
    }

    @Test
    public void test01AddProduct() throws Exception {
        for (Product p : products) {
            pm.addProduct(p);
        }

        Assert.assertEquals(products, pm.getProducts());
    }

    @Test
    public void test02AddUser() throws Exception {
        for (User u : users.values()) {
            pm.addUser(u);
        }

        Assert.assertEquals(users, pm.getUsers());
    }

    @Test
    public void test03GetProductsByPrice() throws Exception {
        List<Product> p;
        p = pm.getProductsByPrice();

        Assert.assertEquals(p.get(0), p5);
        Assert.assertEquals(p.get(1), p2);
        Assert.assertEquals(p.get(2), p4);
        Assert.assertEquals(p.get(3), p3);
        Assert.assertEquals(p.get(4), p1);

        p.clear();
    }

    @Test
    public void test04MakeAndServeOrder() throws Exception {
        pm.makeOrder(productUser11, "1");
        Assert.assertEquals(productUser11, u1.getPendingOrders().getFirst().getProducts());

        pm.makeOrder(productUser12, "1");
        Assert.assertEquals(productUser12, u1.getPendingOrders().get(1).getProducts());

        pm.makeOrder(productUser2, "2");
        Assert.assertEquals(productUser2, u2.getPendingOrders().getFirst().getProducts());

        pm.makeOrder(productUser3, "3");
        Assert.assertEquals(productUser3, u3.getPendingOrders().getFirst().getProducts());

        pm.makeOrder(productUser4, "4");
        Assert.assertEquals(productUser4, u4.getPendingOrders().getFirst().getProducts());

        pm.serveOrder();
        Assert.assertEquals(4, pm.getOrders().size());

        pm.serveOrder();
        Assert.assertEquals(3, pm.getOrders().size());

        pm.serveOrder();
        Assert.assertEquals(2, pm.getOrders().size());

        List<Order> doneOrder1 = pm.getDoneOrdersFromUser("1");
        Assert.assertEquals(doneOrder1, u1.getServedOrders());

        List<Order> doneOrder2 = pm.getDoneOrdersFromUser("2");
        Assert.assertEquals(doneOrder2, u2.getServedOrders());

        List<Order> doneOrder3 = pm.getDoneOrdersFromUser("3");
        Assert.assertEquals(null, doneOrder3);

        doneOrder1.clear();
        doneOrder2.clear();

        List<Product> productBySales;
        productBySales =  pm.getProductsBySales();

        Assert.assertEquals(p5,  productBySales.get(0));
        Assert.assertEquals(p2,  productBySales.get(1));
        Assert.assertEquals(p1,  productBySales.get(2));
        Assert.assertEquals(p4,  productBySales.get(3));
        Assert.assertEquals(p3,  productBySales.get(4));

        productBySales.clear();
    }
}
