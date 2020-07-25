package com.pizzeria.store.rule.impl;

import com.pizzeria.store.entity.Item;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.PizzaToppingDecorator;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.rule.RuleValidator;

import java.util.List;

public class VegPizzaToppingValidator implements RuleValidator {
    @Override
    public Item validate(Item item) {
        if (item instanceof Pizza){
            Pizza pizza = (Pizza) item;
            if(pizza.getPizzaType() == Pizza.Type.VEG && pizza instanceof PizzaToppingDecorator){
                List<Topping> toppings = ((PizzaToppingDecorator) pizza).getToppingList();
                for (Topping topping: toppings) {
                    if (topping.getToppingType() == Topping.Type.NON_VEG) {
                        throw new InvalidOrderException("Vegetarian pizza cannot have a non-vegetarian topping.");
                    }
                }
            }
        }
        return item;
     }
}
