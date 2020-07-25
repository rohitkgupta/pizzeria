package com.pizzeria.store.entity;

public class Topping extends Item {

    public Topping(String name, Float price){
        super(Item.Type.TOPPING, name, price);
    }

}
