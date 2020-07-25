package com.pizzeria.store;

import com.pizzeria.store.entity.Crust;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.PizzaToppingDecorator;

public class Test {
    public static void main(String[] args) {
        Pizza p = new Pizza("FarmHouse Pizza", 20f, Pizza.Size.MEDIUM, Pizza.Type.VEG);
        System.out.println(p.getDescription());
        p.setCrust(new Crust("Hand Tossed"));
        System.out.println(p.getDescription() +" === " + p.getPrice());
        Topping topping = new Topping("Tomato", 10f);
        p = new PizzaToppingDecorator(p, topping);
        System.out.println(p.getDescription() +" === " + p.getPrice());
        Topping topping2 = new Topping("olive", 5f);
        p =  new PizzaToppingDecorator(p, topping2);
        System.out.println(p.getDescription() +" === " + p.getPrice());
    }
}
