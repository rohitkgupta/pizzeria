package com.pizzeria.store.testhelper;

import com.pizzeria.store.entity.Crust;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Sides;
import com.pizzeria.store.entity.Topping;

public class InventoryHelper {

    public final Pizza vegPizza = new Pizza("Farmhouse Pizza", 200f, Pizza.Size.LARGE, Pizza.Type.VEG, 2);
    public final Pizza nonVegPizza = new Pizza("Chicken Cheese Pizza", 250f, Pizza.Size.REGULAR, Pizza.Type.NON_VEG, 1);
    public final Topping nonVegTopping = new Topping("Chicken", 10f, Topping.Type.NON_VEG, 3);
    public final Topping vegTopping = new Topping("Tomato", 5f, Topping.Type.VEG, 3);
    public final Topping oliveTopping = new Topping("Tomato", 5f, Topping.Type.NON_VEG, 1);
    public final Sides coke = new Sides("Coke", 25f, 1);
    public final Crust crust = new Crust("Plain Crust", 3);
    public final Pizza largeVegPizza = new Pizza("Farmhouse Pizza", 300f, Pizza.Size.LARGE, Pizza.Type.VEG, 1);

}
