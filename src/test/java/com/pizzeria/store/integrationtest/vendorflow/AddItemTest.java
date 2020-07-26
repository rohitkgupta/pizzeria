package com.pizzeria.store.integrationtest.vendorflow;

import com.pizzeria.store.entity.*;
import com.pizzeria.store.testhelper.InventoryHelper;
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


    public void addLargeVegPizza() {
        inventoryHelper.largeVegPizza.setId(vendorService.addItem(inventoryHelper.largeVegPizza).getId());
        Assert.assertEquals(3, itemService.getItems(MenuItem.Type.PIZZA).size());
    }

    public void addCrust() {
        inventoryHelper.crust.setId(vendorService.addItem(inventoryHelper.crust).getId());
        Assert.assertEquals(4, itemService.getAllItemType().size());
        Assert.assertEquals(1, itemService.getItems(MenuItem.Type.CRUST).size());
        Assert.assertEquals(3, itemService.getItems(MenuItem.Type.CRUST).get(0).getQuantity().intValue());
    }

    public void addSides() {
        inventoryHelper.coke.setId(vendorService.addItem(inventoryHelper.coke).getId());
        Assert.assertEquals(3, itemService.getAllItemType().size());
        Assert.assertEquals(1, itemService.getItems(MenuItem.Type.SIDES).size());
    }

    public void addVegTopping() {
        inventoryHelper.vegTopping.setId(vendorService.addItem(inventoryHelper.vegTopping).getId());
        Assert.assertEquals(2, itemService.getAllItemType().size());
        Assert.assertEquals(2, itemService.getItems(MenuItem.Type.TOPPING).size());
    }

    public void addNonVegTopping() {
        inventoryHelper.nonVegTopping.setId(vendorService.addItem(inventoryHelper.nonVegTopping).getId());
        Assert.assertEquals(2, itemService.getAllItemType().size());
        Assert.assertEquals(1, itemService.getItems(MenuItem.Type.TOPPING).size());
        Assert.assertEquals(3, itemService.getItems(MenuItem.Type.TOPPING).get(0).getQuantity().intValue());
    }

    public void addNonVegPizza() {
        inventoryHelper.nonVegPizza.setId(vendorService.addItem(inventoryHelper.nonVegPizza).getId());
        Assert.assertEquals(1, itemService.getAllItemType().size());
        Assert.assertEquals(2, itemService.getItems(MenuItem.Type.PIZZA).size());
    }

    public void addVegPizza() {
        Assert.assertEquals(0, itemService.getAllItemType().size());
        inventoryHelper.vegPizza.setId(vendorService.addItem(inventoryHelper.vegPizza).getId());
        Assert.assertEquals(1, itemService.getAllItemType().size());
        Assert.assertEquals(1, itemService.getItems(MenuItem.Type.PIZZA).size());
        Assert.assertEquals(2, itemService.getItems(MenuItem.Type.PIZZA).get(0).getQuantity().intValue());
    }
}
