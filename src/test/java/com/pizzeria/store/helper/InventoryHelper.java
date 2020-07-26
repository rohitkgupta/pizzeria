package com.pizzeria.store.helper;

import com.pizzeria.store.entity.Crust;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Sides;
import com.pizzeria.store.entity.Topping;

public class InventoryHelper {

    public Pizza vegPizza = new Pizza("Farmhouse Pizza", 200f, Pizza.Size.MEDIUM, Pizza.Type.VEG, 2);
    public Pizza nonVegPizza = new Pizza("Chicken Cheese Pizza", 250f, Pizza.Size.REGULAR, Pizza.Type.NON_VEG, 1);
    public Topping nonVegTopping = new Topping("Chicken", 10f, Topping.Type.NON_VEG, 3);
    public Topping vegTopping = new Topping("Tomato", 5f, Topping.Type.NON_VEG, 3);
    public Sides coke = new Sides("Coke", 25f, 1);
    public Crust crust = new Crust("Plain Crust", 2);

}
