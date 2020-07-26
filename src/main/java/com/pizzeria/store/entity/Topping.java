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

    public void setToppingType(Type toppingType) {
        this.toppingType = toppingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Topping topping = (Topping) o;
        return toppingType == topping.toppingType;
    }

    public enum Type {
        VEG, NON_VEG, CHEESE
    }
}
