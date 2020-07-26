package com.pizzeria.store.businessrule.offer.impl;

import com.pizzeria.store.businessrule.offer.OfferRule;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.ToppingDecorator;

import java.util.concurrent.atomic.AtomicReference;

public class LargePizzaOfferRule implements OfferRule<Pizza> {

    @Override
    public Float applyOffer(Pizza pizza) {
        AtomicReference<Float> discount = new AtomicReference<>(0f);
        if (pizza.getSize() == Pizza.Size.LARGE && pizza instanceof ToppingDecorator) {
            ((ToppingDecorator) pizza).getToppingList().stream().limit(2).forEach(topping -> discount.set(discount.get() + topping.getPrice()));
        }
        return discount.get();
    }
}
