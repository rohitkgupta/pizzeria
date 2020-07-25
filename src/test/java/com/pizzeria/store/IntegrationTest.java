package com.pizzeria.store;

import com.pizzeria.store.entity.*;
import com.pizzeria.store.service.CrustService;
import com.pizzeria.store.service.ItemService;
import com.pizzeria.store.service.OrderService;
import com.pizzeria.store.service.VendorService;
import com.pizzeria.store.service.impl.CrustServiceImpl;
import com.pizzeria.store.service.impl.ItemServiceImpl;
import com.pizzeria.store.service.impl.OrderServiceImpl;
import com.pizzeria.store.service.impl.VendorServiceImpl;
import javafx.geometry.Side;
import org.junit.Assert;
import org.junit.Test;

public class IntegrationTest {

    private VendorService vendorService = VendorServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();
    private CrustService crustService =  CrustServiceImpl.getInstance();

    private Pizza vegPizza = new Pizza("Farmhouse Pizza", 200f, Pizza.Size.MEDIUM, Pizza.Type.VEG, 2);
    private Pizza nonVegPizza = new Pizza("Chicken Cheese Pizza", 250f, Pizza.Size.REGULAR, Pizza.Type.NON_VEG, 1);
    private Topping nonVegTopping = new Topping("Chicken", 10f, Topping.Type.NON_VEG, 3);
    private Topping vegTopping = new Topping("Tomato", 5f, Topping.Type.NON_VEG, 3);
    private Sides coke = new Sides("Tomato", 25f, 1);

    @Test
    public void testOrder() {
        //Adding 2 Veg Pizza, 1 Non Veg, 3 veg toppings, 3 non veg topping and 1 coke
        addItemsToInventory();
        //Placing order for 1 Veg Pizza, 1 Coke
        placeVegPizzaOrderWithCoke();
        //Placing order for 1 NonVeg Pizza, 1 Tomato, 1 Chicken toppings
        placeNonVegPizzaOrderWithToppings();
    }

    private void placeNonVegPizzaOrderWithToppings() {
        Cart.Builder cartBuilder = new Cart.Builder();
        cartBuilder.forUser("test");
        Pizza pizza = new Pizza(nonVegPizza.getId(), 1, crustService.getAvailableCrusts().get(0));
        pizza = new ToppingDecorator(pizza, new Topping(vegTopping.getId()));
        pizza = new ToppingDecorator(pizza, new Topping(nonVegTopping.getId()));
        cartBuilder.addItem(pizza);

        Order order = new Order(cartBuilder.build());
        order = orderService.validate(order);
        Assert.assertEquals(Float.valueOf(265f), order.getCart().getTotal());
        Assert.assertEquals(Order.Status.NEW_ORDER, order.getStatus());

        order = orderService.placeOrder(order);
        Assert.assertEquals(Float.valueOf(265f), order.getCart().getTotal());
        Assert.assertEquals(Order.Status.PLACED, order.getStatus());
        Assert.assertEquals(1, itemService.getItems(Item.Type.PIZZA).get(0).getQuantity().intValue());
        Assert.assertEquals(0, itemService.getItems(Item.Type.PIZZA).get(1).getQuantity().intValue());
    }

    private void placeVegPizzaOrderWithCoke() {
        Cart.Builder cartBuilder = new Cart.Builder();
        cartBuilder.forUser("test");
        cartBuilder.addItem(new Pizza(vegPizza.getId(), 1, crustService.getAvailableCrusts().get(0)));
        cartBuilder.addItem(new Sides(coke.getId(), 1));

        Order order = new Order(cartBuilder.build());
        order = orderService.validate(order);
        Assert.assertEquals(Float.valueOf(225f), order.getCart().getTotal());
        Assert.assertEquals(Order.Status.NEW_ORDER, order.getStatus());

        order = orderService.placeOrder(order);
        Assert.assertEquals(Float.valueOf(225f), order.getCart().getTotal());
        Assert.assertEquals(Order.Status.PLACED, order.getStatus());
        Assert.assertEquals(1, itemService.getItems(Item.Type.PIZZA).get(0).getQuantity().intValue());
        Assert.assertEquals(1, itemService.getItems(Item.Type.PIZZA).get(1).getQuantity().intValue());
    }

    public void addItemsToInventory() {
        Assert.assertEquals(0, itemService.getAllItemType().size());
        vegPizza = (Pizza) vendorService.addItem(vegPizza);
        Assert.assertEquals(1, itemService.getAllItemType().size());
        Assert.assertEquals(1, itemService.getItems(Item.Type.PIZZA).size());
        Assert.assertEquals(2, itemService.getItems(Item.Type.PIZZA).get(0).getQuantity().intValue());

        nonVegPizza = (Pizza) vendorService.addItem(nonVegPizza);
        Assert.assertEquals(1, itemService.getAllItemType().size());
        Assert.assertEquals(2, itemService.getItems(Item.Type.PIZZA).size());

        nonVegTopping = (Topping) vendorService.addItem(nonVegTopping);
        Assert.assertEquals(2, itemService.getAllItemType().size());
        Assert.assertEquals(2, itemService.getItems(Item.Type.PIZZA).size());
        Assert.assertEquals(1, itemService.getItems(Item.Type.TOPPING).size());
        Assert.assertEquals(3, itemService.getItems(Item.Type.TOPPING).get(0).getQuantity().intValue());

        vegTopping = (Topping) vendorService.addItem(vegTopping);
        coke = (Sides) vendorService.addItem(coke);
        Assert.assertEquals(3, itemService.getAllItemType().size());
        Assert.assertEquals(2, itemService.getItems(Item.Type.PIZZA).size());
        Assert.assertEquals(2, itemService.getItems(Item.Type.TOPPING).size());
        Assert.assertEquals(1, itemService.getItems(Item.Type.SIDES).size());
    }
}
