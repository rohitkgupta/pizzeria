package com.pizzeria.store.rule.impl;

import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.rule.RuleValidator;

public class PizzaCrustValidator implements RuleValidator {
    @Override
    public MenuItem validate(MenuItem item) {
        if (item.getType() == MenuItem.Type.PIZZA) {
            Pizza pizza = (Pizza) item;
            if (pizza.getCrust() == null) {
                throw new InvalidOrderException("Crust not selected for pizza["+pizza.getName()+"]");
            }
        }
        return item;
    }
}
