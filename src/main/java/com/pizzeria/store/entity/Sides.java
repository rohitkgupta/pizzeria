package com.pizzeria.store.entity;

public class Sides extends Item {

    public Sides(String name, Float price, Integer quantity){
        super(Item.Type.SIDES, name, price, quantity);
    }
}
