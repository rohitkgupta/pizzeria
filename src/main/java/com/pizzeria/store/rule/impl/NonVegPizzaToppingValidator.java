package com.pizzeria.store.rule.impl;

import com.pizzeria.store.entity.Item;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.ToppingDecorator;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.rule.RuleValidator;

import java.util.List;

public class NonVegPizzaToppingValidator implements RuleValidator {
    @Override
    public Item validate(Item item) {
        if (item.getType() == Item.Type.PIZZA) {
            Pizza pizza = (Pizza) item;
            if (pizza.getPizzaType() == Pizza.Type.NON_VEG && pizza instanceof ToppingDecorator) {
                List<Topping> toppings = ((ToppingDecorator) pizza).getToppingList();
                int nonVegToppingCount = 0;
                for (Topping topping : toppings) {
                    if (topping.getToppingType() == Topping.Type.NON_VEG) {
                        nonVegToppingCount++;
                    }
                }
                if (nonVegToppingCount > 1) {
                    throw new InvalidOrderException("You can add only one of the non-veg toppings in non-vegetarian pizza.");
                }
            }
        }
        return item;
    }
}
