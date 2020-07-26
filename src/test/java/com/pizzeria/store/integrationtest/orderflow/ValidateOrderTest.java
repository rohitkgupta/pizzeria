package com.pizzeria.store.integrationtest.orderflow;

import com.pizzeria.store.entity.*;
import com.pizzeria.store.helper.InventoryHelper;
import com.pizzeria.store.service.OrderService;
import com.pizzeria.store.service.impl.OrderServiceImpl;
import org.junit.Assert;

public class ValidateOrderTest {

    private OrderService orderService = OrderServiceImpl.getInstance();
    private InventoryHelper inventoryHelper = null;

    public ValidateOrderTest(InventoryHelper inventoryHelper) {
        this.inventoryHelper = inventoryHelper;
    }

    public void validateNonVegPizzaOrderWithToppings() {
        Cart.Builder cartBuilder = new Cart.Builder();
        cartBuilder.forUser("test");
        Pizza pizza = new Pizza(inventoryHelper.nonVegPizza.getId(), 1, inventoryHelper.crust);
        pizza = new ToppingDecorator(pizza, new Topping(inventoryHelper.vegTopping.getId()));
        pizza = new ToppingDecorator(pizza, new Topping(inventoryHelper.nonVegTopping.getId()));
        cartBuilder.addItem(pizza);

        Order order = new Order(cartBuilder.build());
        order = orderService.validate(order);
        Assert.assertEquals(Float.valueOf(265f), order.getCart().getTotal());
        Assert.assertEquals(Order.Status.NEW_ORDER, order.getStatus());
    }

    public void validateVegPizzaOrderWithCoke() {
        Cart.Builder cartBuilder = new Cart.Builder();
        cartBuilder.forUser("test");
        cartBuilder.addItem(new Pizza(inventoryHelper.vegPizza.getId(), 1, inventoryHelper.crust));
        cartBuilder.addItem(new Sides(inventoryHelper.coke.getId(), 1));

        Order order = new Order(cartBuilder.build());
        order = orderService.validate(order);
        Assert.assertEquals(Float.valueOf(225f), order.getCart().getTotal());
        Assert.assertEquals(Order.Status.NEW_ORDER, order.getStatus());
    }

}
