package com.pizzeria.store.rule.impl;

import com.pizzeria.store.entity.Item;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.rule.RuleValidator;

import java.util.List;

public class PaneerToppingValidator implements RuleValidator {
    @Override
    public Item validate(Item item) {
        if (item.getType() == Item.Type.PIZZA){
            Pizza pizza = (Pizza) item;
            if(pizza.getPizzaType() == Pizza.Type.NON_VEG && pizza instanceof ToppingDecorator){
                List<Topping> toppings = ((ToppingDecorator) pizza).getToppingList();
                for (Topping topping: toppings) {
                    if (topping.getToppingType() == Topping.Type.VEG && topping.getName().equalsIgnoreCase("paneer")) {
                        throw new InvalidOrderException("Non-vegetarian pizza cannot have paneer topping.");
                    }
                }
            }
        }
        return item;
     }
}
