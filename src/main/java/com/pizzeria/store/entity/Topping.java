package com.pizzeria.store.entity;

public class Topping extends MenuItem {
    private Type toppingType;

    public Topping(Integer id) {
        super(id, MenuItem.Type.TOPPING, 1);
    }

    public Topping(String name, Float price, Type toppingType, Integer quantity) {
        super(MenuItem.Type.TOPPING, name, price, quantity);
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
