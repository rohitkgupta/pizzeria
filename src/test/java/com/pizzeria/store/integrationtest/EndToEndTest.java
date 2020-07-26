package com.pizzeria.store.integrationtest;

import com.pizzeria.store.helper.InventoryHelper;
import com.pizzeria.store.integrationtest.orderflow.PlaceOrderTest;
import com.pizzeria.store.integrationtest.orderflow.ValidateOrderTest;
import com.pizzeria.store.integrationtest.vendorflow.AddItemTest;
import org.junit.Test;

public class EndToEndTest {

    private InventoryHelper inventoryHelper = new InventoryHelper();
    private AddItemTest addItemTest = new AddItemTest(inventoryHelper);
    private ValidateOrderTest validateOrderTest = new ValidateOrderTest(inventoryHelper);
    private PlaceOrderTest placeOrderTest = new PlaceOrderTest(inventoryHelper);

    @Test
    public void test(){
        addItemTest.addVegPizza();
        addItemTest.addNonVegPizza();
        addItemTest.addNonVegTopping();
        addItemTest.addVegTopping();
        addItemTest.addSides();

        validateOrderTest.validateVegPizzaOrderWithCoke();
        validateOrderTest.validateNonVegPizzaOrderWithToppings();

        placeOrderTest.placeVegPizzaOrderWithCoke();
        placeOrderTest.placeNonVegPizzaOrderWithToppings();
    }
}
