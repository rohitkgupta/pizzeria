package com.pizzeria.store.entity;

public class PizzaToppingDecorator extends Pizza{

    private Pizza pizza;
    private Topping topping;

    public PizzaToppingDecorator(Pizza pizza, Topping topping){
        super(pizza.getName(), pizza.getPrice(), pizza.getSize(), pizza.getPizzaType());
        this.pizza = pizza;
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
}
