package com.pizzeria.store.entity;

public class Topping extends Item {
    private Type toppingType;

    public Topping(Integer id) {
        super(id, Item.Type.TOPPING, 1);
    }

    public Topping(String name, Float price, Type toppingType, Integer quantity) {
        super(Item.Type.TOPPING, name, price, quantity);
        this.toppingType = toppingType;
    }

    public Topping(Topping topping) {
        super(topping);
        if (topping != null) {
            this.toppingType = topping.toppingType;
        }
    }

    public Type getToppingType() {
        return toppingType;
    }

    public enum Type {
        VEG, NON_VEG, CHEESE
    }
}
