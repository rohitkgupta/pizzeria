package com.pizzeria.store.dao;

import com.pizzeria.store.dao.impl.ItemDaoImpl;
import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.exception.InvalidDataException;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

@FixMethodOrder(MethodSorters.JVM)
public class ItemDaoTest {

    private ItemDao dao = ItemDaoImpl.getInstance();

    private Pizza vegPizza = new Pizza.Builder().withName("VegPizza").setPrice(10f).setQuantity(1).setPizzaType(Pizza.Type.VEG).build();

    @Test
    public void testGetAllItemType(){
        Assert.assertEquals(0, dao.getAllItemType().size());
    }

    @Test
    public void testAddItem(){
        MenuItem addedPizza = dao.addItem(vegPizza);
        Assert.assertEquals(0, addedPizza.getId().intValue());
        vegPizza.setId(addedPizza.getId());
    }

    @Test
    public void testGetItems(){
        Assert.assertEquals(1, dao.getItems(MenuItem.Type.PIZZA).size());
    }

    @Test
    public void testGetItem(){
        Assert.assertEquals(vegPizza.getName(), dao.getItem(0).get().getName());
        Assert.assertEquals(vegPizza.getPrice(), dao.getItem(0).get().getPrice());
    }

    @Test
    public void testUpdateItem(){
        MenuItem updatedPizza = dao.updateItem(new Pizza.Builder(0).setPrice(20f).build());
        Assert.assertEquals(20f, updatedPizza.getPrice().floatValue(), 0f);
    }

    @Test
    public void testUpdatedItemPrice(){
        Optional<MenuItem> item = dao.getItem(0);
        MenuItem pizza = item.orElse(null);
        Assert.assertNotNull(pizza);
        Assert.assertEquals(vegPizza.getName(), pizza.getName());
        Assert.assertEquals(20f, pizza.getPrice().floatValue(), 0f);
    }

    @Test
    public void testUpdateQuantity(){
        dao.updateQuantity(new LinkedList<>(Arrays.asList(new Pizza.Builder(0).setQuantity(1).build())));
        Assert.assertEquals(vegPizza.getQuantity()-1 , dao.getItem(0).get().getQuantity().intValue());
    }
}
