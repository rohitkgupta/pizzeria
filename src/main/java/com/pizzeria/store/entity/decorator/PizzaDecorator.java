package com.pizzeria.store.entity.decorator;

import com.pizzeria.store.entity.Pizza;

public class PizzaDecorator extends Pizza {
    private Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        super(pizza.getId(), pizza.getQuantity(), pizza.getCrust());
        this.pizza = pizza;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}
