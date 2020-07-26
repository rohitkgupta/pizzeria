package com.pizzeria.store.utils;

import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.PizzaDecorator;
import com.pizzeria.store.entity.decorator.ToppingDecorator;

public class ItemUtils {

    public static MenuItem getCopy(MenuItem item) {
        MenuItem copy;
        if (item.getType() == MenuItem.Type.PIZZA){
            if (item instanceof PizzaDecorator){
                copy = new PizzaDecorator((Pizza)item);
            } else {
                copy = new Pizza((Pizza) item);
            }
        } else if (item.getType() == MenuItem.Type.TOPPING){
            copy = new Topping((Topping) item);
        } else {
            copy = new MenuItem(item);
        }
        return copy;
    }
}
