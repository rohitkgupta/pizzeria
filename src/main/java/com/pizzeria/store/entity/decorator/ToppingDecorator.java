package com.pizzeria.store.entity.decorator;

import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;

import java.util.LinkedList;
import java.util.List;

public class ToppingDecorator extends PizzaDecorator {
    private Topping topping;

    public ToppingDecorator(Pizza pizza, Topping topping) {
        super(pizza);
        topping.setQuantity(1);
        this.topping = topping;
    }

    @Override
    public String getDescription() {
        return this.getPizza().getDescription() + " , " + topping.getDescription();
    }

    @Override
    public Float getPrice() {
        return ((this.getPizza().getPrice() != null ? this.getPizza().getPrice() : 0f) + (topping.getPrice() != null ? topping.getPrice() : 0f));
    }

    @Override
    public void setPrice(Float price) {
        this.getPizza().setPrice(price);
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
