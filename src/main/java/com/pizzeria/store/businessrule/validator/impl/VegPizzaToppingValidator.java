package com.pizzeria.store.businessrule.validator.impl;

import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.businessrule.validator.RuleValidator;

public class VegPizzaToppingValidator implements RuleValidator<Pizza> {
    @Override
    public void validate(Pizza pizza) {
        if (pizza.getPizzaType() == Pizza.Type.VEG && pizza instanceof ToppingDecorator) {
            if (((ToppingDecorator) pizza).getToppingList().stream().anyMatch(topping -> topping.getToppingType() == Topping.Type.NON_VEG)) {
                throw new InvalidOrderException("Vegetarian pizza cannot have a non-vegetarian topping.");
            }
        }
    }
}
