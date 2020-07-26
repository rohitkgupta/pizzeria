package com.pizzeria.store.utils;

import com.pizzeria.store.entity.Crust;
import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Pizza;
import org.junit.Assert;
import org.junit.Test;

public class ItemUtilsTest {

    @Test
    public void testGetCopy(){
        Pizza largeVegPizza = new Pizza.Builder().withName("VegPizza").setPrice(10f).setSize(Pizza.Size.LARGE).setQuantity(1).setPizzaType(Pizza.Type.VEG).setCrust(new Crust(1)).build();
        MenuItem copy = ItemUtils.getCopy(largeVegPizza);
        Assert.assertEquals(largeVegPizza.getName(), copy.getName());
        Assert.assertEquals(largeVegPizza.getCrust(), ((Pizza)copy).getCrust());
    }
}
