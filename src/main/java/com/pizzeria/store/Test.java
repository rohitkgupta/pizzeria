package com.pizzeria.store;

import com.pizzeria.store.entity.*;
import com.pizzeria.store.service.OrderService;
import com.pizzeria.store.service.impl.OrderServiceImpl;
import com.pizzeria.store.utils.PropertyUtils;

import java.util.LinkedList;

public class Test {
    public static void main(String[] args) {
        /*Pizza p = new Pizza("FarmHouse Pizza", 20f, Pizza.Size.MEDIUM, Pizza.Type.VEG, 1);
        p.setId(1);
        p.setCrust(new Crust("Hand Tossed"));
        Topping topping = new Topping("Tomato", 10f, Topping.Type.VEG, 1);
        p = new PizzaToppingDecorator(p, topping);
        Topping topping2 = new Topping("olive", 5f, Topping.Type.VEG, 1);
        p =  new PizzaToppingDecorator(p, topping2);
        Topping topping3 = new Topping("jalapeno", 5f, Topping.Type.VEG, 1);
        p =  new PizzaToppingDecorator(p, topping3);
        Topping topping4 = new Topping("mushroom", 5f, Topping.Type.VEG, 1);
        p =  new PizzaToppingDecorator(p, topping4);
        p.setQuantity(1);

        Order order = new Order();
        Cart cart = new Cart();
        LinkedList<Item> items = new LinkedList<>();
        items.add(p);
        cart.setItems(items);
        order.setCart(cart);

        OrderService orderService = OrderServiceImpl.getInstance();
        try {
            orderService.placeOrder(order);
            System.out.println("Order Status:"+order.getStatus());


            Topping topping5 = new Topping("chicken", 5f, Topping.Type.NON_VEG, 1);
            p =  new PizzaToppingDecorator(p, topping5);
            items.add(p);
            order.getCart().setItems(items);
            orderService.placeOrder(order);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }*/



    }
}
