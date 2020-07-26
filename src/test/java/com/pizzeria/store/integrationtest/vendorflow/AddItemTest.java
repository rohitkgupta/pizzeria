package com.pizzeria.store.integrationtest.vendorflow;

import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Sides;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.helper.InventoryHelper;
import com.pizzeria.store.service.ItemService;
import com.pizzeria.store.service.VendorService;
import com.pizzeria.store.service.impl.ItemServiceImpl;
import com.pizzeria.store.service.impl.VendorServiceImpl;
import org.junit.Assert;

public class AddItemTest {

    private VendorService vendorService = VendorServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();
    private InventoryHelper inventoryHelper = null;

    public AddItemTest(InventoryHelper inventoryHelper) {
        this.inventoryHelper = inventoryHelper;
    }


    public void addSides() {
        inventoryHelper.coke = (Sides) vendorService.addItem(inventoryHelper.coke);
        Assert.assertEquals(3, itemService.getAllItemType().size());
        Assert.assertEquals(1, itemService.getItems(MenuItem.Type.SIDES).size());
    }

    public void addVegTopping() {
        inventoryHelper.vegTopping = (Topping) vendorService.addItem(inventoryHelper.vegTopping);
        Assert.assertEquals(2, itemService.getAllItemType().size());
        Assert.assertEquals(2, itemService.getItems(MenuItem.Type.TOPPING).size());
    }

    public void addNonVegTopping() {
        inventoryHelper.nonVegTopping = (Topping) vendorService.addItem(inventoryHelper.nonVegTopping);
        Assert.assertEquals(2, itemService.getAllItemType().size());
        Assert.assertEquals(1, itemService.getItems(MenuItem.Type.TOPPING).size());
        Assert.assertEquals(3, itemService.getItems(MenuItem.Type.TOPPING).get(0).getQuantity().intValue());
    }

    public void addNonVegPizza() {
        inventoryHelper.nonVegPizza = (Pizza) vendorService.addItem(inventoryHelper.nonVegPizza);
        Assert.assertEquals(1, itemService.getAllItemType().size());
        Assert.assertEquals(2, itemService.getItems(MenuItem.Type.PIZZA).size());
    }

    public void addVegPizza() {
        Assert.assertEquals(0, itemService.getAllItemType().size());
        inventoryHelper.vegPizza = (Pizza) vendorService.addItem(inventoryHelper.vegPizza);
        Assert.assertEquals(1, itemService.getAllItemType().size());
        Assert.assertEquals(1, itemService.getItems(MenuItem.Type.PIZZA).size());
        Assert.assertEquals(2, itemService.getItems(MenuItem.Type.PIZZA).get(0).getQuantity().intValue());
    }
}
