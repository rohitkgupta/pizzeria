package com.pizzeria.store.rule.impl;

import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.rule.RuleValidator;

import java.util.List;

public class LargePizzaValidator implements RuleValidator {
    @Override
    public MenuItem validate(MenuItem item) {
        if (item.getType() == MenuItem.Type.PIZZA) {
            Pizza pizza = (Pizza) item;
            if (pizza.getSize() == Pizza.Size.LARGE && pizza instanceof ToppingDecorator) {
                List<Topping> toppings = ((ToppingDecorator) pizza).getToppingList();
                int toppingCount = 0;
                for (Topping topping : toppings) {
                    if (toppingCount == 2) {
                        return item;
                    }
                    System.out.println("Congratulations Topping[" + topping.getName() + "] is free with Large Pizza.");
                    topping.setPrice(0f);
                    toppingCount++;
                }
            }
        }
        return item;
    }
}
