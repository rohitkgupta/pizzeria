package com.pizzeria.store.dao;

import com.pizzeria.store.dao.impl.ItemDaoImpl;
import com.pizzeria.store.entity.Crust;
import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Pizza;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemDaoTest {

    private ItemDao dao = ItemDaoImpl.getInstance();

    private Crust crust = new Crust("Hand tossed", 1);

    @Test
    public void test1AddItem(){
        MenuItem addedPizza = dao.addItem(crust);
        Assert.assertEquals(0, addedPizza.getId().intValue());
        crust.setId(addedPizza.getId());
    }

    @Test
    public void test2GetItems(){
        Assert.assertEquals(1, dao.getItems(MenuItem.Type.CRUST).size());
    }

    @Test
    public void test3GetItem(){
        Assert.assertEquals(crust.getName(), dao.getItem(0).get().getName());
        Assert.assertEquals(crust.getPrice(), dao.getItem(0).get().getPrice());
    }

    @Test
    public void test4UpdateItem(){
        MenuItem updatedPizza = dao.updateItem(new Pizza.Builder(0).setPrice(20f).build());
        Assert.assertEquals(20f, updatedPizza.getPrice().floatValue(), 0f);
    }

    @Test
    public void test5UpdatedItemPrice(){
        Optional<MenuItem> item = dao.getItem(0);
        MenuItem pizza = item.orElse(null);
        Assert.assertNotNull(pizza);
        Assert.assertEquals(crust.getName(), pizza.getName());
        Assert.assertEquals(20f, pizza.getPrice().floatValue(), 0f);
    }

    @Test
    public void test6UpdateQuantity(){
        dao.updateQuantity(new LinkedList<>(Arrays.asList(new Pizza.Builder(0).setQuantity(1).build())));
        Assert.assertEquals(crust.getQuantity()-1 , dao.getItem(0).get().getQuantity().intValue());
    }
}
