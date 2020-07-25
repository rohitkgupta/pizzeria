package com.pizzeria.store.entity;

public class Sides extends Item {

    public Sides(Integer id,  Integer quantity){
        super(id, Item.Type.SIDES, quantity);
    }

    public Sides(String name, Float price, Integer quantity){
        super(Item.Type.SIDES, name, price, quantity);
    }
}
