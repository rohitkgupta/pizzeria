package com.pizzeria.store.utils;

import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.PizzaDecorator;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.exception.InvalidOrderException;

import java.util.List;

public class ItemUtils {

    public static MenuItem getCopy(MenuItem item) {
        MenuItem copy;
        if (item.getType() == MenuItem.Type.PIZZA) {
            if (item instanceof PizzaDecorator) {
                copy = new PizzaDecorator((Pizza) item);
            } else {
                copy = new Pizza((Pizza) item);
            }
        } else if (item.getType() == MenuItem.Type.TOPPING) {
            copy = new Topping((Topping) item);
        } else {
            copy = new MenuItem(item);
        }
        return copy;
    }

    public static void isValidItem(MenuItem item) {
        if (item == null || item.getId() == null) {
            throw new InvalidDataException("Invalid data.");
        }
    }

    public static void isValidItemData(MenuItem item) {
        if (item == null || item.getType() == null || item.getName() == null || item.getQuantity() == null || item.getPrice() == null) {
            throw new InvalidDataException("Invalid data.");
        }
    }

    public static void isValidField(Integer field) {
        if (field == null) {
            throw new InvalidDataException("Invalid data.");
        }
    }

    public static void isValid(List<MenuItem> items) {
        if (items == null || items.isEmpty()) {
            throw new InvalidOrderException("No item in the list.");
        }
    }
}
