package com.pizzeria.store.integrationtest;

import com.pizzeria.store.entity.*;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.service.ItemService;
import com.pizzeria.store.service.OrderService;
import com.pizzeria.store.service.VendorService;
import com.pizzeria.store.service.impl.ItemServiceImpl;
import com.pizzeria.store.service.impl.OrderServiceImpl;
import com.pizzeria.store.service.impl.VendorServiceImpl;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTest {

    private VendorService vendorService = VendorServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();

    private Pizza vegPizza = new Pizza("Farmhouse Pizza", 200f, Pizza.Size.MEDIUM, Pizza.Type.VEG, 2);
    private Pizza nonVegPizza = new Pizza("Chicken Cheese Pizza", 250f, Pizza.Size.REGULAR, Pizza.Type.NON_VEG, 1);
    private Topping nonVegTopping = new Topping("Chicken", 10f, Topping.Type.NON_VEG, 3);
    private Topping rostedChickenTopping = new Topping("Rosted Chicken", 10f, Topping.Type.NON_VEG, 3);
    private Topping vegTopping = new Topping("Tomato", 5f, Topping.Type.VEG, 4);
    private Topping pannerTopping = new Topping("Paneer", 5f, Topping.Type.VEG, 3);
    private Sides coke = new Sides("Coke", 25f, 1);
    private Crust crust = new Crust("Plain Crust", 4);
    private Pizza largeVegPizza = new Pizza("Corn Cheese Pizza", 300f, Pizza.Size.LARGE, Pizza.Type.VEG, 1);

    @Test
    public void test01VendorAddItems(){
        vendorService.addItem(vegPizza);
        vendorService.addItem(nonVegPizza);
        vendorService.addItem(largeVegPizza);
        Assert.assertEquals(3, itemService.getItems(MenuItem.Type.PIZZA).size());
    }

    @Test
    public void test02VendorMoreAddItems(){
        vendorService.addItem(crust);
        vendorService.addItem(coke);
        vendorService.addItem(vegTopping);
        vendorService.addItem(nonVegTopping);
        vendorService.addItem(pannerTopping);
        vendorService.addItem(rostedChickenTopping);

        Assert.assertEquals(4, itemService.getAllItemType().size());
        Assert.assertEquals(1, itemService.getItems(MenuItem.Type.SIDES).size());
        Assert.assertEquals(coke.getQuantity(), itemService.getItems(MenuItem.Type.SIDES).get(0).getQuantity());
    }

    @Test
    public void test03VendorRestock(){
        vendorService.restockItem(itemService.getItems(MenuItem.Type.SIDES).get(0).getId(), 2);
        Assert.assertEquals(Integer.valueOf(coke.getQuantity() + 2) , itemService.getItems(MenuItem.Type.SIDES).get(0).getQuantity());
    }

    @Test
    public void test04VendorUpdatePrice(){
        vendorService.updatePrice(itemService.getItems(MenuItem.Type.SIDES).get(0).getId(), coke.getPrice()+5);
        Assert.assertEquals(Float.valueOf(coke.getPrice()+5) , itemService.getItems(MenuItem.Type.SIDES).get(0).getPrice());
    }

    @Test
    public void test05PlaceVegPizzaOrder() {
        Cart.Builder cartBuilder = new Cart.Builder().forUser("testUser");
        MenuItem farmhousePizza = itemService.getItem(MenuItem.Type.PIZZA, vegPizza.getName()).orElse(vegPizza);
        MenuItem plainCrust = itemService.getItem(MenuItem.Type.CRUST, crust.getName()).orElse(crust);
        cartBuilder.addItem(new Pizza.Builder().id(farmhousePizza.getId()).setCrust(new Crust(plainCrust.getId())).build());
        Order order = new Order(cartBuilder.build());
        order = orderService.placeOrder(order);
        Assert.assertEquals(Order.Status.PLACED, order.getStatus());
        Assert.assertEquals(farmhousePizza.getPrice(), order.getTotal());
    }

    @Test
    public void test06PlaceVegPizzaOrderWithToppings() {
        Cart.Builder cartBuilder = new Cart.Builder().forUser("testUser");
        Pizza pizza = new Pizza.Builder().id(itemService.getItem(MenuItem.Type.PIZZA, vegPizza.getName()).orElse(vegPizza).getId()).setCrust(new Crust(itemService.getItem(MenuItem.Type.CRUST, crust.getName()).orElse(crust).getId())).build();
        pizza = new ToppingDecorator(pizza, new Topping(itemService.getItem(MenuItem.Type.TOPPING, vegTopping.getName()).orElse(vegTopping).getId()));
        pizza = new ToppingDecorator(pizza, new Topping(itemService.getItem(MenuItem.Type.TOPPING, pannerTopping.getName()).orElse(pannerTopping).getId()));
        cartBuilder.addItem(pizza);
        Order order = new Order(cartBuilder.build());

        order = orderService.placeOrder(order);
        Assert.assertEquals(Order.Status.PLACED, order.getStatus());
        Assert.assertEquals(Float.valueOf(vegPizza.getPrice()+vegTopping.getPrice()+pannerTopping.getPrice()), order.getTotal());
    }

    @Test(expected = InvalidOrderException.class)
    public void test07VegPizzaWithNonvegToppings() {
        Cart.Builder cartBuilder = new Cart.Builder().forUser("testUser");
        Pizza pizza = new Pizza.Builder().id(itemService.getItem(MenuItem.Type.PIZZA, vegPizza.getName()).orElse(vegPizza).getId()).setCrust(new Crust(itemService.getItem(MenuItem.Type.CRUST, crust.getName()).orElse(crust).getId())).build();
        pizza = new ToppingDecorator(pizza, new Topping(itemService.getItem(MenuItem.Type.TOPPING, vegTopping.getName()).orElse(vegTopping).getId()));
        pizza = new ToppingDecorator(pizza, new Topping(itemService.getItem(MenuItem.Type.TOPPING, nonVegTopping.getName()).orElse(nonVegTopping).getId()));
        cartBuilder.addItem(pizza);
        Order order = new Order(cartBuilder.build());

        orderService.placeOrder(order);
    }

    @Test(expected = InvalidOrderException.class)
    public void test08NonVegPizzaWithPaneerToppings() {
        Cart.Builder cartBuilder = new Cart.Builder().forUser("testUser");
        Pizza pizza = new Pizza.Builder().id(itemService.getItem(MenuItem.Type.PIZZA, nonVegPizza.getName()).orElse(nonVegPizza).getId()).setCrust(new Crust(itemService.getItem(MenuItem.Type.CRUST, crust.getName()).orElse(crust).getId())).build();
        pizza = new ToppingDecorator(pizza, new Topping(itemService.getItem(MenuItem.Type.TOPPING, pannerTopping.getName()).orElse(pannerTopping).getId()));
        cartBuilder.addItem(pizza);
        Order order = new Order(cartBuilder.build());

        orderService.placeOrder(order);
    }

    @Test(expected = InvalidDataException.class)
    public void test09PizzaWithoutCrust() {
        Cart.Builder cartBuilder = new Cart.Builder().forUser("testUser");
        cartBuilder.addItem(new Pizza.Builder().id(itemService.getItem(MenuItem.Type.PIZZA, nonVegPizza.getName()).orElse(nonVegPizza).getId()).setCrust(new Crust(null)).build());
        Order order = new Order(cartBuilder.build());

        orderService.placeOrder(order);
    }

    @Test(expected = InvalidOrderException.class)
    public void test10NonVegPizzaWithMultipleNonVegToppings() {
        Cart.Builder cartBuilder = new Cart.Builder().forUser("testUser");
        Pizza pizza = new Pizza.Builder().id(itemService.getItem(MenuItem.Type.PIZZA, nonVegPizza.getName()).orElse(nonVegPizza).getId()).setCrust(new Crust(itemService.getItem(MenuItem.Type.CRUST, crust.getName()).orElse(crust).getId())).build();
        pizza = new ToppingDecorator(pizza, new Topping(itemService.getItem(MenuItem.Type.TOPPING, vegTopping.getName()).orElse(vegTopping).getId()));
        pizza = new ToppingDecorator(pizza, new Topping(itemService.getItem(MenuItem.Type.TOPPING, nonVegTopping.getName()).orElse(nonVegTopping).getId()));
        pizza = new ToppingDecorator(pizza, new Topping(itemService.getItem(MenuItem.Type.TOPPING, rostedChickenTopping.getName()).orElse(rostedChickenTopping).getId()));
        cartBuilder.addItem(pizza);
        Order order = new Order(cartBuilder.build());

        orderService.placeOrder(order);
    }

    @Test
    public void test11LargePizzaOffer() {
        Cart.Builder cartBuilder = new Cart.Builder().forUser("testUser");
        Pizza pizza = new Pizza.Builder().id(itemService.getItem(MenuItem.Type.PIZZA, largeVegPizza.getName()).orElse(largeVegPizza).getId()).setCrust(new Crust(itemService.getItem(MenuItem.Type.CRUST, crust.getName()).orElse(crust).getId())).build();
        pizza = new ToppingDecorator(pizza, new Topping(itemService.getItem(MenuItem.Type.TOPPING, vegTopping.getName()).orElse(vegTopping).getId()));
        pizza = new ToppingDecorator(pizza, new Topping(itemService.getItem(MenuItem.Type.TOPPING, pannerTopping.getName()).orElse(pannerTopping).getId()));
        cartBuilder.addItem(pizza);
        Order order = new Order(cartBuilder.build());

        order = orderService.placeOrder(order);
        Assert.assertEquals(Order.Status.PLACED, order.getStatus());
        Assert.assertEquals(largeVegPizza.getPrice(), order.getTotal());
    }



}
