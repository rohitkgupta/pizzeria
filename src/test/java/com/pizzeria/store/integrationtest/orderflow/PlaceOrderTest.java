package com.pizzeria.store.integrationtest.orderflow;

import com.pizzeria.store.entity.*;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.helper.InventoryHelper;
import com.pizzeria.store.service.ItemService;
import com.pizzeria.store.service.OrderService;
import com.pizzeria.store.service.impl.ItemServiceImpl;
import com.pizzeria.store.service.impl.OrderServiceImpl;
import org.junit.Assert;

public class PlaceOrderTest {

    private OrderService orderService = OrderServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();
    private InventoryHelper inventoryHelper = null;

    public PlaceOrderTest(InventoryHelper inventoryHelper) {
        this.inventoryHelper = inventoryHelper;
    }

    public void placeNonVegPizzaOrderWithToppings() {
        Cart.Builder cartBuilder = new Cart.Builder();
        cartBuilder.forUser("test");
        Pizza pizza = new Pizza(inventoryHelper.nonVegPizza.getId(), 1, inventoryHelper.crust);
        pizza = new ToppingDecorator(pizza, new Topping(inventoryHelper.vegTopping.getId()));
        pizza = new ToppingDecorator(pizza, new Topping(inventoryHelper.nonVegTopping.getId()));
        cartBuilder.addItem(pizza);
        Order order = new Order(cartBuilder.build());

        order = orderService.placeOrder(order);
        Assert.assertEquals(Float.valueOf(265f), order.getCart().getTotal());
        Assert.assertEquals(Order.Status.PLACED, order.getStatus());
        Assert.assertEquals(1, itemService.getItems(Item.Type.PIZZA).get(0).getQuantity().intValue());
        Assert.assertEquals(0, itemService.getItems(Item.Type.PIZZA).get(1).getQuantity().intValue());
    }

    public void placeVegPizzaOrderWithCoke() {
        Cart.Builder cartBuilder = new Cart.Builder();
        cartBuilder.forUser("test");
        cartBuilder.addItem(new Pizza(inventoryHelper.vegPizza.getId(), 1, inventoryHelper.crust));
        cartBuilder.addItem(new Sides(inventoryHelper.coke.getId(), 1));
        Order order = new Order(cartBuilder.build());

        order = orderService.placeOrder(order);
        Assert.assertEquals(Float.valueOf(225f), order.getCart().getTotal());
        Assert.assertEquals(Order.Status.PLACED, order.getStatus());
        Assert.assertEquals(1, itemService.getItems(Item.Type.PIZZA).get(0).getQuantity().intValue());
        Assert.assertEquals(1, itemService.getItems(Item.Type.PIZZA).get(1).getQuantity().intValue());
    }

}
