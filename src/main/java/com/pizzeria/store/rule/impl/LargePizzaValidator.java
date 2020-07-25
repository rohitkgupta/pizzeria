package com.pizzeria.store.rule.impl;

import com.pizzeria.store.entity.Item;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.ToppingDecorator;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.rule.RuleValidator;

import java.util.List;

public class LargePizzaValidator implements RuleValidator {
    @Override
    public Item validate(Item item) {
        if (item.getType() == Item.Type.PIZZA) {
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