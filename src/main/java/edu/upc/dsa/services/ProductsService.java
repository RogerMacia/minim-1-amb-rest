package edu.upc.dsa.services;

import edu.upc.dsa.data.*;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api(value = "/productMng")
@Path("/productMng")
public class ProductsService {

    private ProductManager pm;

    public ProductsService() {
        this.pm = ProductManagerImpl.getInstance();
        if (pm == null) {
            Product p1 = new Product(1, "Hamburguesa", 7.5);
            Product p2 = new Product(2, "Patates", 3);
            Product p3 = new Product(3, "Pasta", 5);
            Product p4 = new Product(4, "Entrepà", 4.5);
            Product p5 = new Product(5, "Aigua", 0.75);

            User u1 = new User(1, "Josep");
            User u2 = new User(2, "Toni");
            User u3 = new User(3, "Albert");
            User u4 = new User(4, "Antonia");
            User u5 = new User(5, "Carla");

            List<Product> products = new ArrayList<>();
            HashMap<String, User> users = new HashMap<>();

            List<Product> productUser11 =  new ArrayList<>();
            List<Product> productUser12 =  new ArrayList<>();
            List<Product> productUser2 =  new ArrayList<>();
            List<Product> productUser3 =  new ArrayList<>();
            List<Product> productUser4 =  new ArrayList<>();

            products.add(p1);
            products.add(p2);
            products.add(p3);
            products.add(p4);
            products.add(p5);

            users.put(String.valueOf(u1.getId()), u1);
            users.put(String.valueOf(u2.getId()), u2);
            users.put(String.valueOf(u3.getId()), u3);
            users.put(String.valueOf(u4.getId()), u4);
            users.put(String.valueOf(u5.getId()), u5);

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

            for (Product p : products) {
                pm.addProduct(p);
            }

            for (User u : users.values()) {
                pm.addUser(u);
            }

            pm.makeOrder(productUser11, "1");
            pm.makeOrder(productUser12, "1");
            pm.makeOrder(productUser2, "2");
            pm.makeOrder(productUser3, "3");
            pm.makeOrder(productUser4, "4");
            pm.serveOrder();
            pm.serveOrder();
            pm.serveOrder();
        }
    }

    @GET
    @ApiOperation(value = "get all products sorted by ascending price")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer = "List")
    })
    @Path("/productsByPrice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByPrice() {
        List<Product> products = this.pm.getProductsByPrice();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "make an order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/makeOrder/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makeOrder(List<Product> products, @PathParam("id") String id) {
        if (products == null || pm.getUser(id) == null) return Response.status(500).entity(products).build();
        pm.makeOrder(products, id);
        return Response.status(201).entity(products).build();
    }

    @POST
    @ApiOperation(value = "serve an order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
    })
    @Path("/serveOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response serveOrder() {
        pm.serveOrder();
        return Response.status(201).entity("Successful").build();
    }

    @GET
    @ApiOperation(value = "get all done orders form a user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer = "List")
    })
    @Path("/doneProducts/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoneOrdersFromUser(@PathParam("id") String id) {
        List<Order> orders = this.pm.getDoneOrdersFromUser(id);

        GenericEntity<List<Order>> entity = new GenericEntity<List<Order>>(orders) {};
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get all products sorted by number of sales in descending order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer = "List")
    })
    @Path("/productsBySales")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsBySales() {
        List<Product> products = this.pm.getProductsBySales();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build();
    }
}