package com.pizzeria.store.entity;

public class Crust extends MenuItem {

    public Crust(Integer id){
        super(id, Type.CRUST, 1);
    }

    public Crust(String name, Integer quantity){
        super(MenuItem.Type.CRUST, name, 0f, quantity);
    }
}
