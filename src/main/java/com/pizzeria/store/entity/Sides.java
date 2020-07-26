package com.pizzeria.store.entity;

public class Sides extends MenuItem {

    public Sides(Integer id,  Integer quantity){
        super(id, MenuItem.Type.SIDES, quantity);
    }

    public Sides(String name, Float price, Integer quantity){
        super(MenuItem.Type.SIDES, name, price, quantity);
    }


}
