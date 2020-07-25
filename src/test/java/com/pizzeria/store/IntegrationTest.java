package com.pizzeria.store;

import com.pizzeria.store.entity.*;
import com.pizzeria.store.service.ItemService;
import com.pizzeria.store.service.OrderService;
import com.pizzeria.store.service.VendorService;
import com.pizzeria.store.service.impl.ItemServiceImpl;
import com.pizzeria.store.service.impl.OrderServiceImpl;
import com.pizzeria.store.service.impl.VendorServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class IntegrationTest {

    private VendorService vendorService = VendorServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();

    private Item vegPizza = new Pizza("Farmhouse Pizza", 200f, Pizza.Size.MEDIUM, Pizza.Type.VEG, 2);
    private Item nonVegPizza = new Pizza("Chicken Cheese Pizza", 250f, Pizza.Size.REGULAR, Pizza.Type.NON_VEG, 1);
    private Item nonVegTopping = new Topping("Chicken", 10f, Topping.Type.NON_VEG, 3);
    private Item vegTopping = new Topping("Tomato", 5f, Topping.Type.NON_VEG, 3);
    private Item coke = new Sides("Tomato", 25f, 1);

    @Test
    public void testOrder() {
        addItemsToInventory();
        placeVegPizzaOrderWithCoke();
    }

    private void placeVegPizzaOrderWithCoke() {
        Cart cart = new Cart();
        cart.getItems().add(new Item(vegPizza.getId(), vegPizza.getType(), 1));
        cart.getItems().add(new Item(coke.getId(), coke.getType(), 1));
        Order order = new Order(cart);
        order = orderService.validate(order);
        Assert.assertEquals(Float.valueOf(225f), order.getCart().getTotal());
        Assert.assertEquals(Order.Status.NEW_ORDER, order.getStatus());
        order = orderService.placeOrder(order);
        Assert.assertEquals(Float.valueOf(225f), order.getCart().getTotal());
        Assert.assertEquals(Order.Status.PLACED, order.getStatus());
    }

    public void addItemsToInventory() {
        Assert.assertEquals(0, itemService.getAllItemType().size());
        vegPizza = vendorService.addItem(vegPizza);
        Assert.assertEquals(1, itemService.getAllItemType().size());
        Assert.assertEquals(1, itemService.getItems(Item.Type.PIZZA).size());
        Assert.assertEquals(2, itemService.getItems(Item.Type.PIZZA).get(0).getQuantity().intValue());

        nonVegPizza = vendorService.addItem(nonVegPizza);
        Assert.assertEquals(1, itemService.getAllItemType().size());
        Assert.assertEquals(2, itemService.getItems(Item.Type.PIZZA).size());

        nonVegTopping = vendorService.addItem(nonVegTopping);
        Assert.assertEquals(2, itemService.getAllItemType().size());
        Assert.assertEquals(2, itemService.getItems(Item.Type.PIZZA).size());
        Assert.assertEquals(1, itemService.getItems(Item.Type.TOPPING).size());
        Assert.assertEquals(3, itemService.getItems(Item.Type.TOPPING).get(0).getQuantity().intValue());

        vegTopping = vendorService.addItem(vegTopping);
        coke = vendorService.addItem(coke);
        Assert.assertEquals(3, itemService.getAllItemType().size());
        Assert.assertEquals(2, itemService.getItems(Item.Type.PIZZA).size());
        Assert.assertEquals(2, itemService.getItems(Item.Type.TOPPING).size());
        Assert.assertEquals(1, itemService.getItems(Item.Type.SIDES).size());


    }
}
