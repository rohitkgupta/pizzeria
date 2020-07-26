package com.pizzeria.store.businessrule.validator.impl;

import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.businessrule.validator.RuleValidator;

public class NonVegPizzaToppingValidator implements RuleValidator<Pizza> {
    @Override
    public void validate(Pizza pizza) {
        if (pizza.getPizzaType() == Pizza.Type.NON_VEG && pizza instanceof ToppingDecorator) {
            if (((ToppingDecorator) pizza).getToppingList().stream().filter(topping -> topping.getToppingType() == Topping.Type.NON_VEG).count() > 1) {
                throw new InvalidOrderException("You can add only one of the non-veg toppings in non-vegetarian pizza.");
            }
        }
    }
}
