package com.pizzeria.store.entity;

public class Crust extends MenuItem {

    public Crust(Integer id,  Integer quantity){
        super(id, Type.CRUST, quantity);
    }

    public Crust(String name, Integer quantity){
        super(MenuItem.Type.CRUST, name, 0f, quantity);
    }
}
