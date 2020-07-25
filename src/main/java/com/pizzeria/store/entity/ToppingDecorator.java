package com.pizzeria.store.entity;

import java.util.LinkedList;
import java.util.List;

public class ToppingDecorator extends Pizza{

    private Pizza pizza;
    private Topping topping;

    public ToppingDecorator(Pizza pizza, Topping topping){
        super(pizza.getId(), pizza.getQuantity());
        this.pizza = pizza;
        topping.setQuantity(1);
        this.topping = topping;
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + " , " + topping.getDescription();
    }

    @Override
    public Float getPrice() {
        return pizza.getPrice() + topping.getPrice();
    }

    public Pizza getPizza() {
        return pizza;
    }

    public Topping getTopping() {
        return topping;
    }

    public List<Topping> getToppingList() {
        List<Topping> toppings = new LinkedList<>();
        toppings.add(topping);
        if (this.getPizza() instanceof ToppingDecorator) {
            toppings.addAll(((ToppingDecorator) this.getPizza()).getToppingList());
        }
        return toppings;
    }
}