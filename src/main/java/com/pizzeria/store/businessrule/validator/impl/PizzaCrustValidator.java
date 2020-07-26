package com.pizzeria.store.businessrule.validator.impl;

import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.businessrule.validator.RuleValidator;

public class PizzaCrustValidator implements RuleValidator<Pizza> {
    @Override
    public void validate(Pizza pizza) {
        if (pizza.getCrust() == null) {
            throw new InvalidOrderException("Crust not selected for pizza[" + pizza.getName() + "]");
        } else if (pizza.getCrust().getQuantity() > 1) {
            throw new InvalidOrderException("Only one type of crust can be selected for any pizza.");
        }
    }
}
