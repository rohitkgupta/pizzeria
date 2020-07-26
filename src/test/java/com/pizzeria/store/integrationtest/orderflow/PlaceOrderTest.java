package com.pizzeria.store.integrationtest.orderflow;

import com.pizzeria.store.entity.*;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.testhelper.InventoryHelper;
import com.pizzeria.store.service.ItemService;
import com.pizzeria.store.service.OrderService;
import com.pizzeria.store.service.impl.ItemServiceImpl;
import com.pizzeria.store.service.impl.OrderServiceImpl;

public class PlaceOrderTest {

    private OrderService orderService = OrderServiceImpl.getInstance();
    private InventoryHelper inventoryHelper = null;

    public PlaceOrderTest(InventoryHelper inventoryHelper) {
        this.inventoryHelper = inventoryHelper;
    }

    public void placeNonVegPizzaOrderWithToppings() {
        Cart.Builder cartBuilder = new Cart.Builder();
        cartBuilder.forUser("test");
        Pizza pizza = new Pizza(inventoryHelper.nonVegPizza.getId(), 1, new Crust(inventoryHelper.crust.getId()));
        pizza = new ToppingDecorator(pizza, new Topping(inventoryHelper.vegTopping.getId()));
        pizza = new ToppingDecorator(pizza, new Topping(inventoryHelper.nonVegTopping.getId()));
        cartBuilder.addItem(pizza);
        Order order = new Order(cartBuilder.build());

        order = orderService.placeOrder(order);
    }


    public void placeLargeVegPizzaOrderWithToppings() {
        Cart.Builder cartBuilder = new Cart.Builder();
        cartBuilder.forUser("test");
        Pizza pizza = new Pizza(inventoryHelper.largeVegPizza.getId(), 1, new Crust(inventoryHelper.crust.getId()));
        pizza = new ToppingDecorator(pizza, new Topping(inventoryHelper.vegTopping.getId()));
        cartBuilder.addItem(pizza);
        Order order = new Order(cartBuilder.build());

        order = orderService.placeOrder(order);
    }

    public void placeVegPizzaOrderWithCoke() {
        Cart.Builder cartBuilder = new Cart.Builder();
        cartBuilder.forUser("test");
        cartBuilder.addItem(new Pizza(inventoryHelper.vegPizza.getId(), 1, new Crust(inventoryHelper.crust.getId())));
        cartBuilder.addItem(new Sides(inventoryHelper.coke.getId(), 1));
        Order order = new Order(cartBuilder.build());

        order = orderService.placeOrder(order);
    }

}
