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
    private InventoryHelper inventoryHelper = null;

    public AddItemTest(InventoryHelper inventoryHelper) {
        this.inventoryHelper = inventoryHelper;
    }


    public void addLargeVegPizza() {
        inventoryHelper.largeVegPizza.setId(vendorService.addItem(inventoryHelper.largeVegPizza).getId());
    }

    public void addCrust() {
        inventoryHelper.crust.setId(vendorService.addItem(inventoryHelper.crust).getId());
    }

    public void addSides() {
        inventoryHelper.coke.setId(vendorService.addItem(inventoryHelper.coke).getId());
    }

    public void addVegTopping() {
        inventoryHelper.vegTopping.setId(vendorService.addItem(inventoryHelper.vegTopping).getId());
    }

    public void addNonVegTopping() {
        inventoryHelper.nonVegTopping.setId(vendorService.addItem(inventoryHelper.nonVegTopping).getId());
    }

    public void addNonVegPizza() {
        inventoryHelper.nonVegPizza.setId(vendorService.addItem(inventoryHelper.nonVegPizza).getId());
    }

    public void addVegPizza() {
        inventoryHelper.vegPizza.setId(vendorService.addItem(inventoryHelper.vegPizza).getId());
    }
}
